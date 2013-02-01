package game.utils;

public class Vector2d {
	public double x, y;

	public Vector2d() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d(Vector2d prototype) {
		this.x = prototype.x;
		this.y = prototype.y;
	}
	
	public void assign(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void assign(Vector2d vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	public double dotprod(Vector2d v) {
		return v.x * this.x + v.y * this.y;
	}

	public double norm() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public void add(Vector2d vector) {
		this.x += vector.x;
		this.y += vector.y;
	}

	public Vector2d sum(Vector2d v) {
		return new Vector2d(this.x + v.x, this.y + v.y);
	}
	
	public Vector2d mul(double value) {
		return new Vector2d(this.x*value, this.y*value);
	}
	
	public void scale(double value) {
		this.x*=value;
		this.y*=value;
	}
	
	public void negate() {
		this.x=-this.x;
		this.y=-this.y;
	}
}
