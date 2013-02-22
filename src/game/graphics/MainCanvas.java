package game.graphics;

import game.Runner;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class MainCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	
	public MainCanvas() {
	}
	
	@Override
	public void paint(Graphics g) {
		
	}
	
	@Override
	public void update(Graphics g) {
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			bs=getBufferStrategy();
			requestFocus();
		}
		Graphics2D graphics = (Graphics2D) bs.getDrawGraphics();
		Runner.inst().renderer.render(graphics);
		bs.show();
	}
	
}
