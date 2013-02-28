package game.physics.objects;

import game.engine.Settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/*
 * Animation object for showing text label on screen
 */
public class InfoTip extends DrawableUnit {
	private static final long serialVersionUID = 1896431443802950820L;
	
	public String message;
	public Color color;
	public double textSize = Settings.InfoTip.defaultTextSize;

	public InfoTip(String message) {
		this.message = message;
		this.isMaterial = false;
	}
	
	/**
	 * 
	 * @param graphics
	 * @return	Size of tip in graphics context
	 */
	public Dimension getSize(Graphics2D graphics) {
		Font oldFont = graphics.getFont();
		graphics.setFont(new Font("Courier new", Font.PLAIN, (int)textSize));
		FontMetrics fm = graphics.getFontMetrics();
		Dimension result = new Dimension(fm.stringWidth(message), fm.getHeight());
		graphics.setFont(oldFont);
		return result;
	}

	/**
	 * Draws object into left top side of its bounds,
	 * rotate it around the center of string in current
	 * graphics font metrics
	 */
	@Override
	public void draw(Graphics2D graphics) {
		Color oldColor = graphics.getColor();
		Font oldFont = graphics.getFont();
		graphics.setColor(color);
		graphics.setFont(new Font("Courier new", Font.PLAIN, (int)textSize));
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
		graphics.setFont(oldFont);
		graphics.setColor(oldColor);
	}
	
}
