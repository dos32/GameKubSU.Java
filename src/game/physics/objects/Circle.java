package game.physics.objects;

import game.Runner;
import game.engine.Settings;
import game.utils.Rectd;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Circle extends Unit {
	private static final long serialVersionUID = -2503510928736687406L;
	public double radius;
	
	public Circle(double radius) {
		this.radius = radius;
	}
	
	public Rectd getApproxBox() {
		return Rectd.createSquare(position, radius);
	}

	@Override
	public void draw(Graphics2D graphics) {
		if(Settings.Renderer.drawImages) {
			if(Runner.inst().circleImg != null)
				graphics.drawImage(Runner.inst().circleImg, (int)(position.x-radius), (int)(position.y-radius),
						(int)(2*radius), (int)(2*radius), null);
			else
				System.err.println("img not loaded");
		}
		else {
			AffineTransform oldTransform = graphics.getTransform();
			graphics.translate(position.x-radius, position.y-radius);
			graphics.drawOval(0, 0, (int)radius*2, (int)radius*2);
			graphics.setTransform(oldTransform);
		}
	}

	@Override
	public double getSquare() {
		return Math.PI*radius*radius;
	}
}
