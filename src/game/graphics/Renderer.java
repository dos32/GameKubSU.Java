package game.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.Runner;
import game.engine.Settings;
import game.physics.objects.Unit;

public final class Renderer {
	public boolean updated = false;
	
	protected double fps = 0;
	protected long framesCount = 0, time = 0, lastRealTime = 0;
	
	public void updateFPS() {
		if(Runner.inst().infoRendererFPS == null)
			return;
		if(fps == 0)
			Runner.inst().infoRendererFPS.message = String.format("Renderer.FPS = %s", "n/a");
		else {
			Runner.inst().infoRendererFPS.message = String.format("Renderer.FPS = %s", Math.round(fps));
		}
	}

	public void render(Graphics2D graphics) {
		long t1 = System.nanoTime();
		if(updated)
		{
			graphics.setFont(new Font("Courier new", Font.PLAIN, 14));
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			graphics.setBackground(Color.white);
			graphics.setColor(Color.black);
			graphics.clearRect(0, 0, (int)Settings.World.width, (int)Settings.World.height);
			for(Unit unit : Runner.inst().physics.objects)
				unit.draw(graphics);
			updated = false;
		}
		time+=System.nanoTime()-t1;
		framesCount++;
		if(System.nanoTime() - lastRealTime > Settings.PerfMonitor.FPS.realTimeSpan) {
			lastRealTime = System.nanoTime();
			fps = framesCount * 1e9 / time;
			updateFPS();
			if (framesCount > Settings.PerfMonitor.FPS.resetPeriod) {
				framesCount = 0;
				time = 0;
			}
		}
	}
}
