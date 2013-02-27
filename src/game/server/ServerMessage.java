package game.server;

import game.engine.World;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;
import game.physics.objects.Vehicle;

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
		JSONObject json = new JSONObject();
		json.put("messageType", messageType);
		json.put("world", world.toJSON());
		json.put("self", self.toJSON());
		return json;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		throw new JSONClassCheckException("s->JSON for this class is not provided");
	}
	
}
