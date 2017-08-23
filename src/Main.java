import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
	static int speed = 1, repeats = 1;
	static Boolean loop = true, keyDown = false, record = false, play = false;;
	static int key = 0, keynumb = 0;
	static Timer timer;
	static Thread p, t;
	static Menu m;
	static String recordingName = "";

	public static void main(String[] args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
			GlobalScreen.registerNativeHook();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		// add standard mouse, key, and JNativeHook listeners
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
			@Override
			public void run() {
				PrintWriter writer;
				try {
					writer = new PrintWriter(new File(recordingName));
					writer.print("");
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			    // initialize a Timer with an interval delay of 1
				s = 0;
				timer = new Timer();
				timer.scheduleAtFixedRate(new addCount(), 0, 1);
				
				// unregister JNativeHook if not recording
				if (!record) {
					GlobalScreen.unregisterNativeHook();
					Thread.currentThread().interrupt();
				}
			}
		};
		t = new Thread(r);
		t.start();
	}

	// thread to play through each command by specified number of times
	public static void play() {
		p = new Thread(new Runnable() {
			@Override
			public void run() {
				String items = null;
				try {
					items = readFile(new File(Menu.list.getSelectedValue() + "")
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
					// reset counter if on infinite repeats
					if (Menu.chckbxInfiniteLoop.isSelected())
						k=0;
					// mouse drag variable
					boolean drag = false;
					for (int i = 0; i < commands.length - 2; i += 4) {
						final int l = i;
						// parse each line of command and perform action using Java Robot
						if (play == true) {
							// key press action
							if (commands[i].contains("KP")) {
								robot.keyPress(Integer
										.parseInt(commands[i + 1]));
								robot.keyRelease(Integer
										.parseInt(commands[i + 1]));
							}
							// mouse scroll wheel action
							if (commands[i].contains("MW")) {
								robot.mouseWheel(Integer
										.parseInt(commands[i + 1]));
							}
							// mouse movement
							if (commands[i].contains("MM")) {
								robot.mouseMove(
										Integer.parseInt(commands[i + 1]),
										Integer.parseInt(commands[i + 2]));
							}
							// mouse down action by specific keys
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
							// mouse dragging action
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
				// update menu after playing
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

	// read file content into String
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
	
	// set Java robot timeout
	public static void timeOut(long m) {
		robot.delay((int) (m / speed));
	}
}

// counter with interval timing
class addCount extends TimerTask {
	@Override
	public void run() {
		Main.s++;
	}

}
