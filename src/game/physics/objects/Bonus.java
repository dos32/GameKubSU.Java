package game.physics.objects;

import game.Runner;
import game.engine.Settings;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Bonus extends Circle implements Serializable {
	private static final long serialVersionUID = 8357488035574848722L;
	protected BonusType type;

	public Bonus(BonusType bonusType) {
		super(Settings.BonusSpawner.defaultRadius);
		isMaterial = false;
		type = bonusType;
		for(int i=0; i<Settings.BonusSpawner.placementTries; i++) {
			boolean isCollide = false;
			for(Unit unit : Runner.inst().physics.objects)
				if(unit!=this)
					isCollide |= Runner.inst().physics.collideForce.isCollide(this, unit);
			if(!isCollide)
				break;
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		// TODO Auto-generated method stub
		super.draw(graphics);
	}
}
