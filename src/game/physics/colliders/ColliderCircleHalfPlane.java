package game.physics.colliders;

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
			if (unit1.getClass() == Circle.class && unit2.getClass() == HalfPlane.class) {
				circle = (Circle) unit1;
				hp = (HalfPlane) unit2;
			} else if (unit1.getClass() == HalfPlane.class && unit2.getClass() == Circle.class) {
				circle = (Circle) unit2;
				hp = (HalfPlane) unit1;
			} else
				return;
			Vector2d n = new Vector2d(Math.cos(hp.angle), Math.sin(hp.angle));
			double dr = circle.radius - ((circle.position.diff(hp.position)).dotprod(n));
			double vn = circle.speed.dotprod(n);
			if(dr>0 && vn<0)
				circle.speed.add(n.mul(-2*vn));
		}
	}

}
