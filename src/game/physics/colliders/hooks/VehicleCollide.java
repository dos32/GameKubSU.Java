package game.physics.colliders.hooks;

import game.engine.Settings;
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
			((Vehicle)attachedObject).doDamage(penetrationDepth*Settings.Vehicle.damageFactor);
			return false;
		}
	}

}
