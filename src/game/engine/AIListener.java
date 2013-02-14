package game.engine;

import game.Runner;
import game.physics.World;

import java.net.*;

@SuppressWarnings("unused")
public final class AIListener {
	Runner runner;
	public int port;
	public String ip;
	
	public AIListener(Runner runner) {
		this.runner = runner;
	}

	/*
	 * Send to all client serialization of World object and wait it to response,
	 * which includes changes in controls which manages by client AI
	 */
	public void tick() {
		// TODO sale place for code there :)
	}
}
