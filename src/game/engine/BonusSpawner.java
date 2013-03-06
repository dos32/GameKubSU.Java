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
			return BonusType.NITRO_FUEL;
	}
	
	public double probability() {
		if(Runner.inst().world.bonuses.size() == 0)
			return 1;
		else
			return (Math.random()-Settings.BonusSpawner.probability/2+1/Runner.inst().world.bonuses.size());
	}
	
	public void tick() {
		if(probability() > 1-Settings.BonusSpawner.probability) {
			Runner.inst().physics.collideForce.placeNoCollide(
					new Bonus(genType()), Settings.BonusSpawner.placementTries);
		}
	}
}
