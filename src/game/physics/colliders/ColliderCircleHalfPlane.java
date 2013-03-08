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
                    Vector2d frictionForce = new Vector2d();
                    Vector2d tangentSpeed = new Vector2d(circle.speed.diff(n.mul(vn)));
                    double frictionCoefficient = circle.frictionCoeff + hp.frictionCoeff; // friction coefficient*friction effect
                    frictionForce.add(tangentSpeed.mul(-frictionCoefficient*dr));
                    frictionForce.add(n.rotatedBy(Math.PI/2.0).mul(circle.angularSpeed * circle.radius * 0.4 * circle.radius * circle.mass * frictionCoefficient));

                    // three steps 1: vertical part
                    circle.speed.add(n.mul(-2 * vn));
                    // 2: tangent speed
                    circle.speed.add(frictionForce);
                    // 3: rotation speed
                    circle.angularSpeed += frictionForce.rotatedBy(Math.PI/2.0).dotprod(n)/(circle.radius*0.4*circle.radius*circle.mass);
				}
			}
		}
	}

}
