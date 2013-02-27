package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class BotAction implements JSONSerializable {
	public double power, turn;

	@Override
	public String getClassName() {
		return "BotAction";
	}

	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		power = json.getDouble("power");
		turn = json.getDouble("turn");
	}
}
