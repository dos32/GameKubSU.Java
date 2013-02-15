package game.physics.forces;

import game.physics.objects.Unit;

public abstract class BindedForce {
	public Unit bindUnit;
	
	public BindedForce(Unit bindUnit) {
		this.bindUnit = bindUnit;
	}

	public abstract void apply();
}
