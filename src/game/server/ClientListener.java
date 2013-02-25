package game.server;

import game.Runner;
import game.engine.Player;
import game.engine.Settings.Vehicle;
import game.engine.World;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public final class ClientListener implements Runnable {
	public Socket client;
	public Player player = new Player();
	public boolean crashed = false;
	public BotAction response;
	
	protected ObjectOutputStream out = null;
	protected ObjectInputStream in = null;
	
	public ClientListener(Socket client) {
		this.client = client;
	}

	/*
	 * Send to client serialization of World object and wait to its response,
	 *	which includes changes in controls which manages by client AI
	 */
	@Override
	public void run() {
		if(client == null)
			return;
		if (!client.isClosed() && player.isAlive())
			try {
				if(out == null) {
					out = new ObjectOutputStream(client.getOutputStream());
					out.flush();
					in = new ObjectInputStream(client.getInputStream());
				}
				out.reset();
				out.writeObject(Runner.inst().world);
				out.writeObject(player.vehicles.get(0));
				out.flush();
				try {
					response = (BotAction) in.readObject();
				} catch (ClassNotFoundException e) {
					System.err.println("Something wrong with"
							+ " reading object from client response");
					e.printStackTrace();
				} catch (java.net.SocketTimeoutException e) {
					System.out.println(String.format("Strategy crashed::timeout, player=%s", player.name));
					crashed = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		else {
			if(response == null)
				response = new BotAction(0, 0);
			else
				response.assign(0, 0);
		}
	}
	
	public void release() {
		try {
			// ObjectOutputStream sendData = new ObjectOutputStream(client.getOutputStream());
			World temp1 = null;
			out.writeObject(temp1);
			Vehicle temp2 = null;
			out.writeObject(temp2);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
