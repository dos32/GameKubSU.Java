package game.physics.forces;

import game.Runner;

public abstract class GlobalForce {
	public final Runner runner;
	
	public GlobalForce(Runner runner) {
		this.runner=runner;
	}
	
	public abstract void apply();
}
