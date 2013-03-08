package game.physics.objects;

import game.Runner;
import game.engine.Settings;
import game.engine.Tickable;
import game.engine.TipPlacer;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bonus extends Circle implements Serializable, JSONSerializable, Tickable {
	private static final long serialVersionUID = 8357488035574848722L;
	protected int bornTime;
	public int lifeTime = Settings.Bonus.defaultLifeTime;
	public BonusType type;
	protected static BufferedImage imageNitro = null,
			imageMedkit = null,
			imageFlag = null;

	public Bonus(BonusType bonusType) {
		super(Settings.BonusSpawner.defaultRadius);
		type = bonusType;
		this.bornTime = Runner.inst().tick;
	}
	
	protected String typeToString(BonusType bonusType) {
		switch (type) {
		case MED_KIT:
			return "MED_KIT";
		case FLAG:
			return "FLAG";
		case NITRO_FUEL:
			return "NITRO_FUEL";
		default:
			return "";
		}
	}
	
	protected BonusType stringToType(String string) {
		if(string.equals("MED_KIT"))
			return BonusType.MED_KIT;
		else if(string.equals("FLAG"))
			return BonusType.FLAG;
		else if(string.equals("NITRO_FUEL"))
			return BonusType.NITRO_FUEL;
		else {
			System.err.println("Bonus.stringToType()::Cant recognize BonusType name");
			return BonusType.FLAG;
		}
	}
	
	public static void prepareImages() {
		if(imageNitro == null || imageMedkit == null || imageFlag == null) {
			try {
				URL url = Runner.class.getResource("/res/nitro1.png");
				imageNitro = ImageIO.read(url);
				url = Runner.class.getResource("/res/medkit1.png");
				imageMedkit = ImageIO.read(url);
				url = Runner.class.getResource("/res/flag1.png");
				imageFlag = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		default:
			break;
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

	public void draw(Graphics2D graphics) {
		switch(type) {
			case MED_KIT:
				if(Settings.Renderer.drawImages) {
					prepareImages();
					AffineTransform oldTransform = graphics.getTransform();
					graphics.translate(position.x, position.y);
					//graphics.rotate(angle);
					graphics.drawImage(imageMedkit, (int)-radius, (int)-radius,
							(int)(2*radius), (int)(2*radius), null);
					graphics.setTransform(oldTransform);
				}
				else {
					AffineTransform oldTransform = graphics.getTransform();
					graphics.translate(position.x-radius, position.y-radius);
					graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
					graphics.drawLine((int)(position.x-radius), (int)position.y, (int)(position.x+radius), (int)position.y);
					graphics.drawLine((int)position.x, (int)(position.y-radius), (int)position.x, (int)(position.y+radius));
					graphics.setTransform(oldTransform);
				}
				break;
			case FLAG:
				if(Settings.Renderer.drawImages) {
					prepareImages();
					AffineTransform oldTransform = graphics.getTransform();
					graphics.translate(position.x, position.y);
					//graphics.rotate(angle);
					graphics.drawImage(imageFlag, (int)-radius, (int)-radius,
							(int)(2*radius), (int)(2*radius), null);
					graphics.setTransform(oldTransform);
				}
				else {
					AffineTransform oldTransform = graphics.getTransform();
					graphics.translate(position.x-radius, position.y-radius);
					graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
					graphics.drawLine((int)position.x, (int)(position.y-radius), (int)position.x, (int)(position.y+radius));
					graphics.setTransform(oldTransform);
				}
				break;
			case NITRO_FUEL:
				if(Settings.Renderer.drawImages) {
					prepareImages();
					AffineTransform oldTransform = graphics.getTransform();
					graphics.translate(position.x, position.y);
					//graphics.rotate(angle);
					graphics.drawImage(imageNitro, (int)-radius, (int)-radius,
							(int)(2*radius), (int)(2*radius), null);
					graphics.setTransform(oldTransform);
				}
				else {
					AffineTransform oldTransform = graphics.getTransform();
					graphics.translate(position.x-radius, position.y-radius);
					graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
					graphics.drawString("N", (float)position.x, (float)position.y);
					graphics.setTransform(oldTransform);
				}
				break;
			default:
				break;
		}
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
