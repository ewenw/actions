import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class WindowDragListener {

	public static Point point = new Point();
	
	public WindowDragListener(final JFrame frame) {
		// add mouse listener to panel
				frame.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						point.x = e.getX();
						point.y = e.getY();
					}
				});
				// GUI window move with mouse
				frame.addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseDragged(MouseEvent me) {
						Point p = frame.getLocation();
						frame.setLocation(p.x + me.getX() - point.x, p.y + me.getY()
								- point.y);
					}
				});

	}
}
