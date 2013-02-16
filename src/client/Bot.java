package client;

import game.engine.World;
import game.server.BotAction;

public class Bot {
	private ClientRunner runner;
	public Bot(ClientRunner runner){
		this.runner = runner;
	}
	
	public void init() {
		
	}
	
	public void move(World world, BotAction action) {
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
