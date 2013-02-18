package game.physics.objects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Box extends Unit {
	private static final long serialVersionUID = 8112815845676840482L;
	public final double width, height;
	
	public Box(double width, double height)
	{
		this.width=width;
		this.height=height;
	}

	@Override
	public void draw(Graphics2D graphics) {
		AffineTransform oldTransform=graphics.getTransform();
		graphics.translate(this.position.x, this.position.y);
		graphics.rotate(this.angle);
		graphics.drawRect(-(int)this.width/2, -(int)this.height/2,
				(int)this.width, (int)this.height);
		graphics.setTransform(oldTransform);
	}

	@Override
	public double getSquare() {
		return width*height;
	}
}
