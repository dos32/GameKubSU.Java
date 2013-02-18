package game.physics.colliders;

import game.physics.objects.Box;
import game.physics.objects.Circle;
import game.utils.Vector2d;

public class ColliderCircleBox extends Collider {

	public boolean isCollide(Circle unit1, Box unit2) {
		return false;
	}

	@SuppressWarnings("unused")
	public void Collide(Circle unit1, Box unit2) {
		/*Circle circle;
		Box box;
		if(Circle.class.isAssignableFrom(unit1.getClass()) && Box.class.isAssignableFrom(unit2.getClass())) {
			circle = (Circle)unit1;
			box = (Box)unit2;
		}
		else if(Box.class.isAssignableFrom(unit1.getClass()) && Circle.class.isAssignableFrom(unit2.getClass())) {
			circle = (Circle)unit2;
			box = (Box)unit1;
		}
		else
			return;*/
		if(unit1.isMaterial && unit2.isMaterial) {
			Vector2d n1 = new Vector2d(Math.cos(unit2.angle), Math.sin(unit2.angle));
			Vector2d n2 = new Vector2d(-n1.y, n1.x);
			
		}
	}

}
