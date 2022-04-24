package raymarcher;
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
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setResizable(false);
		display.frame.setVisible(true);
		display.start();
	}
	
	public synchronized void start() {
		rendering = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}
	
	public synchronized void stop() {
		rendering = false;
		try {
			this.thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60;
		double deltaTime = 0;
		int fps = 0;
		
		while(rendering) {
			long now = System.nanoTime();
			deltaTime += (now - lastTime) / ns;
			lastTime = now;
			while (deltaTime >= 1) {
				update();
				deltaTime--;
			}
			draw();
			fps++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(title + " | " + fps + " fps");;
				fps = 0;
			}
		}
	}
	
	private void draw() {
		
	}
	
	private void update() {
		
	}
}
