package client.model;

import client.json.JSONObject;
import client.json.JSONSerializable;

public class Circle extends Unit implements JSONSerializable {
	public double radius;
	
	@Override
	public String getClassName() {
		return "Circle";
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		json.put("radius", Double.toString(radius));
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) {
		// TODO Auto-generated method stub
		
	}
}
