package game.physics.objects;

import game.Runner;
import game.engine.Settings;
import game.engine.Tickable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class AnimatedTip extends InfoTip implements Tickable {
	private static final long serialVersionUID = 6634407963815306442L;
	public long lifeTime = Settings.AnimatedTip.defaultLifeTime;
	public long bornTime;
	public double textSize = Settings.AnimatedTip.startTextSize;
	public Color color = Settings.AnimatedTip.dmgColor;

	public AnimatedTip(String message, Color color) {
		super(message);
		this.color = color;
		this.isMaterial = false;
		this.bornTime = Runner.inst().tick;
		this.speed.assign(Settings.AnimatedTip.defaultSpeed);
		setZIndex(Settings.AnimatedTip.defaultZIndex);
	}

	@Override
	public void tick() {
		double time = (double)(Runner.inst().tick - bornTime)/(double)lifeTime;
		textSize = (int)Math.round((1-time)*(Settings.AnimatedTip.startTextSize-Settings.AnimatedTip.endTextSize) +
			Settings.AnimatedTip.endTextSize);
		if(time > 1)
			dispose();
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		Font oldFont = graphics.getFont();
		Color oldColor = graphics.getColor();
		graphics.setColor(color);
		graphics.setFont(new Font("Courier new", Font.PLAIN, (int)textSize));
		graphics.drawString(message, (float)position.x, (float)(position.y+graphics.getFontMetrics().getAscent()));
		graphics.setColor(oldColor);
		graphics.setFont(oldFont);
	}

}
