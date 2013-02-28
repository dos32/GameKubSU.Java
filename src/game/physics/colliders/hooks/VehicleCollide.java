package game.physics.colliders.hooks;

import game.engine.Settings;
import game.engine.TipPlacer;
import game.physics.objects.AnimatedTip;
import game.physics.objects.Bonus;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;

public class VehicleCollide extends CollideEventHook {
	public VehicleCollide(Vehicle attachedObject) {
		super(attachedObject);
	}

	@Override
	public boolean onEvent(Unit secondObject, double penetrationDepth) {
		if(secondObject instanceof Bonus) {
			((Bonus)secondObject).collect((Vehicle)attachedObject);
			return true;
		}
		else {
			int healthDelta = (int)Math.round(((Vehicle)attachedObject).changeHealth(-penetrationDepth*Settings.Vehicle.damageFactor));
			if(Math.abs(healthDelta) > 0)
				TipPlacer.placeTip(new AnimatedTip(String.format("%+d", healthDelta), Settings.AnimatedTip.dmgColor), attachedObject.position);
			return false;
		}
	}

}
