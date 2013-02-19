package game.engine;

import game.Runner;
import game.physics.objects.Bonus;
import game.physics.objects.BonusType;

public class BonusSpawner {
	public BonusType genType() {
		double p = Math.random();
		if(p<Settings.BonusSpawner.pFlag)
			return BonusType.FLAG;
		else if(p<Settings.BonusSpawner.pFlag+Settings.BonusSpawner.pMedKit)
			return BonusType.MED_KIT;
		else
			return null;
	}
	
	public void tick() {
		if(Math.random() > 1-Settings.BonusSpawner.probability) {
			Runner.inst().physics.collideForce.placeNoCollide(new Bonus(genType()), Settings.BonusSpawner.placementTries);
		}
	}
}
