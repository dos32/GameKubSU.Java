package game.physics.forces;

import game.engine.Settings;
import game.physics.objects.Vehicle;
import game.utils.Vector2d;

public class ControlForce extends BindedForce {
	public double powerFactor = 0, turnFactor = 0;
	public double maxPowerConstraint = Settings.Vehicle.maxPower,
			maxTurnConstraint = Settings.Vehicle.maxTurn;
	
	public ControlForce(Vehicle bindUnit) {
		super(bindUnit);
		((Vehicle)bindUnit).engine = this;
	}

	@Override
	public void apply() {
		Vector2d n = new Vector2d(Math.sin(bindUnit.angle), Math.cos(bindUnit.angle));
		bindUnit.speed.add(
			n.mul(Math.signum(powerFactor)*Math.min(1, Math.abs(powerFactor))*maxPowerConstraint));
		bindUnit.angularSpeed += Math.signum(turnFactor)*Math.min(1, Math.abs(turnFactor))*maxTurnConstraint;
	}

}
