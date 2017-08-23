import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
	int pressed = 0;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		pressed = e.getKeyCode();
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		if (pressed == NativeKeyEvent.VK_ESCAPE) {
			Menu.stopPressed();
		} else if (pressed == ';') {
			if (Main.record == false) {
				System.out.println("Now Recording");
				Menu.record.setEnabled(false);
				Menu.play.setEnabled(false);
				Menu.stop.setEnabled(false);
				Menu.edit.setEnabled(false);
				Menu.help.setEnabled(false);
				Main.record = true;
				Main.record();
				Menu.delete.setEnabled(false);
			} else
				Menu.stopPressed();

		} else if (pressed == 222) {
			System.out.println("Playing");
			Menu.stopPressed();
			Menu.save();
			Menu.play.setEnabled(false);
			Menu.record.setEnabled(false);
			Menu.stop.setEnabled(false);
			Menu.edit.setEnabled(false);
			Main.play = true;
			Main.play();
			Menu.delete.setEnabled(false);
		} else if (Main.record == true) {
			MouseListener.write("KP:" + pressed + ":T:" + Main.s + ":");
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {

	}
}