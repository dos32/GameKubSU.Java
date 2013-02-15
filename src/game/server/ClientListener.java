package game.server;

import game.Runner;
import game.engine.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public final class ClientListener implements Runnable {
	public Runner runner;
	public Socket client;
	public boolean crashed;
	public ClientResponse response;
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
			BufferedReader in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			ObjectOutputStream sendData = new ObjectOutputStream(client.getOutputStream());
			sendData.writeObject(runner.world);
			sendData.close();// or sendData.flush();
			long t1 = System.currentTimeMillis();
			while(!in.ready()) {
				if(System.currentTimeMillis() - t1 > Settings.AIListener.timeout)
				{
					crashed = true;
					waiting = false;
					return;
				}
				ObjectInputStream receivedData = 
					new ObjectInputStream(client.getInputStream());
				try {
					response = (ClientResponse)receivedData.readObject();
				} catch (ClassNotFoundException e) {
					System.err.println("Something wrong with"+
						" reading object from client response");
					e.printStackTrace();
				}
			}
			waiting = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
