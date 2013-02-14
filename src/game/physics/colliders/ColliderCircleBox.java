package game.physics.colliders;

import game.physics.objects.Box;
import game.physics.objects.Circle;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class ColliderCircleBox extends Collider {

	@SuppressWarnings("unused")
	@Override
	public void Collide(Unit unit1, Unit unit2) {
		Circle circle;
		Box box;
		if(unit1.getClass() == Circle.class && unit2.getClass() == Box.class) {
			circle = (Circle)unit1;
			box = (Box)unit2;
		}
		else if(unit1.getClass() == Box.class && unit2.getClass() == Circle.class) {
			circle = (Circle)unit2;
			box = (Box)unit1;
		}
		else
			return;
		if(circle.isMaterial && box.isMaterial) {
			Vector2d n1 = new Vector2d(Math.cos(box.angle), Math.sin(box.angle));
			Vector2d n2 = new Vector2d(-n1.y, n1.x);
			
		}
	}

}