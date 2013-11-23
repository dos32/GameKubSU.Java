package client;

import client.json.JSONClassCheckException;
import client.json.JSONException;
import client.json.JSONObject;
import client.model.BotAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRunner implements Runnable {
	public Socket listener;
	public Bot bot;
    
	BufferedReader in = null;
    PrintWriter out = null;
	
	public ClientRunner() {
		bot = new Bot();
	}
	
	@Override
	public void run() {
		try {
			listener = new Socket(Settings.host, Settings.port);
			in = new BufferedReader(new InputStreamReader(listener.getInputStream()));
			out = new PrintWriter(listener.getOutputStream());
			while (!listener.isClosed()&&!listener.isInputShutdown()&&!listener.isOutputShutdown()) {
				ServerMessage sm = new ServerMessage();
				String s = "";
				try {
					s = in.readLine();
					if(s==null || s=="") {
						System.err.println("s is undef");
					}
					sm.fromJSON(new JSONObject(s));
				} catch (JSONException | JSONClassCheckException e) {
					System.err.printf("Some errors with deserializing JSON \"%s\"\n", s);
					e.printStackTrace();
				}
				ClientMessage cm = null;
				switch (sm.messageType) {
					case ServerMessage.MT_INIT:
						cm = new ClientMessage(new BotAction(), bot.init());
						break;
					case ServerMessage.MT_TICK:
						BotAction action = new BotAction();
						bot.move(sm.world, sm.self, action);
						cm = new ClientMessage(action, "");
						break;
					case ServerMessage.MT_END:
						//frame.dispose();
						return;
					default:
						break;
				}
				String sr = cm.toJSON().toString();
				out.println(sr);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ClientRunner().run();
	}
}
