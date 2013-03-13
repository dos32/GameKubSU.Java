package game.physics.objects;

import game.engine.Settings;

import java.awt.Graphics2D;

public class PerfTip extends InfoTip {
	private static final long serialVersionUID = 1L;

	public PerfTip(String message) {
		super(message);
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		if(Settings.PerfMonitor.active)
			super.draw(graphics);
	}
	
}
