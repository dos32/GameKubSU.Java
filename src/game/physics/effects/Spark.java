package game.physics.effects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Runner;
import game.engine.Tickable;
import game.graphics.Sprite;
import game.physics.objects.DrawableUnit;
import game.utils.Vector2d;

public class Spark extends DrawableUnit implements Tickable {
	
	private static final long serialVersionUID = 2891434507753252105L;
	
	private int bornTime = 0;
	private static Sprite
		sprite = new Sprite("/res/Spark1.png", 6, 1, 0.2);
	
	public Spark(Vector2d position) {
		bornTime = Runner.inst().tick;
		this.position.assign(position);
		angle = Math.random() * Math.PI * 2;
	}
	
	public int getLifeTime() {
		return Runner.inst().tick - bornTime;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		AffineTransform oldTransform = graphics.getTransform();
		graphics.translate(position.x, position.y);
		graphics.rotate(angle+Math.PI/2);
		graphics.drawImage(sprite.getFrame(getLifeTime()), null, null);
		graphics.setTransform(oldTransform);
	}

	@Override
	public void tick() {
		if((getLifeTime()+2) * sprite.animationSpeed >= sprite.getFramesCount())
			dispose();
	}
	
}
