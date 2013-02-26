package game.engine;

import game.json.JSONArray;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;
import game.physics.objects.Vehicle;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable, JSONSerializable {
	private static final long serialVersionUID = 8814139469470501756L;
	protected static int lastId = 1;
	public int id;
	public String name = "";
	public int score;
	public Color color;
	
	public Player() {
		this.id = lastId++;
	}
	
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	/*
	 * True if at least one of player's
	 * 	vehicles is alive
	 */
	public boolean isAlive() {
		boolean res = false;
		for(Vehicle vehicle : vehicles)
			res |= vehicle.health>0;
		return res;
	}

	/*
	 * Changes score of player and create animation
	 */
	public void ChangeScore(int scoreDelta) {
		score+= scoreDelta;
		// TODO animation
	}

	@Override
	public String getClassName() {
		return "Player";
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("class", this.getClassName());
		json.put("id", this.name);
		json.put("score", this.score);
		for(Vehicle vehicle : this.vehicles)
			json.append("vehicles", vehicle.toJSON());
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		// id = json.getInt("id");
		score = json.getInt("score");
		JSONArray jvehicles = json.getJSONArray("vehicles");
		for(int i = 0; i<jvehicles.length(); i++) {
			Vehicle vehicle = new Vehicle(this);
			vehicle.fromJSON(jvehicles.getJSONObject(i));
		}
			
	}
}
