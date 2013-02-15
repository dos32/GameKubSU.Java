package client;

import game.engine.Settings;
import game.engine.World;
import game.server.BotAction;

import java.io.IOException;
import java.net.Socket;

public class Runner {
	Socket listener;
	Bot bot;
	
	public Runner() {
		try {
			listener = new Socket("localhost", Settings.Server.port);
			bot = new Bot();
			// Wait for server signal loop
			while(true) {
				// Deserialize world
				World world = null;
				BotAction action = new BotAction();
				bot.move(world, action);
				// Serialize and send action
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Runner();
	}
}
