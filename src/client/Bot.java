package client;

import client.model.World;
import client.model.Vehicle;
import client.model.BotAction;

public class Bot {
	private ClientRunner runner;
	public Bot(ClientRunner runner){
		this.runner = runner;
	}
	
	public String init() {
		return "";
	}
	
	public void move(World world, Vehicle self, BotAction action) {
		if (runner.frame.leftPressed ){
			action.turn = 1;
		}
		if (runner.frame.rightPressed ){
			action.turn = -1;
		}
		if (runner.frame.upPressed ){
			action.power = 1;
		}
		if (runner.frame.downPressed ){
			action.power = -1;
		}
	}
}
