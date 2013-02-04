package game.physics.colliders;

import game.physics.objects.Circle;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class ColliderCircleCircle extends Collider {
	
	@Override
	public void Collide(Unit unit1, Unit unit2) {
		if(unit1.getClass() == Circle.class && unit2.getClass() == Circle.class &&
				unit1.isMaterial && unit2.isMaterial) {
			Vector2d dr = unit1.position.diff(unit2.position);
			if(dr.norm() < ((Circle) unit1).radius+((Circle) unit2).radius) {
				if(!unit1.isStatic)
					unit1.speed.add(dr.mul(unit1.collideCoeff/dr.square()));
				if(!unit2.isStatic)
					unit2.speed.add(dr.mul(-unit2.collideCoeff/dr.square()));
			}
		}
	}

}
