package game.engine;

import game.physics.objects.Vehicle;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private static final long serialVersionUID = 8814139469470501756L;
	public int id;
	public String name;
	public int score;
	public Color color;
	
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
	 * Changes score of player and create visualization animation
	 */
	public void ChangeScore(int scoreDelta) {
		score+= scoreDelta;
		// TODO animation
	}
}
