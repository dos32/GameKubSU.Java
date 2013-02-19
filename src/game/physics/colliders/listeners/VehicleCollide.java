package game.physics.colliders.listeners;

import game.Runner;
import game.engine.Settings;
import game.physics.objects.Bonus;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;

public class VehicleCollide extends CollideEventListener {
	public VehicleCollide(Vehicle attachedObject) {
		super(attachedObject);
	}

	@Override
	public void onEvent(Unit secondObject, double penetrationDepth) {
		if(secondObject instanceof Bonus) {
			((Bonus)secondObject).collect((Vehicle)attachedObject);
			Runner.inst().removeUnit(secondObject);
		}
		else
			((Vehicle)attachedObject).doDamage(penetrationDepth*Settings.Vehicle.damageFactor);
	}

}
