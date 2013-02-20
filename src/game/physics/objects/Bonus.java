package game.physics.objects;

import game.engine.Settings;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Bonus extends Circle implements Serializable {
	private static final long serialVersionUID = 8357488035574848722L;
	protected BonusType type;

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
}
