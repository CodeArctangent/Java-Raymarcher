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
	public static final int WIDTH = 768;
	public static final int HEIGHT = 512;
	public static final int SCALE = 2;
	public static final int STEPS = 50;
	public static final Vector SIZE = new Vector(WIDTH, HEIGHT);
	public static final Vector DELTA = SIZE.div(2);
	private static boolean rendering = false;
	
	public Display() {
		this.frame = new JFrame();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
	}
	
	public static double scene(Vector position) {
		double sphere = SDF.sphere(position, 100);
		return sphere;
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title + " - Res: " + WIDTH/SCALE + "x" + HEIGHT/SCALE + " - Running on CPU - 0 fps");
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
		double time = 0;
		while(rendering) {
			long now = System.nanoTime();
			deltaTime += (now - lastTime) / ns;
			lastTime = now;
			while (deltaTime >= 1) {
				update();
				deltaTime--;
			}
			time += 0.05;
			draw(time);
			fps++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(title + " - Res: " + WIDTH/SCALE + "x" + HEIGHT/SCALE + " - Running on CPU - " + fps + " fps");
				fps = 0;
			}
		}
	}
	
	private void draw(double dt) {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics gd = bs.getDrawGraphics();
		gd.setColor(Color.BLACK);
		gd.fillRect(0,  0, WIDTH, HEIGHT);
		for (double y = -DELTA.y; y < DELTA.y / SCALE; y++) {
			for (double x = -DELTA.x; x < DELTA.x / SCALE; x++) {
//				Color rc = new Color((int) Render.clamp(x / DELTA.x * SCALE * 255, 0, 255), 
//					(int) Render.clamp(y / DELTA.y * SCALE * 255, 0, 255), 0);
				Color rc = Render.renderScene(SIZE, x * SCALE + DELTA.x, y * SCALE + DELTA.y, 0, 1000, dt);
//				double dist = SDF.sphere(new Vector(x, y), 100);
//				Color rc = new Color((int)Render.clamp(dist * 255, 0, 255), (int)Render.clamp(dist * 255, 0, 255), 0);
				gd.setColor(rc);
				gd.fillRect((int) (x * SCALE + DELTA.x), 
					(int) (-(y * SCALE + DELTA.y) + HEIGHT - SCALE), SCALE, SCALE);
			}
		}
		gd.dispose();
		bs.show();
	}
	
	private void update() {
		
	}
}
