package game.physics.forces;

import game.physics.objects.Unit;

public abstract class BindedForce {
	public Unit bindUnit;

	// TODO add auto adding into Physics.bindedForces
	public BindedForce(Unit bindUnit) {
		this.bindUnit = bindUnit;
	}

	public abstract void apply();
}
