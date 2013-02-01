package game.model;

import java.awt.Graphics2D;

import game.utils.Vector2d;

public class Bonus extends Unit {
	protected BonusType type;

	public Bonus(BonusType bonusType) {
		type = bonusType;
	}

	@Override
	public double getPenetrationDepth(Unit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2d getNorm(Unit unit) {
		// TODO Auto-generated method stub
		return null;
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
