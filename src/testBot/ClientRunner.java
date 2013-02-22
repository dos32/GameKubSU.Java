package testBot;

import game.engine.Settings;
import game.engine.World;
import game.physics.objects.Vehicle;
import game.server.BotAction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientRunner implements Runnable {
	public Socket listener;
	public Bot bot;
	
	public ClientRunner() {
		bot = new Bot();
	}
	
	@Override
	public void run() {
		try {
			listener = new Socket("localhost", Settings.Server.port);
			// Wait for server signal loop
			while (true) {
				ObjectInputStream oin = new ObjectInputStream(listener.getInputStream());
				ObjectOutputStream oout = new ObjectOutputStream(listener.getOutputStream());
				World world = null;
				Vehicle self = null;
				try {
					world = (World) oin.readObject();
					self = (Vehicle) oin.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(world == null || self == null)
					return;
				BotAction action = new BotAction();
				bot.move(world, self, action);
				oout.writeObject(action);
				oout.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) {
		new ClientRunner();
	}*/
}