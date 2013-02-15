package client;

import game.engine.World;

public class Bot {
	public void init() {
		
	}
	
	public void move(World world, BotAction action) {
		action.power = 1;
		action.turn = 1;
	}
}
