package game.engine;

import game.physics.objects.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private static final long serialVersionUID = 8814139469470501756L;
	public int id;
	public String name;
	public boolean isAlive;
	public int score;
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

	/*
	 * Changes score of player and create visualization animation
	 */
	public void ChangeScore(int scoreDelta) {

	}
}
