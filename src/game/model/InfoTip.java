package game.model;

import java.awt.Color;
import java.awt.Graphics2D;

import game.utils.Vector2d;

/*
 * Animation object for showing text info on screen 
 */
public class InfoTip extends Unit {
	public String message;
	public Color color;

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

	@Override
	public void draw(Graphics2D graphics) {
		//graphics.getFontMetrics().getLineMetrics("", graphics).getAscent()
		graphics.drawString(message, (float) position.x,
				(float) (position.y+graphics.getFontMetrics().getAscent()));
	}

	@Override
	public double getSquare() {
		return 0;
	}
}
