package raymarcher.space;

import java.awt.Color;

import raymarcher.Display;

public class Render {
	public static final double EPSILON = Math.ulp(1);
	
	public static double distanceToSurface(Vector eye, Vector dirction, double start, double end) {
		double depth = start;
	    for (int i = 0; i < Display.STEPS; i++) {
	    	double dist = Display.scene(eye.add(depth).mul(dirction));
	        if (dist < EPSILON) {
				return depth;
	        }
	        depth += dist;
	        if (depth >= end) {
	            return end;
	        }
	    }
	    return end;
	}
	
	public static Vector estimateNormal(Vector pos) {
	    Vector out = new Vector(
	        Display.scene(new Vector(pos.x + EPSILON, pos.y, pos.z)) 
	        	- Display.scene(new Vector(pos.x - EPSILON, pos.y, pos.z)),
	        Display.scene(new Vector(pos.x, pos.y + EPSILON, pos.z)) 
	        	- Display.scene(new Vector(pos.x, pos.y - EPSILON, pos.z)),
	        Display.scene(new Vector(pos.x, pos.y, pos.z + EPSILON)) 
	        	- Display.scene(new Vector(pos.x, pos.y, pos.z - EPSILON))
	    );
	    return out.normalized();
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
	
	public static Color renderScene(Vector size, double x, double y, double min, double max, double time) {
		Vector pos = new Vector(x-Math.sin(time)*100, y-Math.cos(time)*100);
		Vector dir = rayDirection(35, size, pos);
		Vector eye = new Vector(1, 1, 0);
		Vector norm = estimateNormal(pos);
		double dist = distanceToSurface(eye, dir, min, max);
        Color col = new Color((int) Render.clamp(dist, 0, 255), 0, 0);
//		Color col = new Color((int)Render.clamp(norm.x, 0, 255), 
//			(int)Render.clamp(norm.y, 0, 255),
//			(int)Render.clamp(norm.z, 0, 255));
//		if (dist > max - 0.000001) {
//	        col = new Color(0, 0, 0);
//	    }
		return col;
	}

}
