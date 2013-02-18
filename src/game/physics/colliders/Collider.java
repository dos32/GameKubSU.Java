package game.physics.colliders;

import game.physics.objects.Unit;

/*
 * Each collider must implements calculation of collisions
 *  and calls collide events, if it occurs
 */
public abstract class Collider {
	public boolean isCollide(Unit unit1, Unit unit2) {return false;}
	public void Collide(Unit unit1, Unit unit2) {}
}
