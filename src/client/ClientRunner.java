package client;

import game.engine.Settings;
import game.engine.World;
import game.physics.objects.Vehicle;
import game.server.BotAction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;

public class ClientRunner implements Runnable {
	public ClientFrame frame;
	public Socket listener;
	public Bot bot;
	
	public ClientRunner() {
		frame = new ClientFrame();
		frame.setVisible(true);
		frame.setLocation(800, 660);
		frame.setSize(500, 100);
		frame.setTitle("Press arrow keys to move the vehicle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bot = new Bot(this);
	}
	
	@Override
	public void run() {
		try {
			listener = new Socket("localhost", Settings.Server.port);
			// Wait for server signal loop
			while (!listener.isClosed()&&!listener.isInputShutdown()&&!listener.isOutputShutdown()) {
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
				if(world == null)
				{
					frame.dispose();
					return;
				}
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
