package game.physics.effects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import game.Runner;
import game.engine.Settings;
import game.engine.Tickable;
import game.engine.TipPlacer;
import game.graphics.Sprite;
import game.physics.objects.AnimatedTip;
import game.physics.objects.DrawableUnit;
import game.physics.objects.InfoTip;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;

public class KamikadzeHalo extends DrawableUnit implements Tickable {
	
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
	
	@Override
	public void tick() {
//		if(vehicle.health <= 0) {
//			new Explosion(Explosion.Type.BIG, vehicle.position);
//			ArrayList<Unit> damaged = Runner.inst().physics.findInRadius(vehicle.position, Settings.Bonus.Kamikadze.explodeRadius);
//			for(Unit unit : damaged) {
//				if(unit instanceof Vehicle) {
//					ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
//					double dist = vehicle.position.diff(unit.position).norm() * 0.1;
////					double damage = Math.pow(2, -dist) * Settings.Vehicle.maxHealth;
//					double damage = 1.0/(1.0 + Math.pow(dist, 2)) * Settings.Vehicle.maxHealth * 10;
//					int healthDelta = ((Vehicle)unit).changeHealth(-(int)Math.round(damage));
//					if(Math.abs(healthDelta) > 0)
//						tips.add(new AnimatedTip(String.format("%+d", healthDelta), Settings.AnimatedTip.dmgColor));
//					TipPlacer.placeTips(tips, unit.position);
//				}
//			}
//			dispose();
//		}
	}
	
}
