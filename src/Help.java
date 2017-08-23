import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class Help {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtEsc;

	public Help() {
		initialize();
	}

	/**
	 * Initialize help frame with labels
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 606, 416);
		frame.setResizable(false);
		frame.setUndecorated(true);
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblCopyrightedEwen = new JLabel(
				"Ewen Wang 2017");
		lblCopyrightedEwen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCopyrightedEwen.setBounds(364, 259, 427, 80);
		panel.add(lblCopyrightedEwen);

		JLabel lblRecordPlay = new JLabel("Hot Keys");
		lblRecordPlay.setBounds(54, 0, 108, 85);
		panel.add(lblRecordPlay);
		
		textField = new JTextField();
		textField.setText(";");
		textField.setBounds(140, 108, 22, 34);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("'");
		textField_1.setBounds(306, 169, 22, 34);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		txtEsc = new JTextField();
		txtEsc.setText("ESC");
		txtEsc.setBounds(140, 226, 53, 34);
		panel.add(txtEsc);
		txtEsc.setColumns(10);
		
		JLabel lblRecord = new JLabel("Record");
		lblRecord.setBounds(54, 111, 98, 28);
		panel.add(lblRecord);
		
		JLabel lblFinishRecording = new JLabel("Finish Recording & Play");
		lblFinishRecording.setBounds(54, 170, 288, 34);
		panel.add(lblFinishRecording);
		
		JLabel lblStop = new JLabel("Stop");
		lblStop.setBounds(54, 226, 288, 34);
		panel.add(lblStop);
		
		new WindowDragListener(frame);
	}
}
