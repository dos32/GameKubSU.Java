package client.model;

import client.json.JSONObject;
import client.json.JSONSerializable;
import client.utils.*;

/*
 * Base class for physical object
 */
public abstract class Unit implements JSONSerializable {
	public int id;
	public Vector2d position = new Vector2d();
	public Vector2d speed = new Vector2d();
	public double angle = 0;
	public double angularSpeed = 0;
	
	// JSON part:
	
	@Override
	public String getClassName() {
		return "Unit";
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("class", this.getClassName());
		json.put("id", String.format("%s", id));
		json.put("position", position.toJSON());
		json.put("speed", speed.toJSON());
		json.put("angle", Double.toString(angle));
		json.put("angularSpeed", Double.toString(angularSpeed));
		return json;
	}
	
	@Override
	public void fromJSON(JSONObject json) {
		// TODO
	}
	
}
