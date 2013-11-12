package game.physics.effects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Runner;
import game.engine.Tickable;
import game.graphics.Sprite;
import game.physics.objects.DrawableUnit;
import game.utils.Vector2d;

public class Explosion extends DrawableUnit implements Tickable {
	
	public enum Type {
		SMALL,
		MEDIUM,
		BIG
	}
	
	private static final long serialVersionUID = 59785113286419510L;
	
	public Type type;
	private int bornTime = 0;
	private static Sprite[] sprites = new Sprite[] {
		new Sprite("/res/Explosion1.png", 7, 1, 0.2),
		new Sprite("/res/Explosion2.png", 8, 1, 0.2),
		new Sprite("/res/Explosion3.png", 4, 2, 0.1)
	};
	
	public Explosion(Type type, Vector2d position) {
		bornTime = Runner.inst().tick;
		this.position.assign(position);
		angle = Math.random() * Math.PI * 2;
		this.type = type;
	}
	
	private Sprite getSprite() {
		return sprites[type.ordinal()];
	}
	
	public int getLifeTime() {
		return Runner.inst().tick - bornTime;
	}

	@Override
	public void draw(Graphics2D graphics) {
		AffineTransform oldTransform = graphics.getTransform();
		graphics.translate(position.x, position.y);
		graphics.rotate(angle+Math.PI/2);
		graphics.drawImage(getSprite().getFrame(getLifeTime()), null, null);
		graphics.setTransform(oldTransform);
	}

	@Override
	public void tick() {
		if((getLifeTime()+2) * getSprite().animationSpeed >= getSprite().getFramesCount())
			dispose();
	}

}
