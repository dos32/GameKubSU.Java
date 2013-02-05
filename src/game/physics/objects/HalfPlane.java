package game.physics.objects;

import game.utils.Vector2d;

import java.awt.Graphics2D;

public class HalfPlane extends Unit {
	// position of this object is control point
	// normal to the edge of plane sets by an angle
	
	public HalfPlane(Vector2d position, double angle) {
		this.position.assign(position);
		this.angle = angle;
		isStatic = true;
	}

	@Override
	public void draw(Graphics2D graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSquare() {
		// TODO Auto-generated method stub
		return 0;
	}

}
