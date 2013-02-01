package game.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.utils.Vector2d;

/*
 * Animation object for showing text info on screen 
 */
public class InfoTip extends Unit {
	public String message;
	public Color color;
	public int TextSize;

	public InfoTip(String message) {
		this.message = message;
		this.isMaterial = false;
	}

	@Override
	public double getPenetrationDepth(Unit unit) {
		return 0;
	}

	@Override
	public Vector2d getNorm(Unit unit) {
		return new Vector2d();
	}

	/*
	 * draws object into left top side of its bounds,
	 * rotate it around the center of string in current
	 * graphics font metrics
	 */
	@Override
	public void draw(Graphics2D graphics) {
		Color oldColor = graphics.getColor();
		graphics.setColor(color);
		AffineTransform transform = graphics.getTransform();
		if (angle != 0) {
			double height = graphics.getFontMetrics().getHeight();
			double width = graphics.getFontMetrics().getStringBounds(message, graphics).getWidth();
			graphics.rotate(this.angle, this.position.x + width / 2,
					this.position.y + height / 2);
		}
		graphics.drawString(message, (float) position.x,
				(float) (position.y + graphics.getFontMetrics().getAscent()));
		graphics.setTransform(transform);
		graphics.setColor(oldColor);
	}

	@Override
	public double getSquare() {
		return 0;
	}
}
