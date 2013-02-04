package game.physics.colliders;

import game.physics.objects.Circle;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class ColliderCircleCircle extends Collider {
	
	@Override
	public void Collide(Unit unit1, Unit unit2) {
		if(unit1.getClass() == Circle.class && unit2.getClass() == Circle.class &&
				unit1.isMaterial && unit2.isMaterial) {
			Vector2d n = (unit1.position.diff(unit2.position));
			double dr = n.norm();
			n.scale(1/dr);
			double v1 = unit1.speed.dotprod(n);
			double v2 = unit2.speed.dotprod(n);
			if(dr < ((Circle) unit1).radius+((Circle) unit2).radius)
			if(v1<0 && v2>0 || v1<0 && v2<0 && v1<v2 || v1>0 && v2>0 && v2>v1) {
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
