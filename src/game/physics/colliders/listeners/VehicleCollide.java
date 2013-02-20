package game.physics.colliders.listeners;

import game.Runner;
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
			Runner.inst().removeUnit(secondObject);
			return true;
		}
		else {
			((Vehicle)attachedObject).doDamage(penetrationDepth*Settings.Vehicle.damageFactor);
			return false;
		}
	}

}
