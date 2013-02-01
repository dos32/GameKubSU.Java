package game.model;

import java.awt.Graphics2D;

import game.engine.Settings;
import game.utils.*;

public abstract class Unit {
	protected static int lastid = 1;
	public final int id;
	public Vector2d position = new Vector2d();
	public Vector2d speed = new Vector2d();
	public double angle = 0;
	public double angularSpeed = 0;
	
	/*
	 * When object becomes static it
	 * ignores self speed, friction and all collisions
	 */
	public boolean isStatic = false;
	
	/*
	 * When object becomes not material it
	 * ignores all collisions
	 */
	public boolean isMaterial = true;
	
	// Physical mass of an object
	public double mass;
	
	// Friction coefficient of an object
	public double frictionCoeff = Settings.Physics.defaultFrictionCoeff;
	public double elasticityCoeff = Settings.Physics.defaultElasticityCoeff;

	public Unit() {
		id = lastid;
		lastid++;
	}

	public Unit(double mass) {
		this();
		this.mass = mass;
	}

	public int getId() {
		return id;
	}

	public Vector2d getPosition() {
		return position;
	}

	public Vector2d getSpeed() {
		return speed;
	}

	public double getAngle() {
		return angle;
	}

	public double getAngularSpeed() {
		return angularSpeed;
	}

	protected double getMass() {
		return mass;
	}

	public abstract void draw(Graphics2D graphics);
	
	public abstract double getSquare();
	
	public abstract double getPenetrationDepth(Unit unit);

	public abstract Vector2d getNorm(Unit unit);
	
	//public abstract Vector2d calcForce();
	
	//public abstract boolean isCollide(Unit unit);
}
