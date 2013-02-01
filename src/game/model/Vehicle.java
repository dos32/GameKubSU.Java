package game.model;

import java.awt.Color;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Vehicle extends RectShapeUnit implements Externalizable {
	protected int indexInTeam;
	protected int playerId;
	protected String playerName;
	protected boolean isTeammate;
	protected Color color;

	protected double health;
	protected double armor;
	protected double fuel;
	protected double nitroFuel;

	// AI's controllers
	protected double motorPower;
	protected double turnAngle;
	
	public Vehicle(double width, double height) {
		super(width, height);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub

	}

}
