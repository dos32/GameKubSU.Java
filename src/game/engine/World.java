package game.engine;

import game.Runner;
import game.physics.objects.Bonus;
import game.physics.objects.Obstacle;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

public class World implements Serializable {
	private static final long serialVersionUID = 5624391324336770137L;

	public int tick = 0;
	public final double width, height;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();

	public World() {
		this.width = Settings.World.width;
		this.height = Settings.World.height;
	}

	public void addUnit(Unit unit) {
		Runner.inst().renderer.updated = true;
		if (unit.getClass() == Vehicle.class)
			vehicles.add((Vehicle) unit);
		else if (unit.getClass() == Obstacle.class)
			obstacles.add((Obstacle) unit);
		else if (unit.getClass() == Bonus.class)
			bonuses.add((Bonus) unit);
	}
	
	public void removeUnit(Unit unit) {
		vehicles.remove(unit);
		obstacles.remove(unit);
		bonuses.remove(unit);
	}

	public void clearUnits() {
		vehicles.clear();
		obstacles.clear();
		bonuses.clear();
	}
	
	public void loadMap() {
		// TODO
	}

}
