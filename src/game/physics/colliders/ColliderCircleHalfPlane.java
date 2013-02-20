package game.physics.colliders;

import game.physics.colliders.hooks.CollideEventHook;
import game.physics.objects.Circle;
import game.physics.objects.HalfPlane;
import game.utils.Vector2d;

public class ColliderCircleHalfPlane extends Collider {
	public Vector2d n = new Vector2d();
	public double dr;
	public double vn;
	
	public boolean isCollide(Circle circle, HalfPlane hp) {
		if(circle.isMaterial && hp.isMaterial) {
			n.assign(Math.cos(hp.angle), Math.sin(hp.angle));
			dr = circle.radius - ((circle.position.diff(hp.position)).dotprod(n));
			vn = circle.speed.dotprod(n);
			return dr>0;
		}
		return false;
	}

	public void Collide(Circle circle, HalfPlane hp) {
		if (isCollide(circle, hp)) {
			if(vn<0) {
				boolean bContinue = true;
				// Collide detected, invoke the event listeners:
				if (circle.collideEventHooks != null)
					for (CollideEventHook listener : circle.collideEventHooks)
						bContinue &= !listener.onEvent(hp, dr);
				if (hp.collideEventHooks != null)
					for (CollideEventHook listener : hp.collideEventHooks)
						bContinue &= !listener.onEvent(circle, dr);
				if(bContinue) {
					// Continue calculations:
					circle.speed.add(n.mul(-2 * vn));
				}
			}
		}
	}

}
