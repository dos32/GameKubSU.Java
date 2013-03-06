package game.graphics;

import game.Runner;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class MainCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	protected BufferStrategy bs = null;
	protected Graphics2D graphics = null;
	
	public Graphics2D graphics() {
		prepareGraphics();
		return graphics;
	}
	
	protected void prepareGraphics() {
		bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			bs = getBufferStrategy();
			requestFocus();
			graphics = (Graphics2D) bs.getDrawGraphics();
		}
		if(bs.contentsLost())
			graphics = (Graphics2D) bs.getDrawGraphics();
	}
	
	@Override
	public void paint(Graphics g) {
		prepareGraphics();
		bs.show();
	}
	
	@Override
	public void update(Graphics g) {
		
	}
	
	public void render() {
		Runner.inst().renderer.render(graphics());
		bs.show();
	}
	
}
