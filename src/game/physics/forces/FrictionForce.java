package game.physics.forces;

import game.Runner;
import game.physics.objects.Unit;

public class FrictionForce extends GlobalForce {

	public FrictionForce(Runner runner) {
		super(runner);
	}

	@Override
	public void apply() {
		for (Unit unit : runner.world.allUnits)
			if (!unit.isStatic) {
				// According to the physial law: F = -kmSv, but in this case we
				// must normalize mass and square,
				// so we confine on formula F = -kv
				unit.speed.scale(1 - unit.frictionCoeff);
				unit.angularSpeed *= (1 - unit.frictionCoeff);
			}
	}

}
