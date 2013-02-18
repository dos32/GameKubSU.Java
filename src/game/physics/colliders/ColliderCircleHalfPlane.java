package game.physics.colliders;

import game.physics.colliders.listeners.CollideEventListener;
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
			return (dr>0 && vn<0);
		}
		return false;
	}

	public void Collide(Circle circle, HalfPlane hp) {
		if (isCollide(circle, hp)) {
			// Collide detected, invoke the event listeners:
			if (circle.collideEventListeners != null)
				for (CollideEventListener listener : circle.collideEventListeners)
					listener.onEvent(hp, dr);
			if (hp.collideEventListeners != null)
				for (CollideEventListener listener : hp.collideEventListeners)
					listener.onEvent(circle, dr);
			// Continue calculations:
			circle.speed.add(n.mul(-2 * vn));
		}
	}

}
