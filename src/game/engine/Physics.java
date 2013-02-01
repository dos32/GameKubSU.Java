package game.engine;

import game.model.Unit;

public final class Physics {
	public final Runner runner;

	public Physics(Runner runner) {
		this.runner = runner;
	}

	public void tick() {
		// TODO add collisions
		
		for (Unit unit : runner.world.allUnits)
		{
			// Inertial moving:
			unit.position.add(unit.speed);
			unit.angle+=unit.angularSpeed;
			
			// Friction:
			unit.speed.add(unit.speed.mul(-unit.frictionCoeff/*unit.getSquare()*/));
			unit.angularSpeed-=unit.frictionCoeff*unit.angularSpeed;
		}
		
		runner.renderer.updated = true;
	}
}
