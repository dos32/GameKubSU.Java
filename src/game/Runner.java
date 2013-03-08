package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeSet;

import client.ClientRunner;

import game.engine.BonusSpawner;
import game.engine.Player;
import game.engine.Tickable;
import game.engine.TipPlacer;
import game.engine.UnitContainer;
import game.engine.Settings;
import game.engine.World;
import game.graphics.MainFrame;
import game.graphics.Renderer;
import game.physics.Physics;
import game.physics.objects.AnimatedTip;
import game.physics.objects.Bonus;
import game.physics.objects.HalfPlane;
import game.physics.objects.InfoTip;
import game.physics.objects.Obstacle;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;
import game.server.ClientListener;
import game.server.Server;
import game.utils.Vector2d;

/**
 * Class for assemblying entire game model
 * @author DOS
 *
 */
public class Runner implements UnitContainer {
	/**
	 * Current instance of Runner.
	 * Important: because it is not singleton,
	 *	using multiple instances of this class
	 *	suggest manual switch field current to
	 *	each of runners, when its nested components
	 *	can need to access its fields
	 */
	private static Runner currentInstance;
	
	/**
	 * Same as singleton instance(), but allowing
	 * multi instancing for future features
	 * @return	Current instance of Runner;
	 * in case of multi-instancing the last of them
	 */
	public static Runner inst() {
		return currentInstance;
	}
	
	public final MainFrame mainFrame;
	public final World world;
	public final Renderer renderer;
	public final Server server;
	public final Physics physics;
	public final BonusSpawner bonusSpawner;
	
	public InfoTip infoTick;
	
	public int tick = 0;

	public Runner() {
		currentInstance = this;
		physics = new Physics();
		mainFrame = new MainFrame();
		renderer = new Renderer();
		world = new World();
		server = new Server();
		bonusSpawner = new BonusSpawner();
		// Load resources:
		if(Settings.Renderer.drawImages) {
			Vehicle.prepareImages();
			Bonus.prepareImages();
			Obstacle.prepareImages();
			Renderer.prepareImages();
		}
	}
	
	/**
	 * 
	 * @return	Current rendering graphics context
	 */
	public Graphics2D graphics() {
		return mainFrame.mainCanvas.graphics();
	}
	
	/**
	 * Forces rendering of scene
	 */
	public void forceRender() {
		renderer.updated = true;
		mainFrame.mainCanvas.render();
	}
	
	private int allDeadTick = -1;
	public int allDeadTickout() {
		for(ClientListener listener : server.clients)
			if(listener.player.isAlive()) {
				allDeadTick = -1;
				return Settings.waitWhenAllDead;
			}
		if(allDeadTick == -1)
			allDeadTick = tick;
		return Settings.waitWhenAllDead - tick + allDeadTick;
	}
	
	/**
	 * Delays execution of current thread
	 * @param time
	 */
	protected void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Entering in waiting for time loop
	 * @param time
	 */
	protected void waitForTime(long time) {
		try {
			while(System.currentTimeMillis()<time)
				Thread.sleep(Settings.waitInterval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates tick label
	 */
	public void updateTick() {
		infoTick.message = String.format("ticks = %s", tick);
	}
	
	@Override
	public void addUnit(Unit unit) {
		world.addUnit(unit);
		physics.addUnit(unit);
		renderer.addUnit(unit);
	}

	@Override
	public void removeUnit(Unit unit) {
		world.removeUnit(unit);
		physics.removeUnit(unit);
		renderer.removeUnit(unit);
	}

	@Override
	public void clearUnits() {
		world.clearUnits();
		physics.clearUnits();
		renderer.clearUnits();
	}
	
	/**
	 * Shows greeting message
	 */
	protected void showGreeting() {
		infoTick = new InfoTip("Wait for clients AI");
		infoTick.position.assign(world.width/2, world.height/2);
		forceRender();
	}

	/**
	 * Prepares clients and game environment
	 */
	protected void prepareGame() {
		// test clients:
		for(int i=0; i<Settings.playersCount; i++)
			new Thread(new ClientRunner()).start();
		
		server.acceptClients();
		infoTick.message = "Clients are connected. Preparing the game.";
		forceRender();
		
		// vehicles:
		for(int i=0; i<server.clients.size(); i++) {
			Vehicle v = new Vehicle(server.clients.get(i).player);
			physics.collideForce.placeNoCollide(v, Settings.Vehicle.placeTryCount);
		}
		
		// ticks & stats:
		ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
		
		infoTick.color = Color.red;
		infoTick.setZIndex(Settings.StatusBarZIndex);
		tips.add(infoTick);
		updateTick();
		
		for(ClientListener listener : server.clients) {
			InfoTip playerTip = new InfoTip("");
			playerTip.setZIndex(Settings.StatusBarZIndex);
			playerTip.color = listener.player.vehicles.get(0).getColor();
			listener.player.statsTip = playerTip;
			listener.player.changeScore(0);
			tips.add(playerTip);
		}
		TipPlacer.placeTips(tips, 0, 0);
		tips.clear();
		
		// edges:
		new HalfPlane(new Vector2d(0, 0), 0);
		new HalfPlane(new Vector2d(world.width, 0), Math.PI);
		new HalfPlane(new Vector2d(0, world.height), 3*Math.PI/2);
		new HalfPlane(new Vector2d(0, 0), Math.PI/2);
		
		// test:
		Obstacle c = null;
		for(int i=0; i<25; i++) {
			c = new Obstacle(10);
			c.mass = Math.pow(c.radius,2)*Math.PI*0.01;
			c.speed.assign(Math.random()-0.5, Math.random()-0.5);
			c.speed.scale(0.8);
			physics.collideForce.placeNoCollide(c, Settings.Vehicle.placeTryCount);
		}
		
		// Perf tips:
		renderer.fpsInfo = new InfoTip("");
		renderer.fpsInfo.color = Color.red;
		tips.add(renderer.fpsInfo);
		renderer.updateFPS();
		
		physics.fpsInfo = new InfoTip("");
		physics.fpsInfo.color = Color.red;
		tips.add(physics.fpsInfo);
		physics.updateFPS();
		
		TipPlacer.placeTips(tips, 800, 0);
		tips.clear();
	}

	protected void tick() {
		bonusSpawner.tick();
		if(tick < Settings.maxTicksCount)
			server.tick();
		else if(tick == Settings.maxTicksCount) {
			// add bonus for end health level
			for(Vehicle vehicle : world.vehicles) {
				vehicle.engine.powerFactor = 0;
				vehicle.engine.turnFactor = 0;
				if(vehicle.health > 0) {
					int scoreDelta = vehicle.addGoalPoints(vehicle.health);
					new AnimatedTip(String.format("%+d", scoreDelta), Settings.AnimatedTip.goalColor).position.assign(vehicle.position);
				}
			}
		}
		physics.tick();
		ArrayList<Unit> objects = new ArrayList<Unit>(physics.objects);
		for(Unit unit : objects)
			if(unit instanceof Tickable)
				((Tickable)unit).tick();
		forceRender();
		tick++;
		world.tick = tick;
		updateTick();
	}
	
	/**
	 * Shows results statistics
	 */
	protected void showStats() {
		clearUnits();
		// stats:
		TreeSet<Player> sortedStats = new TreeSet<Player>();
		for(ClientListener listener : server.clients)
			sortedStats.add(listener.player);
		ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
		InfoTip info = new InfoTip("Game over");
		tips.add(info);
		NavigableSet<Player> sortedStatsDesc = sortedStats.descendingSet();
		for(Player player : sortedStatsDesc) {
			InfoTip playerTip = new InfoTip("");
			playerTip.color = player.vehicles.get(0).getColor();
			player.statsTip = playerTip;
			player.changeScore(0);
			tips.add(playerTip);
		}
		TipPlacer.placeTips(tips, new Vector2d(world.width/2, world.height/2));
		tips.clear();
		sortedStats.clear();
		forceRender();
	}
	
	public void start() {
		showGreeting();
		delay(Settings.waitBeforeDuration);
		prepareGame();
		while(tick < Settings.maxTicksCount + Settings.extraTicksCount && allDeadTickout() > 0)
		{
			long t1 = System.currentTimeMillis();
			tick();
			// specially for long ticks instead of delay:
			waitForTime(t1+Settings.tickDuration);
		}
		showStats();
		delay(Settings.waitAfterDuration);
		server.releaseClients();
		mainFrame.dispose();
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.start();
	}

}
