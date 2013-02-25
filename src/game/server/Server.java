package game.server;

import game.engine.Settings;
import game.physics.objects.Vehicle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public final class Server {
	public ServerSocket server;
	public ArrayList<ClientListener> clients = new ArrayList<ClientListener>();
	
	public Server() {
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
			clients.add(new ClientListener(null));
			try {
				Socket client = server.accept();
				client.setSendBufferSize(Settings.Connection.buffer_size);
				clients.get(i).client = client;
				client.setReceiveBufferSize(Settings.Connection.buffer_size);
				client.setSoTimeout(Settings.Server.timeout);
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
		/*Thread[] threads = new Thread[clients.size()];
		for(int i = 0; i<clients.size(); i++) {
			ClientListener clientListener = clients.get(i);
			threads[i] = new Thread(clientListener);
			threads[i].start();
		}
		// Wait for end of listening:
		for(Thread thread : threads)
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		for(int i = 0; i<clients.size(); i++) {
			clients.get(i).run();
		}
		for(ClientListener clientListener : clients) {
			for(Vehicle vehicle : clientListener.player.vehicles) {
				vehicle.engine.powerFactor = clientListener.response.power;
				vehicle.engine.turnFactor = clientListener.response.turn;
			}
		}
	}
	
	public void releaseClients() {
		for(ClientListener c : clients) {
			c.release();
		}
	}
	
}
