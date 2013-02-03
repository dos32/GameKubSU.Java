package game.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import game.Runner;
import game.physics.objects.Unit;

public final class Renderer {
	protected final Runner runner;
	public boolean updated = false;
	public int dbg_ticks=0;

	protected BufferedImage buffer;
	protected Graphics2D bufferGraphics;

	public Renderer(Runner runner) {
		this.runner = runner;
		buffer = new BufferedImage(Settings.Frame.width, Settings.Frame.height,
				BufferedImage.TYPE_INT_ARGB);
		bufferGraphics = (Graphics2D) buffer.getGraphics();
		bufferGraphics.setFont(new Font("Courier new", Font.PLAIN, 14));
		bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		bufferGraphics.setBackground(Color.white);
		bufferGraphics.setColor(Color.black);
	}

	public void render(Graphics2D graphics) {
		if (updated) {
			bufferGraphics.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
			for (Unit unit : runner.world.allUnits)
				unit.draw(bufferGraphics);
			updated = false;
		}
		graphics.drawImage(buffer, null, 0, 0);
		dbg_ticks++;
	}
}
