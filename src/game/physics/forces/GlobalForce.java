package game.physics.forces;

import game.physics.objects.Unit;

//import game.utils.Vector2d;

public abstract class GlobalForce {
	// public abstract Vector2d getAttachPoint(Unit unit);
	// public abstract Vector2d getForce(Unit unit);
	public abstract void tick(Unit unit);
}
