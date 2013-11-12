package game.physics.colliders;

import game.physics.colliders.hooks.CollideEventHook;
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
			// Collision detected, invoke the event listeners:
			double penetrationDepth = R - dr;
			Vector2d penetrationPoint = new Vector2d(n);
			penetrationPoint.normalize();
			penetrationPoint.scale(penetrationDepth/2 - unit1.radius);
			penetrationPoint.add(unit1.position);
			if (unit1.collideEventHooks != null)
				for (CollideEventHook listener : unit1.collideEventHooks)
					bContinue &= !listener.onEvent(unit2, penetrationDepth, penetrationPoint);
			if (unit2.collideEventHooks != null)
				for (CollideEventHook listener : unit2.collideEventHooks)
					bContinue &= !listener.onEvent(unit1, penetrationDepth, penetrationPoint);
			if(bContinue) {
				// Continue calculations:
				n.scale(1 / dr);
				double v1 = unit1.speed.dotprod(n);
                double v2 = unit2.speed.dotprod(n);
                double frictionCoefficient = unit1.frictionCoeff + unit2.frictionCoeff;
                Vector2d tanSpeed1 = new Vector2d(unit1.speed.diff(n.mul(v1)));
                Vector2d tanSpeed2 = new Vector2d(unit2.speed.diff(n.mul(v2)));

                Vector2d frictionForce = new Vector2d();
                frictionForce.add(tanSpeed1.mul(-frictionCoefficient*dr));
                frictionForce.add(tanSpeed2.mul(frictionCoefficient*dr));
                frictionForce.add(n.rotatedBy(Math.PI/2.0).mul(unit1.angularSpeed * unit1.radius * unit1.radius * 0.4 * unit1.mass * frictionCoefficient));
                frictionForce.add(n.rotatedBy(Math.PI/2.0).mul(unit2.angularSpeed * unit2.radius * unit2.radius* 0.4 * unit2.mass * frictionCoefficient));

				if (v1 <= 0 && v2 >= 0 || v1 <= 0 && v2 <= 0 && v1 <= v2 || v1 >= 0 &&
						v2 >= 0 && v2 >= v1) {
					if (!unit1.isStatic) {
						unit1.speed.add(n.mul(-v1));
                        unit1.speed.add(n.mul(((unit1.mass-unit2.mass)*v1 + 2*unit2.mass*v2) / (unit1.mass+unit2.mass)));
                        //unit1.speed.sub(frictionForce);  // tangent speed is taken into account
                        unit1.angularSpeed += frictionForce.rotatedBy(Math.PI/2.0).dotprod(n) / (unit1.mass * unit1.radius * unit1.radius * 0.4);
					}
					if (!unit2.isStatic) {
						unit2.speed.add(n.mul(-v2));
						unit2.speed.add(n.mul(((unit2.mass-unit1.mass)*v2 + 2*unit1.mass*v1) / (unit1.mass+unit2.mass)));
                        //unit2.speed.add(frictionForce);  // tangent speed is taken into account
                        unit2.angularSpeed += frictionForce.rotatedBy(Math.PI/2.0).dotprod(n) / (unit2.mass * unit2.radius * unit2.radius * 0.4 );
					}
				}
			}
		}
	}

}
