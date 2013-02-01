package game.model;

import game.engine.Player;
import game.engine.Runner;
import game.engine.Settings;
import game.utils.Vector2d;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

/*
 * Contains all visible objects,
 * and info about players
 */
public class World implements Externalizable {
	private static final long serialVersionUID = 5624391324336770137L;
	protected final Runner runner;

	public int tick = 0;
	public final double width = Settings.World.width,
			height = Settings.World.height;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	public ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
	public ArrayList<Unit> allUnits = new ArrayList<Unit>();

	public World(Runner runner) {
		this.runner = runner;
	}
	
	protected void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		showGreeting();
		delay(Settings.waitBeforeDuration);
		initialize();
		while (this.tick < Settings.maxTicksCount)
		{
			tick();
			delay(Settings.tickDuration);
		}
		showStats();
		delay(Settings.waitAfterDuration);
		runner.mainFrame.dispose();
	}
	
	protected void showGreeting() {
		InfoTip info = new InfoTip("Wait for client AI");
		info.position.assign(width/2, height/2);
		addUnit(info);
		runner.mainFrame.repaint();
	}

	protected void initialize() {
		clearUnits();
		Vehicle vehicle = new Vehicle(100, 200);
		vehicle.angle = Math.PI / 6;
		vehicle.angularSpeed = 0.01 * Math.PI / 3;
		vehicle.position.assign(50, 100);
		vehicle.speed.assign(1.1, 0.5);
		addUnit(vehicle);
		InfoTip info = new InfoTip(String.format("Ticks=%s", this.tick));
		addUnit(info);
	}
	
	protected void showStats() {
		clearUnits();
		InfoTip info = new InfoTip("Game over");
		info.position = new Vector2d(width/2, height/2);
		addUnit(info);
		runner.mainFrame.repaint();
	}

	protected void tick() {
		runner.physics.tick();
		runner.mainFrame.repaint();
		this.tick++;
		tips.get(0).message = String.format("Ticks=%s", this.tick);
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
			allUnits.remove(allUnits.size() - 1);
			System.err.println("World.addUnit::Cant recognize type of object");
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
