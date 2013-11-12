package game.physics.objects;

import game.Runner;
import game.engine.Settings;
import game.engine.Tickable;
import game.engine.TipPlacer;
import game.graphics.Sprite;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;
import game.physics.effects.KamikadzeHalo;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;

public class Bonus extends Circle implements Serializable, JSONSerializable, Tickable {
	
	public enum Type {
		FLAG,
		MED_KIT,
		NITRO_FUEL,
		KAMIKADZE,
		BULLETS;
	}
	
	private static final long serialVersionUID = 8357488035574848722L;
	
	protected int bornTime;
	public int lifeTime = Settings.Bonus.defaultLifeTime;
	public Type type;
	private static Sprite[] sprites = new Sprite[] {
		new Sprite("/res/flag1.png"),
		new Sprite("/res/medkit1.png"),
		new Sprite("/res/nitro1.png"),
		new Sprite("/res/kamikadze1.png"),
		null
	};
	
	private Sprite getSprite() {
		return sprites[type.ordinal()];
	}
	
	public Bonus() {
		super(Settings.Bonus.defaultRadius);
		double p = Math.random();
		Bonus.Type[] types = Bonus.Type.values();
		for(int i = 0; i<types.length; i++) {
			p -= Settings.BonusSpawner.p[i];
			if(p <= 0) {
				type = types[i];
				break;
			}
		}
		if(p > 0)
			throw new RuntimeException();
		this.bornTime = Runner.inst().tick;
	}

	public Bonus(Type type) {
		super(Settings.Bonus.defaultRadius);
		this.type = type;
		this.bornTime = Runner.inst().tick;
	}
	
	protected String typeToString(Type bonusType) {
		switch (type) {
		case MED_KIT:
			return "MED_KIT";
		case FLAG:
			return "FLAG";
		case NITRO_FUEL:
			return "NITRO_FUEL";
		case KAMIKADZE:
			return "KAMIKADZE";
		case BULLETS:
			return "BULLETS";
		default:
			throw new RuntimeException();
		}
	}
	
	protected Type stringToType(String string) {
		if(string.equals("MED_KIT"))
			return Type.MED_KIT;
		else if(string.equals("FLAG"))
			return Type.FLAG;
		else if(string.equals("NITRO_FUEL"))
			return Type.NITRO_FUEL;
		else if(string.equals("KAMIKADZE"))
			return Type.KAMIKADZE;
		else if(string.equals("BULLETS"))
			return Type.BULLETS;
		else
			throw new RuntimeException();
	}
	
	public void collect(Vehicle vehicle) {
		int healthDelta = 0;
		int nitroDelta = 0;
		int scoreDelta = 0;
		switch (type) {
		case FLAG:
			scoreDelta = vehicle.addGoalPoints(Settings.Bonus.Flag.goalPoints);
			break;
		case MED_KIT:
			healthDelta = vehicle.changeHealth(Settings.Bonus.Medkit.healthSize);
			scoreDelta = vehicle.addGoalPoints(Settings.Bonus.Medkit.goalPoints);
			break;
		case NITRO_FUEL:
			nitroDelta = (int)Math.round(vehicle.changeNitro(Settings.Bonus.Nitro.nitroCount));
			scoreDelta = vehicle.addGoalPoints(Settings.Bonus.Nitro.goalPoints);
			break;
		case KAMIKADZE:
			if(vehicle.kamikadzeHalo == null)
				new KamikadzeHalo(vehicle);
			break;
		case BULLETS:
			// TODO
			break;
		default:
			throw new RuntimeException();
		}
		ArrayList<AnimatedTip> tips = new ArrayList<AnimatedTip>();
		if(Math.abs(healthDelta) > 0)
			tips.add(new AnimatedTip(String.format("%+d", healthDelta),
					(healthDelta > 0 ? Settings.AnimatedTip.healColor : Settings.AnimatedTip.dmgColor) ));
		if(Math.abs(nitroDelta) > 0)
			tips.add(new AnimatedTip(String.format("%+d", nitroDelta),
					Settings.AnimatedTip.nitroColor));
		if(Math.abs(scoreDelta) > 0)
			tips.add(new AnimatedTip(String.format("%+d", scoreDelta),
					Settings.AnimatedTip.goalColor));
		TipPlacer.placeTips(tips, vehicle.position);
		dispose();
	}
	
	private void drawPrimitive(Graphics2D graphics) {
		AffineTransform oldTransform = null;
		switch(type) {
		case MED_KIT:
			oldTransform = graphics.getTransform();
			graphics.translate(position.x-radius, position.y-radius);
			graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
			graphics.drawLine((int)(position.x-radius), (int)position.y, (int)(position.x+radius), (int)position.y);
			graphics.drawLine((int)position.x, (int)(position.y-radius), (int)position.x, (int)(position.y+radius));
			graphics.setTransform(oldTransform);
			break;
		case FLAG:
			oldTransform = graphics.getTransform();
			graphics.translate(position.x-radius, position.y-radius);
			graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
			graphics.drawLine((int)position.x, (int)(position.y-radius), (int)position.x, (int)(position.y+radius));
			graphics.setTransform(oldTransform);
			break;
		case NITRO_FUEL:
			oldTransform = graphics.getTransform();
			graphics.translate(position.x-radius, position.y-radius);
			graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
			graphics.drawString("N", (float)position.x, (float)position.y);
			graphics.setTransform(oldTransform);
			break;
		default:
			throw new RuntimeException();
		}
	}
	
	public void draw(Graphics2D graphics) {
		if(Settings.Renderer.drawImages) {
			AffineTransform oldTransform = graphics.getTransform();
			graphics.translate(position.x, position.y);
			//graphics.rotate(angle);
			graphics.drawImage(getSprite().getFrame(), (int)-radius, (int)-radius,
					(int)(2*radius), (int)(2*radius), null);
			graphics.setTransform(oldTransform);
		} else
			drawPrimitive(graphics);
	}
	
	@Override
	public String getClassName() {
		return "Bonus";
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		json.put("type", typeToString(type));
		return json;
	}

	@Override
	public void fromJSON(JSONObject json) throws JSONClassCheckException {
		super.fromJSON(json);
		this.type = stringToType(json.getString("type"));
	}

	@Override
	public void tick() {
		if(Runner.inst().tick - bornTime > lifeTime)
			dispose();
	}
}
