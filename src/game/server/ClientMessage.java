package game.server;

import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

public class ClientMessage implements JSONSerializable {
	public BotAction botAction;
	public String botName;
	
	@Override
	public String getClassName() {
		return "ClientMessage";
	}
	
	@Override
	public JSONObject toJSON() {
		System.err.println("o->J not allowed on this side");
		return null;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		botAction = new BotAction();
		botAction.fromJSON(json.getJSONObject("action"));
		botName = json.getString("name");
	}
}
