package game.physics.objects;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Bonus extends Circle implements Serializable {
	private static final long serialVersionUID = 8357488035574848722L;
	protected BonusType type;

	public Bonus(BonusType bonusType) {
		super(10);
		type = bonusType;
	}

	@Override
	public void draw(Graphics2D graphics) {
		// TODO Auto-generated method stub
		super.draw(graphics);
	}
}
