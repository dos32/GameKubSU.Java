package game.physics.forces;

import game.Runner;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class GravityForce extends GlobalForce {
	public Vector2d value = new Vector2d();

	public GravityForce(Runner runner, Vector2d value) {
		super(runner);
		this.value.assign(value);
	}

	@Override
	public void apply() {
		for(Unit unit : runner.world.allUnits)
			if(!unit.isStatic)
			unit.speed.add(value.mul(unit.mass));
	}

}
