package game.physics.colliders.listeners;

import game.physics.objects.Unit;

public abstract class CollideEventHook {
	public Unit attachedObject;
	
	public CollideEventHook(Unit attachedObject) {
		this.attachedObject = attachedObject;
	}
	
	/*
	 * Calls on event, returns true if collision is processed by hook
	 */
	public abstract boolean onEvent(Unit secondObject, double penetrationDepth);
}
