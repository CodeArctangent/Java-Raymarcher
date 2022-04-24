import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private static String title = "Open Marcher";
	private static final int WIDTH = 768;
	private static final int HEIGHT = 512;
	private static boolean rendering = false;
	
	public Display() {
		this.frame = new JFrame();
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
	}
	
	@Override
	public void run() {
		
	}
}
