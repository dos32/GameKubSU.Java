package game.physics;

import java.util.ArrayList;

import game.Runner;
import game.physics.forces.CollideForce;
import game.physics.forces.FrictionForce;
import game.physics.forces.GlobalForce;
import game.physics.objects.Unit;

public final class Physics {
	public final Runner runner;
	public ArrayList<GlobalForce> globalForces = new ArrayList<GlobalForce>();

	public Physics(Runner runner) {
		this.runner = runner;
		globalForces.add(new FrictionForce(runner));
		globalForces.add(new CollideForce(runner));
	}

	public void tick() {
		for (Unit unit : runner.world.allUnits)
		{
			// Inertial moving:
			if (!unit.isStatic) {
				unit.position.add(unit.speed);
				unit.angle += unit.angularSpeed;
			}
		}
		
		// Forces:
		for(GlobalForce force : globalForces)
			force.apply();
		
		runner.renderer.updated = true;
	}
}
