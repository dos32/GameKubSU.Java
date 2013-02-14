package game.physics.forces;

import java.util.ArrayList;
import java.util.List;
//import java.util.Hashtable;

import game.Runner;
import game.physics.colliders.Collider;
import game.physics.colliders.ColliderCircleCircle;
import game.physics.colliders.ColliderCircleHalfPlane;
import game.physics.objects.Circle;
//import game.physics.objects.HalfPlane;
import game.physics.objects.Unit;

public class CollideForce extends GlobalForce {
	public ArrayList<Collider> colliders = new ArrayList<Collider>();
	
	/*public class UnitPairClass {
		public Class<? extends Unit> class1, class2;
		
		public UnitPairClass(Class<? extends Unit> class1, Class<? extends Unit> class2) {
			this.class1 = class1;
			this.class2 = class2;
		}
		
		public int hashCode() {
			return class1.hashCode()+class2.hashCode();
		}
		
		public boolean equals(Object o) {
			if(o.getClass() != UnitPairClass.class)
				return false;
			else
			{
				UnitPairClass u = (UnitPairClass)o;
				return class1 == u.class1 && class2 == u.class2 ||
						class1 == u.class2 && class2 == u.class1;
			}
		}
		
	}
	
	public Hashtable<UnitPairClass, Collider> CollideMatrix = new Hashtable<CollideForce.UnitPairClass, Collider>();*/

	public CollideForce(Runner runner) {
		super(runner);
		colliders.add(new ColliderCircleCircle());
		colliders.add(new ColliderCircleHalfPlane());
		/*CollideMatrix.put(new UnitPairClass(Circle.class, Circle.class), new ColliderCircleCircle());
		CollideMatrix.put(new UnitPairClass(Circle.class, HalfPlane.class), new ColliderCircleHalfPlane());*/
	}

	@Override
	public void apply(List<Unit> objects) {
		for(int i=0; i<objects.size(); i++)
			for(int j=i+1; j<objects.size(); j++)
			{
				Unit unit1 = objects.get(i);
				Unit unit2 = objects.get(j);
				for(Collider collider : colliders)
					if(unit1.getClass() == Circle.class && unit2.getClass() == Circle.class)
					{
						if(((Circle)unit1).getApproxBox().isIntersect(((Circle)unit2).getApproxBox()))
							collider.Collide(unit1, unit2);
					}
					else
						collider.Collide(unit1, unit2);
			}
	}

}
