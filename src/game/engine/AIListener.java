package game.engine;

import game.physics.World;

import java.net.*;

@SuppressWarnings("unused")
public final class AIListener {// AIImplementer
	public int port;
	public String ip;
	public World world;

	protected void sendRequest() {
		// TODO
	}

	protected void getResponse() {
		// TODO
	}

	/*
	 * Send to all client serialization of World object and wait it to response,
	 * which includes changes in controls which manages by client AI
	 */
	public void tick() {
		// TODO
	}
}
