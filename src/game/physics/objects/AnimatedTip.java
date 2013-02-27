package game.physics.objects;

import game.Runner;
import game.engine.Settings;
import game.engine.Tickable;
import game.utils.Vector2d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class AnimatedTip extends InfoTip implements Tickable {
	private static final long serialVersionUID = 6634407963815306442L;
	public long lifeTime = Settings.AnimatedTip.defaultLifeTime;
	public long bornTime;
	public Color color = Settings.AnimatedTip.dmgColor;

	public AnimatedTip(String message, Vector2d position) {
		super(message);
		this.position.assign(position);
		this.isMaterial = false;
		this.bornTime = Runner.inst().tick;
		this.speed.assign(Settings.AnimatedTip.defaultSpeed);
		Runner.inst().renderer.removeUnit(this);
		this.zIndex = Settings.AnimatedTip.defaultZIndex;
		Runner.inst().renderer.addUnit(this);
	}

	@Override
	public void tick() {
		if(Runner.inst().tick - bornTime > lifeTime)
			dispose();
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		Font oldFont = graphics.getFont();
		Color oldColor = graphics.getColor();
		graphics.setColor(color);
		double time = (double)(Runner.inst().tick - bornTime)/(double)lifeTime;
		int textSz = (int)Math.round((1-time)*(Settings.AnimatedTip.startTextSize-Settings.AnimatedTip.endTextSize) +
				Settings.AnimatedTip.endTextSize);
		graphics.setFont(new Font("Courier new", Font.PLAIN, textSz));
		graphics.drawString(message, (float)position.x, (float)position.y);
		graphics.setColor(oldColor);
		graphics.setFont(oldFont);
	}

}
