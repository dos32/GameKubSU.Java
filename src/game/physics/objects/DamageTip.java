package game.physics.objects;

import game.Runner;
import game.engine.Settings;
import game.utils.Vector2d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DamageTip extends InfoTip {
	private static final long serialVersionUID = 6634407963815306442L;
	public long lifeTime = Settings.DamageTip.defaultLifeTime;
	public long bornTime;
	public Color color = Settings.DamageTip.defaultColor;

	public DamageTip(String message, Vector2d position) {
		super(message);
		this.position = position;
		this.isMaterial = false;
		this.bornTime = Runner.inst().tick;
		this.speed.assign(Settings.DamageTip.defaultSpeed);
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		Font oldFont = graphics.getFont();
		long time = System.currentTimeMillis() - bornTime;
		int textSz = (int)(time*(Settings.DamageTip.startTextSize-Settings.DamageTip.endTextSize)/lifeTime +
				Settings.DamageTip.endTextSize);
		graphics.setFont(new Font("Courier new", Font.PLAIN, textSz));
		graphics.drawString(message, (float)position.x, (float)position.y);
		graphics.setFont(oldFont);
	}

}
