package game.physics.objects;

import game.physics.Physics;
import game.physics.forces.ControlForce;
import game.utils.Vector2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Vehicle extends Circle implements Serializable {
	private static final long serialVersionUID = 1563688715048158514L;
	protected int indexInTeam;
	protected int playerId;
	protected String playerName;
	protected boolean isTeammate;
	protected Color color;

	protected double health;
	protected double armor;
	protected double fuel;
	protected double nitroFuel;
	
	public ControlForce engine;
	
	private Vehicle() {
		super(0);
	}
	
	public Vehicle(Physics physics, double radius) {
		super(radius);
		new ControlForce(this);
		physics.bindedForces.add(engine);
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		Vector2d n = new Vector2d(Math.sin(angle), Math.cos(angle));
		n.scale(radius);
		n.add(position);
		graphics.drawLine((int)position.x, (int)position.y, (int)n.x, (int)n.y);
	}

}
