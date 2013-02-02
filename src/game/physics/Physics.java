package game.physics;

import java.util.ArrayList;

import game.Runner;
import game.physics.forces.GlobalForce;
import game.physics.objects.Unit;

public final class Physics {
	public final Runner runner;
	public ArrayList<GlobalForce> globalForces = new ArrayList<GlobalForce>();

	public Physics(Runner runner) {
		this.runner = runner;
	}

	public void tick() {
		// TODO add collisions
		
		for (Unit unit : runner.world.allUnits)
		{
			// Inertial moving:
			if (!unit.isStatic) {
				unit.position.add(unit.speed);
				unit.angle += unit.angularSpeed;
			}

			/*
			// Friction:
			if (!unit.isStatic) {
				//unit.getSquare()*unit.mass
				unit.speed.scale(1-unit.frictionCoeff);
				unit.angularSpeed *= (1-unit.frictionCoeff);
			}*/
			
			// Forces:
			for(GlobalForce force : globalForces)
				force.apply(unit);
		}
		
		runner.renderer.updated = true;
	}
}
