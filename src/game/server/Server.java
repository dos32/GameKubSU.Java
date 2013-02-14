package game.server;

import game.Runner;
import game.engine.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
	Runner runner;
	ServerSocket server;
	ArrayList<ClientListener> clients = new ArrayList<ClientListener>();
	
	public Server(Runner runner) {
		this.runner = runner;
		try {
			server = new ServerSocket(Settings.AIListener.port);
		} catch (IOException e) {
			System.err.println(String.format("Failed open server on port %s",
					Settings.AIListener.port));
			e.printStackTrace();
		}
	}
	
	/*
	 * accepts all of clients - the
	 *  initialization part for this server
	 */
	public void acceptClients() {
		try {
			server.setSoTimeout(Settings.AIListener.acceptTimeout);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		for(int i=0; i<Settings.playersCount; i++)
		{
			try {
				clients.add(new ClientListener(runner, server.accept()));
			} catch (IOException e) {
				System.err.println(String.format("Can't accept %s client", i));
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Broadcast to all clients
	 */
	public void tick() {
		for(ClientListener clientListener : clients) {
			new Thread(clientListener).start();
		}
		boolean clientsThinking = true;
		while(clientsThinking)
		{
			clientsThinking = false;
			for(ClientListener cl : clients)
				if(cl.waiting)
					clientsThinking |= true;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
