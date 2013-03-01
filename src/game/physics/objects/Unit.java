package game.physics.objects;

import java.io.Serializable;
import java.util.ArrayList;

import game.Runner;
import game.engine.Settings;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;
import game.physics.colliders.hooks.CollideEventHook;
import game.physics.forces.BindedForce;
import game.utils.*;

/*
 * Base class for physical object
 */
public abstract class Unit implements Serializable, JSONSerializable {
	private static final long serialVersionUID = 1714619678919428922L;
	
	protected static int lastid = 1;
	public final int id;
	public Vector2d position = new Vector2d();
	public Vector2d speed = new Vector2d();
	public double angle = 0;
	public double angularSpeed = 0;
	
	public transient ArrayList<BindedForce> bindedForces = null; // beware of null !
	public transient ArrayList<CollideEventHook> collideEventHooks = null; // beware of null !
	
	/**
	 * When object becomes static it
	 * 	ignores self speed, friction and all collisions
	 */
	public boolean isStatic = false;
	
	/**
	 * When object becomes not material it
	 * 	ignores all collisions
	 */
	public boolean isMaterial = true;
	
	/** 
	 * Physical mass of an object
	 */
	public double mass = Settings.Physics.defaultMass;
	
	/**
	 * Friction coefficient of an object
	 */
	public double frictionCoeff = Settings.Physics.defaultFrictionCoeff;

	public Unit() {
		//this.zIndex = zIndex;
		id = lastid++;
		Runner.inst().addUnit(this);
	}
	
	public void dispose() {
		Runner.inst().removeUnit(this);
	}
	
	/**
	 * Prepares all necessary images
	 */
	public static void prepareImages() {
		
	}
	
	// JSON part:
	
	@Override
	public String getClassName() {
		return "Unit";
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("class", this.getClassName());
		json.put("id", id);
		json.put("position", position.toJSON());
		json.put("speed", speed.toJSON());
		json.put("angle", angle);
		json.put("angularSpeed", angularSpeed);
		return json;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		if(!json.has("class") || !json.getString("class").equals(getClassName()))
			throw new JSONClassCheckException(getClassName());
		//id = (int)json.get("id");
		position.fromJSON(json.getJSONObject("position"));
		speed.fromJSON(json.getJSONObject("speed"));
		angle = json.getDouble("angle");
		angularSpeed = json.getDouble("angularSpeed");
	}
	
}
