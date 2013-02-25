package client.model;

import game.engine.Player;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Vehicle extends Circle implements JSONSerializable {
	public transient Player player;
	protected int indexInTeam;
	protected int playerId;
	protected String playerName;
	protected boolean isTeammate;

	public double health = 0.5;
	
	@Override
	public JSONObject toJSON() {
		// TODO
		JSONObject json = super.toJSON();
		json.put("index", indexInTeam);
		json.put("health", health);
		json.put("playerId", playerId);
		json.put("playerName", playerName);
		json.put("isTeammate", isTeammate);
		return json;
	}
	
	@Override
	public void fromJSON(JSONObject json) {
		// TODO
	}
	
	@Override
	public String getClassName() {
		return "Vehicle";
	}

}
