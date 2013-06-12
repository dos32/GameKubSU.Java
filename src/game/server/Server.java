package game.server;

import game.Runner;
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
			System.err.printf("Failed open server on port %s\n", Settings.Server.port);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Accepts all of clients - the
	 *  initialization part for this server
	 */
	public void acceptClients() {
		try {
			server.setSoTimeout(Settings.Server.acceptTimeout);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		for(int i=0; i<Settings.playersCount; i++) {
			clients.add(new ClientListener(null));
			try {
				Runner.inst().infoTick.message = String.format("Wait for %d client...", i+1);
				Runner.inst().forceRender();
				Socket client = server.accept();
				/*client.setSendBufferSize(Settings.Connection.buffer_size);
				client.setReceiveBufferSize(Settings.Connection.buffer_size);*/
				client.setSoTimeout(Settings.Server.tickTimeout);
				clients.get(i).client = client;
				clients.get(i).init();
				Runner.inst().infoTick.message = String.format("Client %d \"%s\" connected.", i+1, clients.get(i).player.name);
				Runner.inst().forceRender();
			} catch (IOException e) {
				if(e.getClass() == java.net.SocketTimeoutException.class)
					System.err.printf("Can't accept %s client: timeout has been reached\n", i);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Broadcast to all clients
	 */
	public void tick() {
		for(int i = 0; i<clients.size(); i++)
			clients.get(i).run();
		for(ClientListener clientListener : clients) {
			for(Vehicle vehicle : clientListener.player.vehicles) {
				vehicle.engine.powerFactor = clientListener.response.power;
				vehicle.engine.turnFactor = clientListener.response.turn;
			}
		}
	}
	
	/**
	 * Send endgame message and close all clients
	 */
	public void releaseClients() {
		for(ClientListener c : clients)
			c.release();
	}
	
}
