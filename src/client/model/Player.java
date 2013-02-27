package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Player implements JSONSerializable {
	public int id;
	public String name;
	public int score;

	@Override
	public String getClassName() {
		return "Player";
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
		name = json.getString("name");
		score = json.getInt("score");
	}
}
