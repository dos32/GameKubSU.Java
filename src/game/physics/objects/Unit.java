package game.physics.objects;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import game.Runner;
import game.engine.Settings;
import game.physics.colliders.hooks.CollideEventHook;
import game.physics.forces.BindedForce;
import game.utils.*;

/*
 * Base class for physical object
 */
public abstract class Unit implements Serializable, Comparable<Unit> {
	private static final long serialVersionUID = 1714619678919428922L;
	
	protected static int lastid = 1;
	public final int id;
	public Vector2d position = new Vector2d();
	public Vector2d speed = new Vector2d();
	public double angle = 0;
	public double angularSpeed = 0;
	public transient int zIndex = 0;
	
	public transient ArrayList<BindedForce> bindedForces = null; // beware of null !
	public transient ArrayList<CollideEventHook> collideEventHooks = null; // beware of null !
	
	/*
	 * When object becomes static it
	 * 	ignores self speed, friction and all collisions
	 */
	public boolean isStatic = false;
	
	/*
	 * When object becomes not material it
	 * 	ignores all collisions
	 */
	public boolean isMaterial = true;
	
	// Physical mass of an object
	public double mass = Settings.Physics.defaultMass;
	
	// Friction coefficient of an object
	public double frictionCoeff = Settings.Physics.defaultFrictionCoeff;

	public Unit() {
		id = lastid;
		lastid++;
		Runner.inst().addUnit(this);
	}

	public Unit(double mass) {
		this();
		this.mass = mass;
	}
	
	public void dispose() {
		Runner.inst().removeUnit(this);
	}
	
	public static void loadImages() {
		
	}

	public abstract void draw(Graphics2D graphics);
	
	@Deprecated
	public abstract double getSquare();
	
	@Override
	public int compareTo(Unit unit) {
		int res = this.zIndex - unit.zIndex;
		if(res==0)
			res= this.id-unit.id;
		if(res == 0 && this!=unit)
			System.err.println("Unit.compareTo()::res==0");
		return res;
	}
}
