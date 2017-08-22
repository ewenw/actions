import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class Help {

	public JFrame frame;

	public Help() {
		initialize();
	}

	/**
	 * Initialize help frame with labels
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblCopyrightedEwen = new JLabel(
				"Developed by Ewen Wang, 2014-2017");
		lblCopyrightedEwen.setBounds(10, 236, 226, 14);
		panel.add(lblCopyrightedEwen);

		JLabel lblRecordPlay = new JLabel("Record -;   Play - ' Stop - ESC");
		lblRecordPlay.setBounds(26, 32, 185, 14);
		panel.add(lblRecordPlay);
	}
}
