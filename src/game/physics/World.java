package game.physics;

import game.Runner;
import game.engine.Player;
import game.engine.Settings;
import game.physics.objects.Bonus;
import game.physics.objects.Circle;
import game.physics.objects.HalfPlane;
import game.physics.objects.InfoTip;
import game.physics.objects.Obstacle;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;
import game.utils.Vector2d;

import java.awt.Color;
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
	public final double width, height;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	public ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
	public ArrayList<Unit> allUnits = new ArrayList<Unit>();
	
	protected InfoTip infoTick, infoFPS;

	public World(Runner runner) {
		this.runner = runner;
		this.width = Settings.World.width;
		this.height = Settings.World.height;
	}
	
	/*public void init() {
	}*/
	
	protected void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void waitForTime(long time) {
		try {
			while(System.currentTimeMillis()<time)
				Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		showGreeting();
		delay(Settings.waitBeforeDuration);
		prepare();
		while (this.tick < Settings.maxTicksCount)
		{
			long t1 = System.currentTimeMillis();
			tick();
			//delay(Settings.tickDuration);
			waitForTime(t1+Settings.tickDuration);// specially for long ticks instead of delay
		}
		showStats();
		delay(Settings.waitAfterDuration);
		System.out.println(runner.renderer.dbg_ticks);
		runner.mainFrame.dispose();
	}
	
	protected void showGreeting() {
		InfoTip info = new InfoTip("Wait for client AI");
		info.position.assign(width/2, height/2);
		addUnit(info);
		runner.mainFrame.mainCanvas.render();
	}

	protected void prepare() {
		clearUnits();
		// edges:
		addUnit(new HalfPlane(new Vector2d(0, 0), 0));
		addUnit(new HalfPlane(new Vector2d(width, 0), Math.PI));
		addUnit(new HalfPlane(new Vector2d(0, height), 3*Math.PI/2));
		addUnit(new HalfPlane(new Vector2d(0, 0), Math.PI/2));
		// test:
		Circle c = new Circle(100);
		c.mass = Math.pow(c.radius,2)*Math.PI*0.01;
		//c.mass = Double.POSITIVE_INFINITY;
		c.position.assign(Math.random()*width, Math.random()*height);
		c.speed.assign(Math.random()-0.5, Math.random()-0.5);
		c.speed.scale(20);
		addUnit(c);
		for(int i=0; i<1000; i++)
		{
			c = new Circle((Math.random()+0.5)*8);
			c.mass = Math.pow(c.radius,2)*Math.PI*0.01;
			c.position.assign(Math.random()*width, Math.random()*height);
			c.speed.assign(Math.random()-0.5, Math.random()-0.5);
			c.speed.scale(2);
			addUnit(c);
		}
		/*for(int i=0; i<1; i++)
		{
			Vehicle vehicle = new Vehicle(100, 200);
			vehicle.angle = Math.PI / 6;
			vehicle.angularSpeed = 0.01 * Math.PI / 3;
			vehicle.position.assign(50, 100);
			vehicle.speed.assign(1.1, 0.5);
			addUnit(vehicle);
		}*/
		infoTick = new InfoTip(String.format("Ticks=%s", this.tick));
		infoTick.isStatic = true;
		infoTick.color = Color.red;
		addUnit(infoTick);
		infoFPS = new InfoTip(String.format("FPS=%s", Math.round(runner.renderer.fps)));
		infoFPS.isStatic = true;
		infoFPS.position.assign(0, 16);
		infoFPS.color = Color.red;
		addUnit(infoFPS);
	}
	
	protected void showStats() {
		clearUnits();
		InfoTip info = new InfoTip("Game over");
		info.position = new Vector2d(width/2, height/2);
		addUnit(info);
		runner.mainFrame.mainCanvas.render();
	}

	protected void tick() {
		runner.physics.tick();
		runner.mainFrame.mainCanvas.render();
		this.tick++;
		infoTick.message = String.format("Ticks=%s", this.tick);
		if(tick<Settings.Renderer.FPSFramesCount)
			infoFPS.message = "FPS calculating...";
		else
			infoFPS.message = String.format("FPS=%s", Math.round(runner.renderer.fps));
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
