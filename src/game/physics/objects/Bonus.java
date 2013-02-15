package game.physics.objects;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Bonus extends Unit implements Serializable {
	private static final long serialVersionUID = 8357488035574848722L;
	protected BonusType type;

	public Bonus(BonusType bonusType) {
		type = bonusType;
	}

	@Override
	public void draw(Graphics2D graphics) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getSquare() {
		// TODO Auto-generated method stub
		return 0;
	}
}
