package game.server;

import game.Runner;
import game.engine.Player;
import game.json.JSONClassCheckException;
import game.json.JSONException;
import game.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public final class ClientListener implements Runnable {
	public Socket client;
	public Player player = new Player();
	public ClientMessage cm = new ClientMessage();
	public BotAction response = new BotAction();
	
	BufferedReader in = null;
    PrintWriter out = null;
	
	public ClientListener(Socket client) {
		this.client = client;
	}
	
	public void init() {
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerMessage sm = new ServerMessage(ServerMessage.MT_INIT, null, null);
		out.println(sm.toJSON().toString());
		out.flush();
		ClientMessage cm = new ClientMessage();
		try {
			cm.fromJSON(new JSONObject(in.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (JSONClassCheckException e) {
			e.printStackTrace();
		}
		player.name = cm.botName;
		if(player.name == null)
			player.name = "";
	}

	/*
	 * Send to client serialization of World object and wait to its response,
	 *	which includes changes in controls which manages by client AI
	 */
	@Override
	public void run() {
		if(client == null)
			return;
		if (!client.isClosed() && player.isAlive() && !player.crashed)
			try {
				ServerMessage sm = new ServerMessage(ServerMessage.MT_TICK, Runner.inst().world, player.vehicles.get(0));
				out.println(sm.toJSON().toString());
				out.flush();
				try {
					JSONObject rjson = new JSONObject((String) in.readLine());
					cm.fromJSON(rjson);
					response = cm.botAction;
				} catch (java.net.SocketTimeoutException e) {
					System.out.println(String.format("Strategy crashed::timeout, player=%s", player.name));
					player.crashed = true;
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
		out.println(new ServerMessage(ServerMessage.MT_END, null, null)
			.toJSON().toString());
		out.flush();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
