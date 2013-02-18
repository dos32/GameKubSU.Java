package game.physics.colliders;

import game.physics.colliders.listeners.CollideEventListener;
import game.physics.objects.Circle;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class ColliderCircleCircle extends Collider {
	
	@Override
	public void Collide(Unit unit1, Unit unit2) {
		if(Circle.class.isAssignableFrom(unit1.getClass()) && Circle.class.isAssignableFrom(unit2.getClass()) &&
				unit1.isMaterial && unit2.isMaterial) {
			double R = ((Circle)unit1).radius+((Circle)unit2).radius;
			if(Math.abs(unit1.position.x-unit2.position.x)<=R && Math.abs(unit1.position.y-unit2.position.y)<=R)
			{
				Vector2d n = (unit1.position.diff(unit2.position));
				double dr = n.norm();
				if(dr <= R) {
					// Collide detected, invoke the event listeners:
					if(unit1.collideEventListeners != null)
						for(CollideEventListener listener : unit1.collideEventListeners)
							listener.onEvent(unit2, R-dr);
					if(unit2.collideEventListeners != null)
						for(CollideEventListener listener : unit2.collideEventListeners)
							listener.onEvent(unit1, R-dr);
					// Continue calculations:
					n.scale(1/dr);
					double v1 = unit1.speed.dotprod(n);
					double v2 = unit2.speed.dotprod(n);
					if(v1<=0 && v2>=0 || v1<=0 && v2<=0 && v1<=v2 || v1>=0 && v2>=0 && v2>=v1) {
						if(!unit1.isStatic)
							unit1.speed.add(n.mul(-v1));
							unit1.speed.add(n.mul(((unit1.mass-unit2.mass)*v1+2*unit2.mass*v2)/(unit1.mass+unit2.mass)));
						if(!unit2.isStatic)
							unit2.speed.add(n.mul(-v2));
							unit2.speed.add(n.mul(((unit2.mass-unit1.mass)*v2+2*unit1.mass*v1)/(unit1.mass+unit2.mass)));
					}
				}
			}
		}
	}

}
