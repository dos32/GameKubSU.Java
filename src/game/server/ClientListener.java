package game.server;

import game.Runner;
import game.engine.Player;
import game.engine.Settings;
import game.engine.Settings.Vehicle;
import game.engine.World;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public final class ClientListener implements Runnable {
	public Socket client;
	public Player player = new Player();
	public boolean crashed;
	public BotAction response;
	
	public ClientListener(Socket client) {
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
		crashed = false;
		if (!client.isClosed())
			try {
				ObjectOutputStream sendData = new ObjectOutputStream(
						client.getOutputStream());
				//int tmp = 101;
				sendData.writeObject(Runner.inst().world);
				sendData.writeObject(player.vehicles.get(0));
				sendData.flush();
				ObjectInputStream receivedData = new ObjectInputStream(
						client.getInputStream());
				client.setSoTimeout(Settings.Server.timeout);
				try {
					response = (BotAction) receivedData.readObject();
				} catch (ClassNotFoundException e) {
					System.err.println("Something wrong with"
							+ " reading object from client response");
					e.printStackTrace();
				} catch (java.net.SocketTimeoutException e) {
					crashed = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void release() {
		try {
			ObjectOutputStream sendData = new ObjectOutputStream(client.getOutputStream());
			World temp1 = null;
			sendData.writeObject(temp1);
			Vehicle temp2 = null;
			sendData.writeObject(temp2);
			sendData.flush();
			//client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
