package client.model;

import client.json.JSONArray;
import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class World implements JSONSerializable {
	private int tick = 0;
	private double width, height;
	private Player[] players;
	private Vehicle[] vehicles;
	private Obstacle[] obstacles;
	private Bonus[] bonuses;
	
	/**
	 * 
	 * @return	Current game tick
	 */
	public int getTick() {
		return tick;
	}
	
	/**
	 * 
	 * @return	Width of world
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return	Height of world
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * 
	 * @return	All players
	 */
	public Player[] getPlayers() {
		return players.clone();
	}
	
	/**
	 * 
	 * @return	All vehicles
	 */
	public Vehicle[] getVehicles() {
		return vehicles.clone();
	}
	
	/**
	 * 
	 * @return	All obstacles
	 */
	public Obstacle[] getObstacles() {
		return obstacles.clone();
	}
	
	/**
	 * 
	 * @return	All bonuses
	 */
	public Bonus[] getBonuses() {
		return bonuses.clone();
	}

	@Override
	public String getClassName() {
		return "World";
	}
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		tick = json.getInt("tick");
		width = json.getDouble("width");
		height = json.getDouble("height");
		
		JSONArray jplayers = json.getJSONArray("players");
		players = new Player[jplayers.length()];
		for(int i = 0; i<jplayers.length(); i++) {
			Player player = new Player();
			player.fromJSON(jplayers.getJSONObject(i));
			players[i] = player;
		}
		
		JSONArray jvehicles = json.getJSONArray("vehicles");
		vehicles = new Vehicle[jvehicles.length()];
		for(int i = 0; i<jvehicles.length(); i++) {
			Vehicle vehicle = new Vehicle();
			vehicle.fromJSON(jvehicles.getJSONObject(i));
			vehicles[i] = vehicle;
		}
		
		JSONArray jobstacles = json.getJSONArray("obstacles");
		obstacles = new Obstacle[jobstacles.length()];
		for(int i = 0; i<jobstacles.length(); i++) {
			Obstacle obstacle = new Obstacle();
			obstacle.fromJSON(jobstacles.getJSONObject(i));
			obstacles[i] = obstacle;
		}
		
		JSONArray jbonuses = json.getJSONArray("bonuses");
		bonuses = new Bonus[jbonuses.length()];
		for(int i = 0; i<jbonuses.length(); i++) {
			Bonus bonus = new Bonus();
			bonus.fromJSON(jbonuses.getJSONObject(i));
			bonuses[i] = bonus;
		}
	}

}
