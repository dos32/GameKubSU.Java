package game.physics;

import java.util.ArrayList;

import game.Runner;
import game.engine.Settings;
import game.physics.forces.CollideForce;
import game.physics.forces.FrictionForce;
import game.physics.forces.GlobalForce;
import game.physics.forces.GravityForce;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public final class Physics {
	public final Runner runner;
	protected int ticks;
	protected long ticksCount = 0, time = 0;
	protected double fps = 0;
	
	public ArrayList<Unit> objects = new ArrayList<Unit>();
	
	public ArrayList<GlobalForce> globalForces = new ArrayList<GlobalForce>();

	public Physics(Runner runner) {
		this.runner = runner;
		globalForces.add(new FrictionForce(runner));
		// simple demo for gravity force:
		globalForces.add(new GravityForce(runner, new Vector2d(0, 5e-4)));
		globalForces.add(new CollideForce(runner));
	}
	
	public void updateFPS() {
		if(fps == 0)
			runner.infoPhysFPS.message = String.format("Phys.FPS = %s", "n/a");
		else {
			runner.infoPhysFPS.message = String.format("Phys.FPS = %s", Math.round(fps));
		}
	}
	
	public void addUnit(Unit unit) {
		objects.add(unit);
	}
	
	public void clearUnits() {
		objects.clear();
	}

	public void tick() {
		long t1 = System.nanoTime();
		
		for (Unit unit : objects)
		{
			// Inertial moving:
			if (!unit.isStatic) {
				unit.position.add(unit.speed);
				unit.angle += unit.angularSpeed;
			}
		}
		
		// Forces:
		for(GlobalForce force : globalForces)
			force.apply(objects);
		
		runner.renderer.updated = true;
		
		time+=System.nanoTime()-t1;
		ticksCount++;
		if(ticksCount>Settings.Physics.FPSFramesCount) {
			fps = ticksCount * 1e9 / time;
			updateFPS();
			ticksCount = 0;
			time = 0;
		}
	}
}
