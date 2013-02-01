package game.model;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.utils.Vector2d;

public class RectShapeUnit extends Unit {
	public final double width, height;
	
	public RectShapeUnit(double width, double height)
	{
		this.width=width;
		this.height=height;
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
		AffineTransform a=graphics.getTransform();
		graphics.translate(this.position.x+this.width/2, this.position.y+this.height/2);
		graphics.rotate(this.angle,this.width/2,this.height/2);
		graphics.drawRect(0, 0, (int)this.width, (int)this.height);
		graphics.setTransform(a);
	}

	@Override
	public double getSquare() {
		return width*height;
	}
}
