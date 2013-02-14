package game.physics;

import game.Runner;
import game.engine.Player;
import game.engine.Settings;
import game.physics.objects.Bonus;
import game.physics.objects.InfoTip;
import game.physics.objects.Obstacle;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * Contains all visible objects,
 * and info about players
 */
public class World implements Serializable, Externalizable {
	private static final long serialVersionUID = 5624391324336770137L;
	protected final Runner runner;

	public int tick = 0;
	public final double width, height;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	// TODO move tips to Physics
	public ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
	public ArrayList<Unit> allUnits = new ArrayList<Unit>();
	
	// TODO delete depend of InfoTips
	public InfoTip infoTick, infoRendererFPS, infoPhysFPS;

	public World(Runner runner) {
		this.runner = runner;
		this.width = Settings.World.width;
		this.height = Settings.World.height;
	}

	public void addUnit(Unit unit) {
		allUnits.add(unit);
		runner.renderer.updated = true;
		if (unit.getClass() == Vehicle.class)
			vehicles.add((Vehicle) unit);
		else if (unit.getClass() == Obstacle.class)
			obstacles.add((Obstacle) unit);
		else if (unit.getClass() == Bonus.class)
			bonuses.add((Bonus) unit);
		else if (unit.getClass() == InfoTip.class)
			tips.add((InfoTip) unit);
		else {
			//allUnits.remove(allUnits.size() - 1);
			//System.err.println("World.addUnit::Cant recognize type of object");
		}
	}

	public void clearUnits() {
		allUnits.clear();
		vehicles.clear();
		obstacles.clear();
		bonuses.clear();
		tips.clear();
	}
	
	@Override
	public void readExternal(ObjectInput arg0) throws IOException,
			ClassNotFoundException {
		/*
		 * tick = arg0.readInt(); playersCount=arg0.readInt(); width =
		 * arg0.readDouble(); height = arg0.readDouble(); cars =
		 * (ArrayList<Car>) arg0.readObject(); obstacles = (ArrayList<Obstacle>)
		 * arg0.readObject(); bonuses = (ArrayList<Bonus>) arg0.readObject();
		 */
	}

	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		arg0.writeInt(tick);
		arg0.writeInt(players.size());
		arg0.writeDouble(width);
		arg0.writeDouble(height);
		arg0.writeObject(vehicles);
		arg0.writeObject(obstacles);
		arg0.writeObject(bonuses);
	}

}
