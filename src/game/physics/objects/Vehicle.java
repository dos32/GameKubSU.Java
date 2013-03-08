package game.physics.objects;

import game.Runner;
import game.engine.Player;
import game.engine.Settings;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;
import game.physics.colliders.hooks.CollideEventHook;
import game.physics.colliders.hooks.VehicleCollide;
import game.physics.forces.BindedForce;
import game.physics.forces.ControlForce;
import game.utils.Vector2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Vehicle extends Circle implements Serializable, JSONSerializable {
	private static final long serialVersionUID = 1563688715048158514L;
	
	private static final int imagesCount = 6;
	private static transient BufferedImage[] images = new BufferedImage[imagesCount];
	private static transient Color[] imageColors = new Color[imagesCount];
	
	public transient Player player;
	private static int lastColorId = 0;
	protected int colorId;
	protected int indexInTeam;
	protected int playerId;
	protected boolean isTeammate;
	protected transient Color color = Color.black;

	public double health = Settings.Vehicle.maxHealth;
	public double nitro = 0;
	
	public transient ControlForce engine;
	
	private Vehicle() {
		super(Settings.Vehicle.defaultRadius);
		if(lastColorId >= images.length)
			System.err.println("There are not so much colors for vehicles");
		colorId = (lastColorId++ % imagesCount);
	}
	
	public Vehicle(Player player) {
		this();
		this.player = player;
		player.vehicles.add(this);
		new ControlForce(this);
		bindedForces = new ArrayList<BindedForce>();
		bindedForces.add(engine);
		collideEventHooks = new ArrayList<CollideEventHook>();
		collideEventHooks.add(new VehicleCollide(this));
	}
	
	/**
	 * Changes nitro value by delta; constraints it
	 * accordingly settings
	 * @param	delta Desirable change of nitro value
	 * @return	Factual change of nitro under constraints
	 */
	public double changeNitro(double delta) {
		double oldNitroLevel = nitro;
		nitro = Math.max(0, Math.min(nitro + delta, Settings.Vehicle.maxNitro));
		return (nitro - oldNitroLevel);
	}
	
	/**
	 * Changes health value by delta; constraints it
	 * accordingly settings
	 * @param	delta Desirable change of health value
	 * @return	Factual change of health under constraints
	 */
	public double changeHealth(double delta) {
		double oldHealthLevel = health;
		health = Math.min(Math.max(0, health + delta), Settings.Vehicle.maxHealth);
		return (health - oldHealthLevel);
	}
	
	/**
	 * Does same that changeHealth, but not change
	 * value of health
	 * @param delta
	 * @return	Factual possible change of health under constraints
	 */
	public double getHealthChange(double delta) {
		double newHealthLevel = Math.min(Math.max(0, health + delta), Settings.Vehicle.maxHealth);
		return (newHealthLevel - health);
	}
	
	/**
	 * Just adds goal points and changes player score
	 * @param ptsCount
	 * @return	Factual change of score points under constraints
	 */
	public int addGoalPoints(int ptsCount) {
		player.changeScore(ptsCount);
		return ptsCount;
	}
	
	public static void prepareImages() {
		if(images[0] == null) {
			try {
				for(int i = 0; i < imagesCount; i++) {
					URL url = Runner.class.getResource(String.format("/res/car%s.png", i+1));
					images[i] = ImageIO.read(url);
					imageColors[i] = new Color(images[i].getRGB(images[i].getWidth()/2, images[i].getHeight()/2));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		Color oldColor = graphics.getColor();
		if(Settings.Renderer.drawImages) {
			prepareImages();
			AffineTransform oldTransform = graphics.getTransform();
			graphics.translate(position.x, position.y);
			graphics.rotate(angle+Math.PI/2);
			graphics.drawImage(images[colorId], (int)-radius, (int)-radius,
					(int)(2*radius), (int)(2*radius), null);
			graphics.setTransform(oldTransform);
		}
		else {
			graphics.setColor(color);
			super.draw(graphics);
			Vector2d n = new Vector2d(Math.sin(angle), Math.cos(angle));
			n.scale(radius);
			n.add(position);
			graphics.drawLine((int)position.x, (int)position.y, (int)n.x, (int)n.y);
		}
		// Healthbar
		graphics.setColor(Settings.Vehicle.HealthBar.defaultColor);
		graphics.fillRect((int)(position.x-radius),
				(int)(position.y-radius-Settings.Vehicle.HealthBar.descent-Settings.Vehicle.HealthBar.height-Settings.Vehicle.NitroBar.height),
				(int)(2*radius*health/Settings.Vehicle.maxHealth), Settings.Vehicle.HealthBar.height);
		graphics.setColor(Settings.Vehicle.HealthBar.borderColor);
		graphics.drawRect((int)(position.x-radius),
				(int)(position.y-radius-Settings.Vehicle.HealthBar.descent-Settings.Vehicle.HealthBar.height-Settings.Vehicle.NitroBar.height),
				(int)(2*radius), Settings.Vehicle.HealthBar.height);
		// Nitrobar
		graphics.setColor(Settings.Vehicle.NitroBar.defaultColor);
		graphics.fillRect((int)(position.x-radius), (int)(position.y-radius-Settings.Vehicle.NitroBar.descent-Settings.Vehicle.NitroBar.height),
				(int)(2*radius*nitro/Settings.Vehicle.maxNitro), Settings.Vehicle.NitroBar.height);
		graphics.setColor(Settings.Vehicle.NitroBar.borderColor);
		graphics.drawRect((int)(position.x-radius), (int)(position.y-radius-Settings.Vehicle.NitroBar.descent-Settings.Vehicle.NitroBar.height),
				(int)(2*radius), Settings.Vehicle.NitroBar.height);
		
		graphics.setColor(oldColor);
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		json.put("index", indexInTeam);
		json.put("health", health);
		json.put("nitro", nitro);
		json.put("playerId", playerId);
		json.put("playerName", player.name);
		json.put("isTeammate", isTeammate);
		return json;
	}
	
	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
		indexInTeam = json.getInt("index");
		health = json.getDouble("health");
		nitro = json.getDouble("nitro");
		playerId = json.getInt("playerId");
		isTeammate = json.getBoolean("isTeammate");
	}
	
	@Override
	public String getClassName() {
		return "Vehicle";
	}

	public Color getColor() {
		return imageColors[colorId];
	}

}
