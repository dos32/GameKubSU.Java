package client.model;

import client.json.JSONArray;
import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

import java.util.ArrayList;

public class World implements JSONSerializable {
	public int tick = 0;
	public double width, height;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();

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
		for(int i = 0; i<jplayers.length(); i++) {
			Player player = new Player();
			player.fromJSON(jplayers.getJSONObject(i));
			players.add(player);
		}
		JSONArray jvehicles = json.getJSONArray("vehicles");
		for(int i = 0; i<jvehicles.length(); i++) {
			Vehicle vehicle = new Vehicle();
			vehicle.fromJSON(jvehicles.getJSONObject(i));
			vehicles.add(vehicle);
		}
		JSONArray jobstacles = json.getJSONArray("obstacles");
		for(int i = 0; i<jobstacles.length(); i++) {
			Obstacle obstacle = new Obstacle();
			obstacle.fromJSON(jobstacles.getJSONObject(i));
			obstacles.add(obstacle);
		}
		JSONArray jbonuses = json.getJSONArray("bonuses");
		for(int i = 0; i<jbonuses.length(); i++) {
			Bonus bonus = new Bonus();
			bonus.fromJSON(jbonuses.getJSONObject(i));
			bonuses.add(bonus);
		}
	}

}
