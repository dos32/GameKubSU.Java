package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Vehicle extends Circle implements JSONSerializable {
	private int playerId;
	private String playerName;
	private double health;
	private double nitro;
	
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
	 * @return	Health of vehicle
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * 
	 * @return	Health of vehicle
	 */
	public double getNitro() {
		return nitro;
	}
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
		health = json.getDouble("health");
		nitro = json.getDouble("nitro");
		playerId = json.getInt("playerId");
		playerName = json.getString("playerName");
	}
	
	@Override
	public String getClassName() {
		return "Vehicle";
	}

}
