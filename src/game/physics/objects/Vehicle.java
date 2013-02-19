package game.physics.objects;

import game.engine.Player;
import game.engine.Settings;
import game.physics.colliders.listeners.CollideEventListener;
import game.physics.colliders.listeners.VehicleCollide;
import game.physics.forces.BindedForce;
import game.physics.forces.ControlForce;
import game.utils.Vector2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle extends Circle implements Serializable {
	private static final long serialVersionUID = 1563688715048158514L;
	// TODO add bind
	public transient Player player;
	protected int indexInTeam;
	protected int playerId;
	protected String playerName;
	protected boolean isTeammate;
	protected Color color = Color.black;

	protected double health = 0.5;
	/*protected double armor;
	protected double fuel;
	protected double nitroFuel;*/
	
	public transient ControlForce engine;
	
	private Vehicle() {
		super(0);
	}
	
	public Vehicle(Player player) {
		super(Settings.Vehicle.defaultRadius);
		this.player = player;
		player.vehicles.add(this);
		new ControlForce(this);
		bindedForces = new ArrayList<BindedForce>();
		bindedForces.add(engine);
		collideEventListeners = new ArrayList<CollideEventListener>();
		collideEventListeners.add(new VehicleCollide(this));
	}
	
	public void doDamage(double healthDelta) {
		health = Math.min(Math.max(0, health - healthDelta), 1);
		// TODO add animation
	}
	
	public void addGoalPoints(int ptsCount) {
		player.score += ptsCount;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		Color oldColor = graphics.getColor();
		
		graphics.setColor(Settings.Vehicle.HealthBar.defaultColor);
		graphics.fillRect((int)(position.x-radius), (int)(position.y-radius-Settings.Vehicle.HealthBar.descent-Settings.Vehicle.HealthBar.height),
				(int)(health*2*radius), Settings.Vehicle.HealthBar.height);
		
		graphics.setColor(color);
		Vector2d n = new Vector2d(Math.sin(angle), Math.cos(angle));
		n.scale(radius);
		n.add(position);
		graphics.drawLine((int)position.x, (int)position.y, (int)n.x, (int)n.y);
		
		graphics.drawRect((int)(position.x-radius), (int)(position.y-radius-Settings.Vehicle.HealthBar.descent-Settings.Vehicle.HealthBar.height),
				(int)(2*radius), Settings.Vehicle.HealthBar.height);
		
		graphics.setColor(oldColor);
	}

}
