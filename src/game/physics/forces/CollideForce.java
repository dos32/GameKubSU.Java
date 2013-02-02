package game.physics.forces;

import game.Runner;
import game.physics.objects.Circle;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class CollideForce extends GlobalForce {
	public final Runner runner;

	public CollideForce(Runner runner) {
		this.runner = runner;
	}

	@Override
	public void apply(Unit unit) {
		// TODO +law keeping of impulse 
		if (unit.getClass() == Circle.class) {
			for (Unit unit1 : runner.world.allUnits) {
				if (unit1.getClass() == Circle.class && unit != unit1) {
					Vector2d dr = unit.position.diff(unit1.position);
					if (dr.norm() < ((Circle) unit).radius + ((Circle) unit1).radius) {
						dr.scale(unit1.collideCoeff / dr.square());
						unit.speed.add(dr);
					}
				}
			}
		}
	}

}
