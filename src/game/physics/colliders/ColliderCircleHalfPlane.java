package game.physics.colliders;

import game.physics.colliders.listeners.CollideEventListener;
import game.physics.objects.Circle;
import game.physics.objects.HalfPlane;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class ColliderCircleHalfPlane extends Collider {

	@Override
	public void Collide(Unit unit1, Unit unit2) {
		if(unit1.isMaterial && unit2.isMaterial) {
			Circle circle;
			HalfPlane hp;
			if (Circle.class.isAssignableFrom(unit1.getClass()) && HalfPlane.class.isAssignableFrom(unit2.getClass())) {
				circle = (Circle) unit1;
				hp = (HalfPlane) unit2;
			} else if (HalfPlane.class.isAssignableFrom(unit1.getClass()) && Circle.class.isAssignableFrom(unit2.getClass())) {
				circle = (Circle) unit2;
				hp = (HalfPlane) unit1;
			} else
				return;
			Vector2d n = new Vector2d(Math.cos(hp.angle), Math.sin(hp.angle));
			double dr = circle.radius - ((circle.position.diff(hp.position)).dotprod(n));
			double vn = circle.speed.dotprod(n);
			if(dr>0 && vn<0) {
				// Collide detected, invoke the event listeners:
				if(unit1.collideEventListeners != null)
					for(CollideEventListener listener : unit1.collideEventListeners)
						listener.onEvent(unit2, dr);
				if(unit2.collideEventListeners != null)
					for(CollideEventListener listener : unit2.collideEventListeners)
						listener.onEvent(unit1, dr);
				// Continue calculations:
				circle.speed.add(n.mul(-2*vn));
			}
		}
	}

}
