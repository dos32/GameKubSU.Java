package game.server;

import game.Runner;
import game.engine.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public final class ClientListener implements Runnable {
	public Runner runner;
	public Socket client;
	public Player player = new Player();
	public boolean crashed;
	public BotAction response;
	public boolean waiting;
	
	public ClientListener(Runner runner, Socket client) {
		this.runner = runner;
		this.client = client;
	}

	/*
	 * Send to client serialization of World object and wait to its response,
	 * which includes changes in controls which manages by client AI
	 */
	@Override
	public void run() {
		if(client == null)
			return;
		try {
			waiting = true;
			ObjectOutputStream sendData = new ObjectOutputStream(
					client.getOutputStream());
			int tmp = 101;
			sendData.writeObject(tmp);
			sendData.flush();
			ObjectInputStream receivedData = new ObjectInputStream(
					client.getInputStream());
			// client.setSoTimeout(Settings.Server.timeout);
			try {
				response = (BotAction) receivedData.readObject();
			} catch (ClassNotFoundException e) {
				System.err.println("Something wrong with"
						+ " reading object from client response");
				e.printStackTrace();
			}
			waiting = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
