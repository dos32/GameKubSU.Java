package game.physics.objects;

import game.utils.Rectd;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Circle extends Unit {
	public double radius;
	
	public Circle(double radius) {
		this.radius = radius;
	}
	
	public Rectd getApproxBox() {
		return Rectd.createSquare(position, radius);
	}

	@Override
	public void draw(Graphics2D graphics) {
		AffineTransform oldTransform = graphics.getTransform();
		graphics.translate(position.x-radius, position.y-radius);
		graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
		graphics.setTransform(oldTransform);
	}

	@Override
	public double getSquare() {
		return Math.PI*radius*radius;
	}
}
