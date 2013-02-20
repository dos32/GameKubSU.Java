package game.physics.colliders;

import game.physics.colliders.listeners.CollideEventHook;
import game.physics.objects.Circle;
import game.utils.Vector2d;

public class ColliderCircleCircle extends Collider {
	
	public Vector2d n = new Vector2d();
	public double dr, R;

	public boolean isCollide(Circle unit1, Circle unit2) {
		if(unit1.isMaterial && unit2.isMaterial) {
			R = ((Circle) unit1).radius + ((Circle) unit2).radius;
			if (Math.abs(unit1.position.x - unit2.position.x) <= R
					&& Math.abs(unit1.position.y - unit2.position.y) <= R) {
				n.assign(unit1.position);
				n.sub(unit2.position);
				dr = n.norm();
				return (dr <= R);
			}
		}
		return false;
	}
	
	public void Collide(Circle unit1, Circle unit2) {
		if (isCollide(unit1, unit2)) {
			boolean bContinue = true;
			// Collide detected, invoke the event listeners:
			if (unit1.collideEventListeners != null)
				for (CollideEventHook listener : unit1.collideEventListeners)
					bContinue &= !listener.onEvent(unit2, R - dr);
			if (unit2.collideEventListeners != null)
				for (CollideEventHook listener : unit2.collideEventListeners)
					bContinue &= !listener.onEvent(unit1, R - dr);
			if(bContinue) {
				// Continue calculations:
				n.scale(1 / dr);
				double v1 = unit1.speed.dotprod(n);
				double v2 = unit2.speed.dotprod(n);
				if (v1 <= 0 && v2 >= 0 || v1 <= 0 && v2 <= 0 && v1 <= v2 || v1 >= 0 &&
						v2 >= 0 && v2 >= v1) {
					if (!unit1.isStatic) {
						unit1.speed.add(n.mul(-v1));
						unit1.speed.add(n.mul(((unit1.mass-unit2.mass)*v1 + 2*unit2.mass*v2) / (unit1.mass+unit2.mass)));
					}
					if (!unit2.isStatic) {
						unit2.speed.add(n.mul(-v2));
						unit2.speed.add(n.mul(((unit2.mass-unit1.mass)*v2 + 2*unit1.mass*v1) / (unit1.mass+unit2.mass)));
					}
				}
			}
		}
	}

}
