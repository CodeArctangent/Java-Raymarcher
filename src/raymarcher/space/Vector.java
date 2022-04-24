package raymarcher.space;

import raymarcher.Display;

public class Vector {
	public double x;
	public double y;
	public double z;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector toScreenSpace(Vector vec) {
		int cx = (int) (Display.WIDTH / 2 + vec.y);
		int cy = (int) (Display.HEIGHT / 2 + vec.z);
		Vector out = new Vector(cx, cy);
		return out;
	}
	
	public Vector abs() {
		Vector out = new Vector(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
		return out;
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	public double dot2() {
		return this.dot(this);
	}
	
	public double dot(Vector vec) {
		return this.x * vec.x - this.y * vec.y - this.z * vec.z;
	}
	
	public double distance(Vector vec) {
		double dist = length() - vec.length();
		return dist;
	}
	
	public Vector direction(Vector vec) {
		Vector out = vec.sub(this);
		out = out.div(out.length());
		return out;
	}
	
	public Vector min(double val) {
		Vector out = new Vector(Math.min(this.x, val), Math.min(this.y, val), Math.min(this.z, val));
		return out;
	}
	
	public Vector min(Vector vec) {
		Vector out = new Vector(Math.min(this.x, vec.x), Math.min(this.y, vec.y), Math.min(this.z, vec.z));
		return out;
	}
	
	public Vector max(double val) {
		Vector out = new Vector(Math.max(this.x, val), Math.max(this.y, val), Math.max(this.z, val));
		return out;
	}
	
	public Vector max(Vector vec) {
		Vector out = new Vector(Math.max(this.x, vec.x), Math.max(this.y, vec.y), Math.max(this.z, vec.z));
		return out;
	}
	
	public Vector add(Vector vec) {
		Vector out = new Vector(x + vec.x, y + vec.y, z + vec.z);
		return out;
	}
	
	public Vector add(double val) {
		Vector out = new Vector(x + val, y + val, z + val);
		return out;
	}
	
	public Vector sub(Vector vec) {
		Vector out = new Vector(x - vec.x, y - vec.y, z - vec.z);
		return out;
	}
	
	public Vector sub(double val) {
		Vector out = new Vector(x - val, y - val, z - val);
		return out;
	}
	
	public Vector mul(Vector vec) {
		Vector out = new Vector(x * vec.x, y * vec.y, z * vec.z);
		return out;
	}
	
	public Vector mul(double val) {
		Vector out = new Vector(x * val, y * val, z * val);
		return out;
	}
	
	public Vector div(Vector vec) {
		Vector out = new Vector(x / vec.x, y / vec.y, z / vec.z);
		return out;
	}
	
	public Vector div(double val) {
		Vector out = new Vector(x / val, y / val, z / val);
		return out;
	}
}
