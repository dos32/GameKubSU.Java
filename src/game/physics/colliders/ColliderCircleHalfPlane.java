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
			/*Vector2d r1 = new Vector2d(hp.position);
			Vector2d r2 = new Vector2d(-Math.sin(hp.angle),Math.cos(hp.angle));
			r2.add(r1);
			//double r1r22 = r1.diff(r2).square(); // but according to calculation of r2:
			double r1r22 = 1;
			Vector2d r1r2 = r1.diff(r2);
			double Alpha = (r2.square()-r1.square()+r1r22+2*circle.position.dotprod(r1r2))/(2*r1r22);
			Vector2d r = (r1.mul(Alpha).sum(r2.mul(1-Alpha)));
			double sgn = Math.signum(r.diff(circle.position).dotprod(new Vector2d(Math.cos(hp.angle),Math.sin(hp.angle))));
			r.negate();
			r.add(circle.position);
			double dr = circle.radius - r.norm()*sgn;
			if(dr>0)
				circle.speed.add(new Vector2d(Math.cos(hp.angle),Math.sin(hp.angle)).mul(-1/(dr*dr)));*/
			Vector2d n = new Vector2d(Math.cos(hp.angle), Math.sin(hp.angle));
			double dr = circle.radius - (circle.position.diff(hp.position).dotprod(n));
			if(dr>0)
				circle.speed.add(n.mul(1/dr*dr));
		}
	}

}
