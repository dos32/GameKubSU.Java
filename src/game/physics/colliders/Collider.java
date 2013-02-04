package game.physics.colliders;

import game.physics.objects.Unit;

public abstract class Collider {
	public abstract void Collide(Unit unit1, Unit unit2);
}
