package game.physics.objects;

import game.utils.Vector2d;

import java.awt.Graphics2D;

public class HalfPlane extends Unit {
	// position of this object is control point
	public Vector2d normal;

	@Override
	public void draw(Graphics2D graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSquare() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPenetrationDepth(Unit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2d getNorm(Unit unit) {
		// TODO Auto-generated method stub
		return null;
	}

}
