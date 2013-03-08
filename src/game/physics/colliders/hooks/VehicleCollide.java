package game.physics.colliders.hooks;

import java.util.ArrayList;

import game.engine.Settings;
import game.engine.TipPlacer;
import game.physics.objects.AnimatedTip;
import game.physics.objects.Bonus;
import game.physics.objects.InfoTip;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;

public class VehicleCollide implements CollideEventHook {
	public Vehicle attachedObject;

	public VehicleCollide(Vehicle attachedObject) {
		this.attachedObject = attachedObject;
	}

	@Override
	public boolean onEvent(Unit secondObject, double penetrationDepth) {
		if(secondObject instanceof Bonus) {
			((Bonus)secondObject).collect(attachedObject);
			return true;
		}
		else {
			ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
			int damage1 = -(int)Math.round(penetrationDepth*Settings.Vehicle.damageFactor);
			int healthDelta1 = (int)Math.round(attachedObject.changeHealth(damage1));
			if(Math.abs(healthDelta1) > 0)
				tips.add(new AnimatedTip(String.format("%+d", healthDelta1), Settings.AnimatedTip.dmgColor));
			
			if(secondObject instanceof Vehicle) {
				Vehicle vehicle2 = (Vehicle)secondObject;
				int damage2 = -(int)Math.round(penetrationDepth*Settings.Vehicle.damageFactor);
				int healthDelta2 = (int)Math.round(vehicle2.getHealthChange(damage2));
				int scoreDelta = attachedObject.addGoalPoints(Math.abs(healthDelta2));
				if(Math.abs(scoreDelta) > 0)
					tips.add(new AnimatedTip(String.format("%+d", scoreDelta), Settings.AnimatedTip.goalColor));
			}
			TipPlacer.placeTips(tips, attachedObject.position);
			return false;
		}
	}

}
