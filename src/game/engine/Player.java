package game.engine;

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
	public boolean crashed = false;
	
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
		json.put("id", this.id);
		json.put("name", this.name);
		json.put("score", this.score);
		json.put("crashed", this.crashed);
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		// id = json.getInt("id");
		name = json.getString("name");
		score = json.getInt("score");
		crashed = json.getBoolean("crashed");
	}
}
