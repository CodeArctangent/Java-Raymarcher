package raymarcher.space;

import java.awt.Color;

import raymarcher.Display;

public class Render {
	public static double distanceToSurface(Vector eye, Vector dirction, double start, double end) {
		double depth = start;
	    for (int i = 0; i < Display.STEPS; i++) {
	    	double dist = Display.scene(eye.add(depth).mul(dirction));
	        if (dist < 0.000001) {
				return depth;
	        }
	        depth += dist;
	        if (depth >= end) {
	            return end;
	        }
	    }
	    return end;
	}
	
	public static Vector rayDirection(double fov, Vector size, Vector fragCoord) {
	    Vector xy = fragCoord.sub(size.div(2));
	    double z = size.y / Math.tan(Math.toRadians(fov) / 2.0);
	    xy = new Vector(xy.x, xy.y, -z);
	    return xy;
	}
	
	public static double clamp(double val, double min, double max) {
	    return Math.max(min, Math.min(max, val));
	}
	
	public static Color renderScene(Vector size, double x, double y, double min, double max) {
		Vector pos = new Vector(x, y);
		Vector dir = rayDirection(45, size, pos);
		Vector eye = new Vector(0, 0, 5);
		double dist = distanceToSurface(eye, dir, min, max);
        Color col = new Color(255, 0, 0);
		if (dist > max - 0.000001) {
	        col = new Color(0, 0, 0);
	    }
		return col;
	}

}
