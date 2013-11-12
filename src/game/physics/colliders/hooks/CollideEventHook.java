package game.physics.colliders.hooks;

import game.physics.objects.Unit;
import game.utils.Vector2d;

public interface CollideEventHook {
	
	/**
	 * Calls on event of collision on binded object
	 * @return	true if collision is processed by hook,
	 * false if collsion must be calculated by collider
	 */
	public abstract boolean onEvent(Unit secondObject, double penetrationDepth, Vector2d penetrationPoint);
}
