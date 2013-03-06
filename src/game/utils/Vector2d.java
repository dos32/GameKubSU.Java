package game.utils;

import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

import java.io.Serializable;

public class Vector2d implements Serializable, JSONSerializable {
	private static final long serialVersionUID = -7197366098173773030L;
	
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
		return Math.hypot(this.x, this.y);
	}
	
	public double square() {
		return this.x*this.x+this.y*this.y;
	}

	public void add(Vector2d vector) {
		x += vector.x;
		y += vector.y;
	}

	public void add(double x, double y) {
		this.x+=x;
		this.y+=y;
	}

	public Vector2d sum(Vector2d v) {
		return new Vector2d(this.x + v.x, this.y + v.y);
	}

	public Vector2d sum(double x, double y) {
		return new Vector2d(this.x + x, this.y + y);
	}
	
	public Vector2d diff(Vector2d v) {
		return new Vector2d(this.x-v.x, this.y-v.y);
	}
	
	public Vector2d diff(double x, double y) {
		return new Vector2d(this.x-x, this.y-y);
	}
	
	public void sub(Vector2d v) {
		x-=v.x;
		y-=v.y;
	}
	
	public void sub(double x, double y) {
		this.x-=x;
		this.y-=y;
	}
	
	public Vector2d mul(double value) {
		return new Vector2d(this.x*value, this.y*value);
	}
	
	public void scale(double value) {
		x*=value;
		y*=value;
	}
	
	public void negate() {
		x=-x;
		y=-y;
	}
	
	public void normalize() {
		double h = norm();
		if(h!=0) {
			x/=h;
			y/=h;
		}
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("class", this.getClassName());
		json.put("x", Double.toString(x));
		json.put("y", Double.toString(y));
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		x = json.getDouble("x");
		y = json.getDouble("y");
	}

	@Override
	public String getClassName() {
		return "Vector2d";
	}
}
