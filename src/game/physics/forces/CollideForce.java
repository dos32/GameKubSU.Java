package game.physics.forces;

import java.util.ArrayList;

import game.Runner;
import game.physics.colliders.Collider;
import game.physics.colliders.ColliderCircleCircle;
import game.physics.colliders.ColliderCircleHalfPlane;

public class CollideForce extends GlobalForce {
	public ArrayList<Collider> colliders = new ArrayList<Collider>();

	public CollideForce(Runner runner) {
		super(runner);
		colliders.add(new ColliderCircleCircle());
		colliders.add(new ColliderCircleHalfPlane());
	}

	@Override
	public void apply() {
		for(int i=0; i<runner.world.allUnits.size(); i++)
			for(int j=i+1; j<runner.world.allUnits.size(); j++)
				for(Collider collider : colliders)
					collider.Collide(runner.world.allUnits.get(i), runner.world.allUnits.get(j));
	}

}
