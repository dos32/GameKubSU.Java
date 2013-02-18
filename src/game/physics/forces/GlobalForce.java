package game.physics.forces;

import java.util.List;

import game.physics.objects.Unit;

public abstract class GlobalForce {
	
	// TODO add auto adding into Physics.globalForces
	public GlobalForce() {
	}
	
	public abstract void apply(List<Unit> objects);
}
