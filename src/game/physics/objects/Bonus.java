package game.physics.objects;

import game.engine.Settings;
import game.json.JSONClassCheckException;
import game.json.JSONObject;
import game.json.JSONSerializable;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Bonus extends Circle implements Serializable, JSONSerializable {
	private static final long serialVersionUID = 8357488035574848722L;
	public BonusType type;
	
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

	public Bonus(BonusType bonusType) {
		super(Settings.BonusSpawner.defaultRadius);
		type = bonusType;
	}
	
	public void collect(Vehicle vehicle) {
		switch (type) {
		case FLAG:
			vehicle.addGoalPoints(Settings.Bonus.Flag.goalPoints);
			break;
		case MED_KIT:
			vehicle.doDamage(-Settings.Bonus.Medkit.healthSize);
			vehicle.addGoalPoints(Settings.Bonus.Medkit.goalPoints);
			break;
		default:
			break;
		}
		dispose();
	}

	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		switch(type) {
			case MED_KIT:
				graphics.drawLine((int)(position.x-radius), (int)position.y, (int)(position.x+radius), (int)position.y);
				graphics.drawLine((int)position.x, (int)(position.y-radius), (int)position.x, (int)(position.y+radius));
				break;
			case FLAG:
				graphics.drawLine((int)position.x, (int)(position.y-radius), (int)position.x, (int)(position.y+radius));
				break;
			case NITRO_FUEL:
				break;
			case REPAIR_KIT:
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
}
