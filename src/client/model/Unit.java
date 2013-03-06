package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;
import client.utils.Vector2d;

public abstract class Unit implements JSONSerializable {
	private int id;
	private Vector2d position = new Vector2d();
	private Vector2d speed = new Vector2d();
	private double angle = 0;
	private double angularSpeed = 0;
	
	/**
	 * 
	 * @return	Unique identifier of unit
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return	Radius-vector of unit
	 */
	public Vector2d getPosition() {
		return new Vector2d(position);
	}
	
	/**
	 * 
	 * @return	Speed vector of unit
	 */
	public Vector2d getSpeed() {
		return new Vector2d(speed);
	}
	
	/**
	 * 
	 * @return	Angle in radians of unit,
	 * remember that the axis y is directed downward
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * 
	 * @return	Angular speed in radians per tick
	 */
	public double getAngularSpeed() {
		return angularSpeed;
	}
	
	@Override
	public String getClassName() {
		return "Unit";
	}
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		id = json.getInt("id");
		position.fromJSON(json.getJSONObject("position"));
		speed.fromJSON(json.getJSONObject("speed"));
		angle = json.getDouble("angle");
		angularSpeed = json.getDouble("angularSpeed");
	}
	
}
