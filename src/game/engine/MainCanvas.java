package game.engine;

import game.Runner;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class MainCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	protected final Runner runner;
	public BufferStrategy bs;
	public Graphics2D graphics;
	
	public MainCanvas(Runner runner) {
		this.runner = runner;
	}
	
	@Override
	public void paint(Graphics g) {
		if(bs != null)
			bs.show();
	}
	
	@Override
	public void update(Graphics g) {
		
	}
	
	public void render() {
		graphicsNeeded();
		runner.renderer.render(graphics);
		bs.show();
	}
	
	public void graphicsNeeded() {
		if(graphics == null)
		{
			bs = getBufferStrategy();
			if(bs == null) {
				createBufferStrategy(2);
				bs=getBufferStrategy();
				requestFocus();
			}
			graphics = (Graphics2D) bs.getDrawGraphics();
		}
	}
	
	public Graphics2D beginPaint() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			bs=getBufferStrategy();
			requestFocus();
		}
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		return g2;
	}
	
	public void endPaint() {
		getBufferStrategy().show();
	}
	
}
