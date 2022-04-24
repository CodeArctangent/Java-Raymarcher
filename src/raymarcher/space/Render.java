package raymarcher.space;

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

}
