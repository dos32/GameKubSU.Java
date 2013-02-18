package game.physics.colliders;

import game.physics.objects.Unit;

/*
 * Each collider must implements calculation of collisions
 *  and calls collide events, if it occurs
 */
public abstract class Collider {
	public abstract void Collide(Unit unit1, Unit unit2);
}
