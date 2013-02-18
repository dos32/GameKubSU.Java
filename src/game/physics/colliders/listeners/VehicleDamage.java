package game.physics.colliders.listeners;

import game.engine.Settings;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;

public class VehicleDamage extends CollideEventListener {
	public VehicleDamage(Vehicle attachedObject) {
		super(attachedObject);
	}

	@Override
	public void onEvent(Unit secondObject, double penetrationDepth) {
		((Vehicle)attachedObject).doDamage(penetrationDepth*Settings.Vehicle.damageFactor);
	}

}
