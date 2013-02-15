package game.physics.forces;

import java.util.List;

import game.Runner;
import game.physics.objects.Unit;

public abstract class GlobalForce {
	public final Runner runner;
	
	// TODO add auto adding into Physics.globalForces
	public GlobalForce(Runner runner) {
		this.runner=runner;
	}
	
	public abstract void apply(List<Unit> objects);
}
