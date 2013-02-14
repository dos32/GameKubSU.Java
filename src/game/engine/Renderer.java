package game.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.Runner;
import game.physics.objects.Unit;

public final class Renderer {
	protected final Runner runner;
	public boolean updated = false;
	public int dbg_ticks=0;
	
	public double fps = 0;
	protected long framesCount = 0, time = 0;

	public Renderer(Runner runner) {
		this.runner = runner;
	}
	
	public void updateFPS() {
		if(fps == 0)
			runner.world.infoRendererFPS.message = String.format("Renderer.FPS = %s", "n/a");
		else {
			runner.world.infoRendererFPS.message = String.format("Renderer.FPS = %s", Math.round(fps));
		}
	}

	public void render(Graphics2D graphics) {
		long t1 = System.nanoTime();
		if (updated)
		{
			graphics.setFont(new Font("Courier new", Font.PLAIN, 14));
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			graphics.setBackground(Color.white);
			graphics.setColor(Color.black);
			graphics.clearRect(0, 0, (int)Settings.World.width, (int)Settings.World.height);
			for (Unit unit : runner.world.allUnits)
				unit.draw(graphics);
			updated = false;
		}
		time+=System.nanoTime()-t1;
		framesCount++;
		if(framesCount > Settings.Renderer.FPSFramesCount) {
			fps = framesCount * 1e9 / time;
			updateFPS();
			framesCount=0;
			time=0;
		}
		dbg_ticks++;
	}
}
