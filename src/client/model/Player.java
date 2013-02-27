package client.model;

import client.json.JSONClassCheckException;
import client.json.JSONObject;
import client.json.JSONSerializable;

public class Player implements JSONSerializable {
	private int id;
	private String name;
	private int score;
	private boolean crashed;
	
	/**
	 * @return	The unique id of player
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return	Name of player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return	Amount of game points of this player
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * 
	 * @return	Status of players strategy
	 */
	public boolean isCrashed() {
		return crashed;
	}

	@Override
	public String getClassName() {
		return "Player";
	}

	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		id = json.getInt("id");
		name = json.getString("name");
		score = json.getInt("score");
		crashed = json.getBoolean("crashed");
	}
}
