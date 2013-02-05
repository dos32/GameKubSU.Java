package game.physics.objects;

import java.awt.Graphics2D;

public class Bonus extends Unit {
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
