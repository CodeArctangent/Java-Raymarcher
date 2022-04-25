package raymarcher;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import raymarcher.scene.SDF;
import raymarcher.space.Render;
import raymarcher.space.Vector;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private static String title = "Open Marcher";
	public static final int WIDTH = 750;
	public static final int HEIGHT = 500;
	public static final int SCALE = 20;
	public static final int STEPS = 50;
	public static final Vector SIZE = new Vector(WIDTH, HEIGHT);
	private static boolean rendering = false;
	
	public Display() {
		this.frame = new JFrame();
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
	}
	
	public static double scene(Vector position) {
		double sphere = SDF.sphere(position, 1);
		return sphere;
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title + " - Running on CPU - 0 fps");
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
				this.frame.setTitle(title + " - Running on CPU - " + fps + " fps");
				fps = 0;
			}
		}
	}
	
	private void draw() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics gd = bs.getDrawGraphics();
		gd.setColor(Color.BLACK);
		gd.fillRect(0,  0, WIDTH, HEIGHT);
		for (double y = 0; y < HEIGHT / SCALE; y++) {
			for (double x = 0; x < WIDTH / SCALE; x++) {
//				Color pos = new Color((int) (x / WIDTH * SCALE * 255), (int) (y / HEIGHT * SCALE * 255), 0);
				Color rc = Render.renderScene(SIZE, x * SCALE, y * SCALE, 0, 1000);
				gd.setColor(rc);
				gd.fillRect((int)x*SCALE, (int) y * SCALE * -1 + HEIGHT - SCALE, SCALE, SCALE);
			}
		}
		gd.dispose();
		bs.show();
	}
	
	private void update() {
		
	}
}
