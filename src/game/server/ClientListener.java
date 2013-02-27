package game.server;

import game.Runner;
import game.engine.Player;
import game.json.JSONClassCheckException;
import game.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public final class ClientListener implements Runnable {
	public Socket client;
	public Player player = new Player();
	public boolean crashed = false;
	public ClientMessage cm = new ClientMessage();
	public BotAction response = new BotAction();
	
	BufferedReader in = null;
    PrintWriter out = null;
	
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
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					out = new PrintWriter(client.getOutputStream());
				}
				ServerMessage sm = new ServerMessage(ServerMessage.MT_TICK, Runner.inst().world, player.vehicles.get(0));
				out.println(sm.toJSON().toString());
				out.flush();
				try {
					JSONObject rjson = new JSONObject((String) in.readLine());
					cm.fromJSON(rjson);
					response = cm.botAction;
				} catch (java.net.SocketTimeoutException e) {
					System.out.println(String.format("Strategy crashed::timeout, player=%s", player.name));
					crashed = true;
				} catch (JSONClassCheckException e) {
					e.printStackTrace();
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
		out.println(new ServerMessage(ServerMessage.MT_END, Runner.inst().world, player.vehicles.get(0))
			.toJSON()
			.toString());
		out.flush();
	}
}
