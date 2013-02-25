package client.model;

import client.json.JSONObject;
import client.json.JSONSerializable;

public class Obstacle extends Unit implements JSONSerializable {
	
	@Override
	public String getClassName() {
		return "Obstacle";
	}
	
	@Override
	public JSONObject toJSON() {
		// TODO
		return super.toJSON();
	}
	
	@Override
	public void fromJSON(JSONObject json) {
		// TODO
	}
	
}
