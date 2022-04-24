package raymarcher.scene;

import raymarcher.space.Vector;

public class SDF {
	public static double sphere(Vector position, double radius) {
		return position.length() - radius;
	}
	
//	public static double box(Vector position, Vector size) {
//		Vector q = Math.abs()
//	}
}
