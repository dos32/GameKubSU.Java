package game.engine;

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
		if(Math.random() < Settings.BonusSpawner.probability) {
			new Bonus(genType());
		}
	}
}
