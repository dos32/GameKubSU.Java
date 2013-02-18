package game.physics.objects;

import java.awt.Graphics2D;

public class DamageTip extends InfoTip {
	private static final long serialVersionUID = 6634407963815306442L;
	public int lifeTime;
	public int bornTime;
	public double width, height;
	
	/*
	 * Recalcs width & height of this object
	 * 	in specified graphics metrics
	 */
	public void needMetrics(Graphics2D g) {
		// TODO
	}

	public DamageTip(String message) {
		super(message);
	}
	
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		// TODO
	}

}
