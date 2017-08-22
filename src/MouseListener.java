import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class MouseListener implements NativeMouseInputListener,
		NativeMouseWheelListener {

	public static String filename = "";

	public MouseListener() {

	}

	public void nativeMousePressed(NativeMouseEvent e) {

	}

	public void nativeMouseReleased(NativeMouseEvent e) {

		if (Main.record) {
			write(e.getButton() + "MD:" + e.getX() + ":" + e.getY()
					+ e.getButton() + ":" + Main.s + ":");
		}
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		if (Main.record) {
			write(e.getButton() + "Drag:" + e.getX() + ":" + e.getY() + ":"
					+ Main.s + ":");
		}
	}

	public void nativeMouseMoved(NativeMouseEvent e) {

		if (Main.record) {
			write("MM:" + e.getX() + ":" + e.getY() + ":" + Main.s + ":");
		}
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {

	}

	public static void write(String s) {
		FileWriter fw;
		try {
			fw = new FileWriter(new File(Main.recordingName).getAbsoluteFile(),
					true);

			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(s);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		if (Main.record) {
			write("MW:" + e.getWheelRotation() + ":T:" + Main.s + ":");
		}
	}

}
