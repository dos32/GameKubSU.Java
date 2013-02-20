package game.physics.forces;

import java.util.List;

import game.Runner;
import game.physics.colliders.ColliderCircleCircle;
import game.physics.colliders.ColliderCircleHalfPlane;
import game.physics.objects.Circle;
import game.physics.objects.HalfPlane;
import game.physics.objects.Unit;

public class CollideForce extends GlobalForce {
	public ColliderCircleCircle cc = new ColliderCircleCircle();
	public ColliderCircleHalfPlane chp = new ColliderCircleHalfPlane();

	/*
	 * Change position of unit until it collides with other objects
	 *	or operation repeats more than tryCount times  
	 */
	public void placeNoCollide(Unit unit, int tryCount) {
		for(int i=0; i<tryCount; i++) {
			unit.position.assign(Runner.inst().world.width*Math.random(),
					Runner.inst().world.height*Math.random());
			boolean isCollide = false;
			for(Unit unit2 : Runner.inst().physics.objects) {
				if(unit2!=unit) {
					isCollide |= isCollide(unit, unit2);
					if(isCollide)
						break;
				}
			}
			if(!isCollide)
				break;
		}
	}

	public boolean isCollide(Unit unit1, Unit unit2) {
		if((unit1 instanceof Circle) && (unit2 instanceof Circle))
			return cc.isCollide((Circle)unit1, (Circle)unit2);
		else if((unit1 instanceof Circle) && (unit2 instanceof HalfPlane))
			return chp.isCollide((Circle)unit1,(HalfPlane)unit2);
		else if((unit2 instanceof Circle) && (unit1 instanceof HalfPlane))
			return chp.isCollide((Circle)unit2,(HalfPlane)unit1);
		else {
			/*System.err.println(String.format("CollideForce.isCollide()::Unknown types of params,%s,%s",
					unit1.getClass().getName(), unit2.getClass().getName()));*/
			return false;
		}
	}

	@Override
	public void apply(List<Unit> objects) {
		for(int i=0; i<objects.size(); i++)
			for(int j=i+1; j<objects.size(); j++) {
				Unit unit1 = objects.get(i);
				Unit unit2 = objects.get(j);
				if((unit1 instanceof Circle) && (unit2 instanceof Circle))
					cc.Collide((Circle)unit1, (Circle)unit2);
				else if((unit1 instanceof Circle) && (unit2 instanceof HalfPlane))
					chp.Collide((Circle)unit1,(HalfPlane)unit2);
				else if((unit2 instanceof Circle) && (unit1 instanceof HalfPlane))
					chp.Collide((Circle)unit2,(HalfPlane)unit1);
				else
					;/*System.err.println(String.format("CollideForce.apply()::Unknown types of params,%s,%s",
							unit1.getClass().getName(), unit2.getClass().getName()));*/
			}
	}

}
