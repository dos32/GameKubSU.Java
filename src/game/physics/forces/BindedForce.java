package game.physics.forces;

import game.physics.objects.Unit;
import game.utils.Vector2d;

public abstract class BindedForce {
	public Unit bindUnit;
	
	public abstract Vector2d getAttachPoint();
	public abstract Vector2d getForce();
}
