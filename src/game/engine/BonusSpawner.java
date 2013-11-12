package game.engine;

import game.Runner;
import game.physics.objects.Bonus;

public class BonusSpawner {
	
	public double probability() {
		if(Runner.inst().world.bonuses.size() == 0)
			return 1;
		else
			return (Math.random()-Settings.BonusSpawner.probability/2+1/Runner.inst().world.bonuses.size());
	}
	
	public void tick() {
		if(probability() > 1-Settings.BonusSpawner.probability) {
			Runner.inst().physics.collideForce.placeNoCollide(
					new Bonus(), Settings.BonusSpawner.placementTries);
		}
	}
}
