import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JCheckBox;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

public class Menu extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3708394314407132700L;
	
	// define GUI elements
	private JDesktopPane desktopPane;
	public static JButton record, play, stop, delete, exit, edit, help;
	public static JSlider slider;
	public static JPanel contentPane, panel, panel_1, panel_2;
	private JLabel label, lblSpeed, lblRecordings, lblName;
	public static JTextField nameField, textField, textField1;
	static JList list;
	public static int index = 0;
	public static JCheckBox chckbxInfiniteLoop;
	public static Point point = new Point();

	// define button images
	BufferedImage img = null;
	BufferedImage img2 = null;
	BufferedImage pla = null;
	BufferedImage pla2 = null;
	BufferedImage sto = null;
	BufferedImage sto2 = null;
	BufferedImage tra = null;
	BufferedImage edi = null;
	BufferedImage tra2 = null;
	BufferedImage edi2 = null;
	BufferedImage exi = null;

	
	@SuppressWarnings("unchecked")
	public Menu() {
		// use Nimbus look and feel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setShape(new RoundRectangle2D.Double(0, 0, 500, 500, 290, 290));
		setSize(600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		loadImages();
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				Point p = getLocation();
				setLocation(p.x + me.getX() - point.x, p.y + me.getY()
						- point.y);
			}
		});
		// initialize all panels
		setupPanels();
		// initialize all buttons
		createButtons();
		
		// add list selection for recorded files
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedValue() != null) {

					String text = null;
					try {
						text = Main.readFile(new File(list.getSelectedValue()
								+ "").getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					int startIndexSpeed = text.indexOf("(");
					int endIndexSpeed = text.indexOf(")");
					String f1 = "1";
					try{
						f1 = text.substring(startIndexSpeed + 1,
							endIndexSpeed);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					Main.speed = Integer.parseInt(f1);
					slider.setValue(Main.speed);
					int startIndexRepeats = text.indexOf("<");
					try {
						String f2 = text.substring(startIndexRepeats + 1,
								text.length() - 1);
						Main.repeats = Integer.parseInt(f2);
					} catch (Exception e) {

					}
					String value = String.valueOf(list.getSelectedValue());
					nameField.setText(value.substring(0, value.length() - 4));
					index = list.getSelectedIndex();
					textField.setText(String.valueOf(Main.repeats));
				}
			}
		});
		edit.addActionListener(this);
		delete.addActionListener(this);
		// listener for speed slider
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				Main.speed = ((JSlider) ce.getSource()).getValue();
				lblSpeed.setText("Speed: " + Main.speed);
			}
		});
		
		setOpacity(0.9f);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(411, 43, 28, 23);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(0, 0, 511, 510);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		exit = new JButton();
		exit.setFocusPainted(false);
		exit.setIcon(new ImageIcon(exi));
		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.setContentAreaFilled(false);
		exit.addActionListener(this);
		
		JLabel picLabel = new JLabel(new ImageIcon("resources/background1.jpg"));
		
		panel_2.add(panel_4);
		panel_4.add(exit);
		panel_5.add(picLabel);
		desktopPane.add(panel_5);
		
		// refresh GUI elements
		refresh();
	}

	private void loadImages() {
		try {
			exi = ImageIO.read(new File("resources/exit.png"));
			img = ImageIO.read(new File("resources/record.png"));
			img2 = ImageIO.read(new File("resources/record2.png"));
			pla = ImageIO.read(new File("resources/play.png"));
			pla2 = ImageIO.read(new File("resources/play2.png"));
			sto = ImageIO.read(new File("resources/stop.png"));
			sto2 = ImageIO.read(new File("resources/stop2.png"));
			tra = ImageIO.read(new File("resources/trash.png"));
			tra2 = ImageIO.read(new File("resources/trash2.png"));
			edi = ImageIO.read(new File("resources/save.png"));
			edi2 = ImageIO.read(new File("resources/save2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// initialize all buttons with tooltips and listeners
	private void createButtons() {
		record = new JButton();
		record.setFocusPainted(false);
		record.setIcon(new ImageIcon(img));
		record.setBorder(BorderFactory.createEmptyBorder());
		record.setContentAreaFilled(false);
		record.setToolTipText("Record | Shortcut: ;");
		record.setRolloverIcon(new ImageIcon(img2));
		record.addActionListener(this);
		panel.add(record);

		play = new JButton();
		play.setFocusPainted(false);
		play.setIcon(new ImageIcon(pla));
		play.setBorder(BorderFactory.createEmptyBorder());
		play.setContentAreaFilled(false);
		play.setRolloverIcon(new ImageIcon(pla2));
		play.setToolTipText("Play | Shortcut: '");
		play.addActionListener(this);
		panel.add(play);

		stop = new JButton();
		stop.setFocusPainted(false);
		stop.setIcon(new ImageIcon(sto));
		stop.setBorder(BorderFactory.createEmptyBorder());
		stop.setContentAreaFilled(false);
		stop.setRolloverIcon(new ImageIcon(sto2));
		stop.setEnabled(false);
		stop.setToolTipText("Stop | Shortcut: ESC");
		stop.addActionListener(this);
		panel.add(stop);

		help = new JButton("Help");
		help.setToolTipText("Help");
		help.addActionListener(this);
		panel.add(help);

		delete = new JButton();
		delete.setFocusPainted(false);
		delete.setIcon(new ImageIcon(tra));
		delete.setBorder(BorderFactory.createEmptyBorder());
		delete.setContentAreaFilled(false);
		delete.setRolloverIcon(new ImageIcon(tra2));
		delete.setToolTipText("Delete Recording");
		panel_1.add(delete);

		edit = new JButton();
		edit.setFocusPainted(false);
		edit.setIcon(new ImageIcon(edi));
		edit.setBorder(BorderFactory.createEmptyBorder());
		edit.setContentAreaFilled(false);
		edit.setRolloverIcon(new ImageIcon(edi2));
		edit.setToolTipText("Save Settings");
		panel_1.add(edit);
	}

	private void setupPanels() {
		contentPane.setLayout(null);
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 595, 595);
		desktopPane.setBackground(Color.WHITE);
		contentPane.add(desktopPane);
		desktopPane.setLayout(null);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		panel = new JPanel();
		panel.setBounds(0, 32, 490, 59);
		desktopPane.add(panel);

		panel_2 = new JPanel();
		panel_2.setBounds(-32, -36, 552, 545);
		desktopPane.add(panel_2);
		panel_2.setLayout(null);

		lblName = new JLabel("Name:");
		lblName.setBounds(63, 167, 57, 14);
		panel_2.add(lblName);

		lblRecordings = new JLabel("Recordings:");
		lblRecordings.setBounds(63, 251, 112, 14);
		panel_2.add(lblRecordings);

		lblSpeed = new JLabel("Speed: 1");
		lblSpeed.setBounds(327, 157, 93, 34);
		panel_2.add(lblSpeed);

		slider = new JSlider();
		slider.setBounds(327, 192, 200, 31);
		panel_2.add(slider);
		slider.setValue(1);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinimum(1);
		slider.setMaximum(20);

		label = new JLabel("Repeats:");
		label.setBounds(327, 251, 58, 14);
		panel_2.add(label);

		textField = new JTextField();
		textField.setBounds(327, 276, 155, 31);
		panel_2.add(textField);
		textField.setText("1");
		textField.setColumns(10);

		chckbxInfiniteLoop = new JCheckBox("Infinite Loop");
		chckbxInfiniteLoop.setBounds(391, 305, 93, 23);
		chckbxInfiniteLoop.setToolTipText("PRESS ESC TO STOP");
		panel_2.add(chckbxInfiniteLoop);

		nameField = new JTextField();
		nameField.setBounds(63, 192, 155, 34);
		panel_2.add(nameField);
		nameField.setColumns(10);
		nameField.setToolTipText("Rename Selected Recording");
		panel_1 = new JPanel();
		panel_1.setBounds(340, 362, 134, 127);
		panel_2.add(panel_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(66, 276, 222, 200);
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_2.add(panel_3);

		list = new JList(ListFiles.getFiles());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel_3.add(scrollPane, BorderLayout.CENTER);
	}

	// update all GUI elements to latest data
	public static void refresh() {
		String[] files = ListFiles.getFiles();
		list.setListData(files);
		list.setSelectedIndex(index);
		if (files.length > 0) {
			delete.setEnabled(true);
			play.setEnabled(true);
			edit.setEnabled(true);

			String text = null;
			try {
				text = Main.readFile(new File(list.getSelectedValue() + "")
						.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (text != null) {
				int startIndexSpeed = text.indexOf("(");
				int endIndexSpeed = text.indexOf(")");
				String speed_str = text.substring(startIndexSpeed + 1,
						endIndexSpeed);
				Main.speed = Integer.parseInt(speed_str);
				slider.setValue(Main.speed);
				int startIndexRepeats = text.indexOf("<");
				int endIndexRepeats = text.length();
				String repeats_str = text.substring(startIndexRepeats + 1,
						endIndexRepeats - 1);
				try {
					Main.repeats = Integer.parseInt(repeats_str);
				} catch (NumberFormatException e) {
					Main.repeats = 0;
				}
				String value = String.valueOf(list.getSelectedValue());
				textField.setText(String.valueOf(Main.repeats));
				nameField.setText(value.substring(0, value.length() - 4));
			}
		} else {
			delete.setEnabled(false);
			play.setEnabled(false);
			edit.setEnabled(false);
		}

	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b == record) {
			stop.setEnabled(false);
			record.setEnabled(false);
			play.setEnabled(false);
			stop.setEnabled(true);
			edit.setEnabled(false);

			Main.record = true;
			Main.record();
			delete.setEnabled(false);
		}
		if (b == play) {
			save();
			play.setEnabled(false);
			record.setEnabled(false);
			stop.setEnabled(false);
			edit.setEnabled(false);
			Main.play = true;
			Main.play();
			delete.setEnabled(false);
		}
		if (b == stop)
			stopPressed();
		if (b == help) {
			Help window = new Help();
			window.frame.setVisible(true);
		}
		if (b == delete) {
			File file = new File(list.getSelectedValue() + "");
			try {
				Files.delete(file.toPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (index > 0)
				index--;
			refresh();
			if (list.getModel().getSize() == 0)
				nameField.setText("");
		}
		if (b == edit)
			save();
		if (b == exit)
			System.exit(0);
	}

	public static void stopPressed() {
		try {
			if (Main.p != null)
				Main.p.interrupt();
			if (Main.t != null)
				Main.t.interrupt();
		} catch (Exception e) {
			System.out.println("Interrupted");
		}

		if (Main.record == true) {
			MouseListener.write("Speed(1):Repeats<1");
			index = 0;
			refresh();
		}

		Main.record = false;
		Main.play = false;
		Main.s = 0;
		if (Main.timer != null)
			Main.timer.cancel();
		stop.setEnabled(false);
		play.setEnabled(true);
		record.setEnabled(true);
		edit.setEnabled(true);
		delete.setEnabled(true);
		save();
		refresh();

	}

	public static void save() {
		String text = null;
		try {
			text = Main.readFile(new File(list.getSelectedValue() + "")
					.getAbsolutePath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int starti = text.indexOf("(");
		int startf = text.indexOf(")");
		String speed = "";
		String s1 = text.substring(0, starti + 1);
		String s2 = text.substring(startf, text.length());

		speed = String.valueOf(slider.getValue());
		StringBuilder text2 = new StringBuilder(s1 + speed + s2);

		int starti2 = text.indexOf("<");
		String repeats = "";
		text2 = new StringBuilder(text2.substring(0, starti2 + 1));
		repeats = textField.getText();

		if (!text2.toString().contains("<"))
			text2.append("<" + repeats);
		else
			text2.append(repeats);
		Main.repeats = Integer.parseInt(repeats);
		Main.speed = Integer.parseInt(speed);
		PrintWriter writer;
		try {
			writer = new PrintWriter(
					new File(list.getSelectedValue() + "").getAbsolutePath());
			writer.print(text2);
			writer.close();
		} catch (FileNotFoundException exc) {
			exc.printStackTrace();
		}
		File file = new File(list.getSelectedValue() + "");
		File file2 = new File(nameField.getText() + ".mcr");
		boolean success = file.renameTo(file2);
		if (!success) {
			JOptionPane.showMessageDialog(null,
					"Recording name already exists", "",
					JOptionPane.ERROR_MESSAGE);
		}
		refresh();
	}
}
