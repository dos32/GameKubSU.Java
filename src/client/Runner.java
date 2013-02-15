package client;

import game.engine.Settings;

import java.io.IOException;
import java.net.Socket;

public class Runner {
	Socket listener;
	Bot bot;
	
	public Runner() {
		try {
			listener = new Socket("localhost", Settings.AIListener.port);
			bot = new Bot();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
