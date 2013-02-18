package game.physics;

import java.util.ArrayList;

import game.Runner;
import game.engine.Settings;
import game.physics.forces.BindedForce;
import game.physics.forces.CollideForce;
import game.physics.forces.FrictionForce;
import game.physics.forces.GlobalForce;
import game.physics.forces.GravityForce;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public final class Physics {
	protected int ticks;
	protected long ticksCount = 0, innerTime = 0, lastRealTime = 0;
	protected double fps = 0;
	
	public ArrayList<Unit> objects = new ArrayList<Unit>();
	
	public ArrayList<GlobalForce> globalForces = new ArrayList<GlobalForce>();
	
	public ArrayList<BindedForce> bindedForces = new ArrayList<BindedForce>();

	public Physics() {
		globalForces.add(new FrictionForce());
		globalForces.add(new GravityForce(new Vector2d(0, 5e-4)));
		globalForces.add(new CollideForce());
	}
	
	public void updateFPS() {
		if(Runner.inst().infoPhysFPS == null)
			return;
		if(fps == 0)
			Runner.inst().infoPhysFPS.message = String.format("Phys.FPS = %s", "n/a");
		else {
			Runner.inst().infoPhysFPS.message = String.format("Phys.FPS = %s", Math.round(fps));
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
		for(BindedForce force : bindedForces)
			force.apply();
		
		for(GlobalForce force : globalForces)
			force.apply(objects);
		
		Runner.inst().renderer.updated = true;
		
		innerTime+=System.nanoTime()-t1;
		ticksCount++;
		if(System.nanoTime() - lastRealTime > Settings.PerfMonitor.FPS.realTimeSpan) {
			lastRealTime = System.nanoTime();
			fps = ticksCount * 1e9 / innerTime;
			updateFPS();
			if(ticksCount > Settings.PerfMonitor.FPS.resetPeriod) {
				ticksCount = 0;
				innerTime = 0;
			}
		}
	}
}
