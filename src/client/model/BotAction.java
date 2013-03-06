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
		JSONObject json = new JSONObject();
		json.put("class", getClassName());
		json.put("power", power);
		json.put("turn", turn);
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
	}
}
