package raymarcher.scene;

import raymarcher.space.Vector;

public class SDF {
	public static double SDFUnion(double df1, double df2) {
		return Math.min(df1, df2);
	}
	
	public static double SDFSubtraction(double df1, double df2) {
		return Math.max(-df1, df2);
	}
	
	public static double SDFIntersection(double df1, double df2) {
		return Math.max(df1, df2);
	}
	
	public static double sphere(Vector position, double radius) {
		return position.length() - radius;
	}
	
	public static double box(Vector position, Vector size) {
		Vector q = position.abs().sub(size);
		double dist = q.max(0).length() + Math.min(Math.max(q.x, Math.max(q.y, q.z)), 0);
		return dist;
	}
	
	public static double torus(Vector position, double inner, double outer) {
		Vector q = new Vector((new Vector(position.x, position.z)).length() - inner, position.y);
		double dist = q.length() - outer;
		return dist;
	}
}
