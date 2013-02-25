package game.server;

import java.io.Serializable;

public class BotAction implements Serializable {
	private static final long serialVersionUID = 6715590463417335976L;
	public double power, turn;
	
	public BotAction() {
	}
	
	public BotAction(double power, double turn) {
		this.power = power;
		this.turn = turn;
	}
	
	public void assign(double power, double turn) {
		this.power = power;
		this.turn = turn;
	}
}
