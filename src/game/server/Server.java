package game.server;

import game.Runner;
import game.engine.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

public final class Server {
	Runner runner;
	ServerSocket server;
	ArrayList<ClientListener> clients = new ArrayList<ClientListener>();
	
	public Server(Runner runner) {
		this.runner = runner;
		try {
			server = new ServerSocket(Settings.Server.port);
		} catch (IOException e) {
			System.err.println(String.format("Failed open server on port %s",
					Settings.Server.port));
			e.printStackTrace();
		}
	}
	
	/*
	 * accepts all of clients - the
	 *  initialization part for this server
	 */
	public void acceptClients() {
		try {
			server.setSoTimeout(Settings.Server.acceptTimeout);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		for(int i=0; i<Settings.playersCount; i++)
		{
			clients.add(new ClientListener(runner, null));
			try {
				// ? multi thread accepting
				clients.get(i).client = server.accept();
			} catch (IOException e) {
				if(e.getClass() == java.net.SocketTimeoutException.class)
					System.err.println(String.format("Can't accept %s client: timeout has been reached", i));
				else
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
