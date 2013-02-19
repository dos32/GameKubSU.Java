package game.engine;

import game.Runner;
import game.physics.objects.Bonus;
import game.physics.objects.BonusType;
import game.physics.objects.Unit;

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

	public void place(Bonus bonus) {
		for(int i=0; i<Settings.BonusSpawner.placementTries; i++) {
			bonus.position.assign(Runner.inst().world.width*Math.random(),
				Runner.inst().world.height*Math.random());
			boolean isCollide = false;
			for(Unit unit : Runner.inst().physics.objects)
				if(unit != bonus)
					isCollide |= Runner.inst().physics.collideForce.isCollide(bonus, unit);
			if(!isCollide)
				break;
		}
	}
	
	public void tick() {
		if(Math.random() > 1-Settings.BonusSpawner.probability) {
			place(new Bonus(genType()));
		}
	}
}
