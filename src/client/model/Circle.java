package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Circle extends Unit implements JSONSerializable {
	private double radius;
	
	/**
	 * 
	 * @return	Radius of the circle
	 */
	public double getRadius() {
		return radius;
	}
	
	@Override
	public String getClassName() {
		return "Circle";
	}

	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
		radius = json.getDouble("radius");
	}
}
