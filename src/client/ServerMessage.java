package client;

import client.model.World;
import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;
import client.model.Vehicle;

public class ServerMessage implements JSONSerializable {
	public int messageType;
	public World world;
	public Vehicle self;
	
	public static final int
			MT_INIT	= 1,
			MT_TICK	= 2,
			MT_END	= 3;
	
	@Override
	public String getClassName() {
		return "ServerMessage";
	}
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		messageType = json.getInt("messageType");
		if(messageType<1 || messageType>3)
			throw new JSONClassCheckException(String.format("Wrong messageType: %s", messageType));
		if(messageType == MT_TICK && !(json.has("world")) && json.has("self"))
			throw new JSONClassCheckException("MessageType is MT_TICK, but world or/and self not received in json");
		if(json.has("world"))
			(world = new World()).fromJSON(json.getJSONObject("world"));
		else
			world = null;
		if(json.has("self"))
			(self = new Vehicle()).fromJSON(json.getJSONObject("self"));
		else
			self = null;
	}
	
}
