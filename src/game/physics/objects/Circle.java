package game.physics.objects;

import java.awt.Graphics2D;

import game.utils.Vector2d;

public class Circle extends Unit {
	public double radius;
	
	public Circle(double radius) {
		this.radius = radius;
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

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawOval((int)(position.x-radius), (int)(position.y-radius), (int)radius*2, (int)radius*2);
	}

	@Override
	public double getSquare() {
		// TODO Auto-generated method stub
		return 0;
	}
}
