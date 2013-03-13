package game.physics;

import java.util.ArrayList;
import java.util.Iterator;

import game.Runner;
import game.engine.UnitContainer;
import game.engine.Settings;
import game.physics.forces.BindedForce;
import game.physics.forces.CollideForce;
import game.physics.forces.FrictionForce;
import game.physics.forces.GlobalForce;
import game.physics.forces.GravityForce;
import game.physics.objects.PerfTip;
import game.physics.objects.Unit;
import game.utils.Vector2d;

@SuppressWarnings("unused")
public final class Physics implements UnitContainer {
	protected int ticks;
	protected long ticksCount = 0, innerTime = 0, lastRealTime = 0;
	protected double fps = 0;
	public PerfTip fpsInfo = null;
	
	// List of all physics objects:
	public ArrayList<Unit> objects = new ArrayList<Unit>();
	
	// List for additional minor global forces:
	public ArrayList<GlobalForce> globalForces = new ArrayList<GlobalForce>();
	
	// Main global forces:
	public FrictionForce frictionForce = new FrictionForce();
	public CollideForce collideForce = new CollideForce();
	
	public void updateFPS() {
		if(fpsInfo == null)
			return;
		if(fps == 0)
			fpsInfo.message = String.format("Phys.FPS = %s", "n/a");
		else {
			fpsInfo.message = String.format("Phys.FPS = %s", Math.round(fps));
		}
	}

	@Override
	public void addUnit(Unit unit) {
		objects.add(unit);
	}

	@Override
	public void removeUnit(Unit unit) {
		objects.remove(unit);
	}

	@Override
	public void clearUnits() {
		objects.clear();
	}

	public void tick() {
		long t1 = System.nanoTime();
		
		// Inertial moving:
		for (Unit unit : objects) {
			if (!unit.isStatic) {
				unit.position.add(unit.speed);
				unit.angle += unit.angularSpeed;
			}
			if(unit.bindedForces != null)
				for(BindedForce force : unit.bindedForces)
					force.apply();
		}
		
		// Minor global forces:
		for(GlobalForce force : globalForces)
			force.apply(objects);
		
		frictionForce.apply(objects);
		collideForce.apply(objects);
		
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
