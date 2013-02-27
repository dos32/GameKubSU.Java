package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Vehicle extends Circle implements JSONSerializable {
	public int indexInTeam;
	public int playerId;
	public String playerName;
	public boolean isTeammate;
	public double health;
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
		indexInTeam = json.getInt("index");
		health = json.getDouble("health");
		playerId = json.getInt("playerId");
		playerName = json.getString("playerName");
		isTeammate = json.getBoolean("isTeammate");
	}
	
	@Override
	public String getClassName() {
		return "Vehicle";
	}

}
