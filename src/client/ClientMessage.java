package client;

import client.model.BotAction;
import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class ClientMessage implements JSONSerializable {
	public BotAction botAction;
	public String botName;
	
	public ClientMessage(BotAction botAction, String botName) {
		this.botAction = botAction;
		this.botName = botName;
	}
	
	@Override
	public String getClassName() {
		return "ClientMessage";
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("class", getClassName());
		json.put("action", botAction.toJSON());
		json.put("name", botName);
		return json;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
	}
}
