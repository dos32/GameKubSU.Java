package client;

import javax.swing.JFrame;

import client.model.World;
import client.model.Vehicle;
import client.model.BotAction;

public class _Bot {
	public ClientFrame frame;
	
	public _Bot() {
		frame = new ClientFrame();
		frame.setVisible(true);
		frame.setLocation(800, 660);
		frame.setSize(500, 100);
		frame.setTitle("Press arrow keys to move the vehicle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String init() {
		return "TestBot";
	}
	
	public void move(World world, Vehicle self, BotAction action) {
		if (frame.leftPressed ){
			action.turn = -1;
		}
		if (frame.rightPressed ){
			action.turn = 1;
		}
		if (frame.upPressed ){
			action.power = 2;
		}
		if (frame.downPressed ){
			action.power = -2;
		}
	}
}
