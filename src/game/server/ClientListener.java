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
	
	/**
	 * Send to client init request
	 */
	public void init() {
		if(client == null) {
			System.err.printf("Client of player #%s is null\n", player.id);
			return;
		}
		String sJSON = null;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream());
			ServerMessage sm = new ServerMessage(ServerMessage.MT_INIT, null, null);
			out.println(sm.toJSON().toString());
			out.flush();
			ClientMessage cm = new ClientMessage();
			sJSON = in.readLine();
			cm.fromJSON(new JSONObject(sJSON));
			player.name = cm.botName;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException | JSONClassCheckException e) {
			System.err.printf("Some troubles with parsing JSON: \"%s\"\n", sJSON);
			e.printStackTrace();
		} finally {
			if(player.name == null)
				player.name = "";
		}
	}

	/**
	 * Send to client serialization of World instance and wait to its response,
	 *	which includes changes in controls which manages by client AI
	 */
	@Override
	public void run() {
		if(client == null)
			return;
		if (!client.isClosed() && player.isAlive() && !player.crashed) {
				ServerMessage sm = new ServerMessage(ServerMessage.MT_TICK, Runner.inst().world, player.vehicles.get(0));
				out.println(sm.toJSON().toString());
				out.flush();
				String sJSON = null;
				response = null;
				try {
					sJSON = in.readLine();
					JSONObject rjson = new JSONObject(sJSON);
					cm.fromJSON(rjson);
					response = cm.botAction;
				} catch (JSONException | JSONClassCheckException e) {
					System.err.printf("Some troubles with parsing JSON: \"%s\"\n", sJSON);
					e.printStackTrace();
				} catch (IOException e) {
					if(e.getClass() == SocketTimeoutException.class) {
						System.out.printf("Strategy crashed by timeout, player=\"%s\"#%d\n",
								player.name, player.id);
						player.crashed = true;
					}
					e.printStackTrace();
				} finally {
					if(response == null)
						response = new BotAction(0, 0);
				}
		} else if(response == null)
			response = new BotAction(0, 0);
		else
			response.assign(0, 0);
	}
	
	/**
	 * Send to client endgame message and closes it
	 */
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
