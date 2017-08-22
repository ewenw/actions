import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
	public static String test = "";
	public static volatile int s = 0;
	static Robot robot;
	static int speed = 1;
	static int repeats = 1;
	Boolean loop = true;
	static Boolean keyDown = false;
	static int key = 0, keynumb = 0;
	static Boolean record = false, play = false;
	static Timer timer;
	static Thread p, t;
	static Menu m;
	static String recordingName = "";

	public static void main(String[] args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					m = new Menu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.exit(1);
		}
		MouseListener mouse = new MouseListener();
		KeyListener key = new KeyListener();
		GlobalScreen.getInstance().addNativeMouseListener(mouse);
		GlobalScreen.getInstance().addNativeMouseMotionListener(mouse);
		GlobalScreen.getInstance().addNativeMouseWheelListener(mouse);
		GlobalScreen.getInstance().addNativeKeyListener(key);

	}

	public static void record() {
		recordingName = String.valueOf(System.currentTimeMillis() + ".mcr");
		Runnable r = new Runnable() {
			public void run() {
				PrintWriter writer;
				try {
					writer = new PrintWriter(new File(recordingName));

					writer.print("");
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				s = 0;
				timer = new Timer();
				timer.scheduleAtFixedRate(new addCount(), 0, 1);

				if (!record) {
					GlobalScreen.unregisterNativeHook();
					Thread.currentThread().interrupt();
				}
			}
		};

		t = new Thread(r);
		t.start();
	}

	public static void play() {
		p = new Thread(new Runnable() {
			public void run() {

				String items = null;
				try {
					items = readFile(new File(m.list.getSelectedValue() + "")
							.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (items.length() < 1) {
					System.out.println("Please Select a Valid File");
					Menu.stopPressed();
				}
				final String[] commands = items.split(":");
				for (int k = 0; k < repeats; k++) {
					if (Menu.chckbxInfiniteLoop.isSelected())
						k--;
					boolean drag = false;
					for (int i = 0; i < commands.length - 2; i += 4) {
						final int l = i;
						if (play == true) {
							if (commands[i].contains("KP")) {
								robot.keyPress(Integer
										.parseInt(commands[i + 1]));
								robot.keyRelease(Integer
										.parseInt(commands[i + 1]));
							}
							if (commands[i].contains("MW")) {
								robot.mouseWheel(Integer
										.parseInt(commands[i + 1]));
							}
							if (commands[i].contains("MM")) {
								robot.mouseMove(
										Integer.parseInt(commands[i + 1]),
										Integer.parseInt(commands[i + 2]));
							}
							if (commands[i].contains("MD")) {
								if (commands[i].contains("1")) {
									robot.mousePress(InputEvent.BUTTON1_MASK);
									robot.mouseRelease(InputEvent.BUTTON1_MASK);
								} else if (commands[i].contains("2")) {
									robot.mousePress(InputEvent.BUTTON3_MASK);
									robot.mouseRelease(InputEvent.BUTTON3_MASK);
								} else if (commands[i].contains("3")) {
									robot.mousePress(InputEvent.BUTTON2_MASK);
									robot.mouseRelease(InputEvent.BUTTON2_MASK);
								}
							}
							if (commands[i].contains("Drag")) {
								int j = i;
								if (drag == false) {
									while (!commands[j].contains("MD")) {
										j += 4;
									}
									if (commands[j].contains("1"))
										robot.mousePress(InputEvent.BUTTON1_MASK);
									if (commands[j].contains("2"))
										robot.mousePress(InputEvent.BUTTON3_MASK);
									if (commands[j].contains("3"))
										robot.mousePress(InputEvent.BUTTON2_MASK);
									drag = true;
								} else {
									robot.mouseMove(
											Integer.parseInt(commands[i + 1]),
											Integer.parseInt(commands[i + 2]));
									if (commands[i + 4].contains("MD")) {
										if (commands[i + 4].contains("1"))
											robot.mouseRelease(InputEvent.BUTTON1_MASK);
										if (commands[i + 4].contains("3"))
											robot.mouseRelease(InputEvent.BUTTON2_MASK);
										if (commands[i + 4].contains("2"))
											robot.mouseRelease(InputEvent.BUTTON3_MASK);
										i += 4;
										while (commands[i + 4].contains("MD")) {
											i += 4;
										}
										drag = false;
									}
								}
							}
						}
						if (i + 7 < commands.length)
							timeOut(Long.parseLong(commands[i + 7])
									- Long.parseLong(commands[i + 3]));
					}
				}
				Menu.refresh();
				Main.play = false;
				Menu.stop.setEnabled(false);
				Menu.play.setEnabled(true);
				Menu.record.setEnabled(true);
				Menu.edit.setEnabled(true);
				Menu.delete.setEnabled(true);
				Thread.currentThread().interrupt();
			}

		});
		p.start();

	}

	static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static void timeOut(long m) {
		robot.delay((int) (m / speed));
	}
}

class addCount extends TimerTask {
	public void run() {
		Main.s++;
	}

}
