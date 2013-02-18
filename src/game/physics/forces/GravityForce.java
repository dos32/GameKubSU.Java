package game.physics.forces;

import java.util.List;

import game.physics.objects.Unit;
import game.utils.Vector2d;

public class GravityForce extends GlobalForce {
	public Vector2d value = new Vector2d();

	public GravityForce(Vector2d value) {
		super();
		this.value.assign(value);
	}

	@Override
	public void apply(List<Unit> objects) {
		for(Unit unit : objects)
			if(!unit.isStatic)
			unit.speed.add(value.mul(unit.mass));
	}

}
