package game.physics.forces;

import game.engine.Settings;
import game.physics.objects.Vehicle;
import game.utils.Vector2d;

public class ControlForce extends BindedForce {
	public double powerFactor = 0,
			turnFactor = 0;
	
	public ControlForce(Vehicle bindUnit) {
		super(bindUnit);
		((Vehicle)bindUnit).engine = this;
	}

	@Override
	public void apply() {
		Vector2d n = new Vector2d(Math.sin(bindUnit.angle), Math.cos(bindUnit.angle));
		double nitroForTick = Math.min(((Vehicle)bindUnit).nitro, Settings.Vehicle.maxNitroBoost);
		powerFactor = Math.signum(powerFactor)*Math.min(Math.abs(powerFactor),
				Settings.Vehicle.maxPowerFactor + nitroForTick);
		double nitroPower = Math.max(0, Math.abs(powerFactor) - Settings.Vehicle.maxPowerFactor);
		((Vehicle)bindUnit).changeNitro(-nitroPower);
		bindUnit.speed.add(n.mul(powerFactor*Settings.Vehicle.powerCoeff));
		bindUnit.angularSpeed += Math.signum(turnFactor)*Settings.Vehicle.turnCoeff*
				Math.min(Settings.Vehicle.maxTurnFactor, Math.abs(turnFactor));
	}

}
