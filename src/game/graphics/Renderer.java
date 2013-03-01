package game.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import game.Runner;
import game.engine.UnitContainer;
import game.engine.Settings;
import game.physics.objects.Unit;

public final class Renderer implements UnitContainer {
	public boolean updated = false;
	
	protected double fps = 0;
	protected long framesCount = 0, time = 0, lastRealTime = 0;
	
	protected TreeSet<Drawable> objects = new TreeSet<Drawable>();
	
	protected static BufferedImage background = null;
	
	public static void prepareImages() {
		if(background == null) {
			try {
				URL url = Runner.class.getResource(String.format("/res/background.png"));
				BufferedImage image = ImageIO.read(url);
				background = new BufferedImage((int)Runner.inst().world.width, (int)Runner.inst().world.height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphics = background.createGraphics();
				for(int x = 0; x < background.getWidth(); x += image.getWidth())
					for(int y = 0; y < background.getHeight(); y += image.getHeight())
						graphics.drawImage(image, x, y, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
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
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			graphics.setBackground(Color.white);
			graphics.setColor(Color.black);
			if(Settings.Renderer.drawImages) {
				prepareImages();
				graphics.drawImage(background, 0, 0, null);
			}
			else
				graphics.clearRect(0, 0, (int)Settings.World.width, (int)Settings.World.height);
			for(Drawable drawable : objects)
				drawable.draw(graphics);
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
	
	@Override
	public void addUnit(Unit unit) {
		if(unit instanceof Drawable)
			objects.add((Drawable)unit);
	}

	@Override
	public void removeUnit(Unit unit) {
		if(unit instanceof Drawable)
			objects.remove(unit);
	}

	@Override
	public void clearUnits() {
		objects.clear();
	}
	
}
