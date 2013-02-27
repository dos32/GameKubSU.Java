package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Vehicle extends Circle implements JSONSerializable {
	private int playerId;
	private String playerName;
	private boolean isTeammate;
	private double health;
	
	/**
	 * 
	 * @return	Id of vehicle driver
	 */
	public int getPlayerId() {
		return playerId;
	}
	
	/**
	 * 
	 * @return	Name of vehicle driver
	 */
	public String getPlayreName() {
		return playerName;
	}
	
	/**
	 * 
	 * @return	True if vehicle is friendly for you
	 */
	public boolean isTeammate() {
		return isTeammate;
	}
	
	/**
	 * 
	 * @return	Health of vehicle
	 */
	public double getHealth() {
		return health;
	}
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
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
