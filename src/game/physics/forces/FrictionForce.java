package game.physics.forces;

import game.physics.objects.Unit;

public class FrictionForce extends GlobalForce {

	@Override
	public void apply(Unit unit) {
		if (!unit.isStatic) {
			// According to the physial law: F = -kmSv, but in this case we must normalize mass and square,
			//  so we confine on formula F = -kv
			unit.speed.scale(1 - unit.frictionCoeff);
			unit.angularSpeed *= (1 - unit.frictionCoeff);
		}
	}

}
