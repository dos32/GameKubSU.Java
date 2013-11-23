package game.physics.effects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.graphics.Sprite;
import game.physics.objects.DrawableUnit;
import game.physics.objects.Vehicle;

public class KamikadzeHalo extends DrawableUnit {
	
	private static final long serialVersionUID = 1964896598159068570L;
	
	public Vehicle vehicle;
	
	private static Sprite sprite = new Sprite("/res/carHalo1.png");
	
	public KamikadzeHalo(Vehicle vehicle) {
		this.vehicle = vehicle;
		vehicle.kamikadzeHalo = this;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		AffineTransform oldTransform = graphics.getTransform();
		graphics.translate(vehicle.position.x - sprite.getWidth()/2, vehicle.position.y - sprite.getHeight()/2);
//		graphics.rotate(vehicle.angle+Math.PI/2);
		graphics.drawImage(sprite.getFrame(), null, null);
		graphics.setTransform(oldTransform);
	}
	
}
