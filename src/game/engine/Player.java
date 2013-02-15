package game.engine;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 8814139469470501756L;
	protected int id;
	protected String name;
	protected boolean isAlive;
	protected int score;

	/*
	 * Changes score of player and create visualization animation
	 */
	public void ChangeScore(int scoreDelta) {

	}
}
