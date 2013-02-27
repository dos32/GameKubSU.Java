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
	
	public ServerMessage(int messageType, World world, Vehicle self) {
		this.messageType = messageType;
		this.world = world;
		this.self = self;
	}
	
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
		// TODO processing of message type
		world = new World();
		world.fromJSON(json.getJSONObject("world"));
		self = new Vehicle();
		self.fromJSON(json.getJSONObject("self"));
	}
	
}
