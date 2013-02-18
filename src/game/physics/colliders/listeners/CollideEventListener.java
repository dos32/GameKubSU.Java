package game.physics.colliders.listeners;

import game.physics.objects.Unit;

public abstract class CollideEventListener {
	public Unit attachedObject;
	
	public CollideEventListener(Unit attachedObject) {
		this.attachedObject = attachedObject;
	}
	
	public abstract void onEvent(Unit secondObject, double penetrationDepth);
}
