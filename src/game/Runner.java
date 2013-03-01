package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import client.ClientRunner;

import game.engine.BonusSpawner;
import game.engine.Tickable;
import game.engine.TipPlacer;
import game.engine.UnitContainer;
import game.engine.Settings;
import game.engine.World;
import game.graphics.MainFrame;
import game.graphics.Renderer;
import game.physics.Physics;
import game.physics.objects.Bonus;
import game.physics.objects.HalfPlane;
import game.physics.objects.InfoTip;
import game.physics.objects.Obstacle;
import game.physics.objects.Unit;
import game.physics.objects.Vehicle;
import game.server.ClientListener;
import game.server.Server;
import game.utils.Vector2d;

public class Runner implements UnitContainer {
	/* 
	 * Current instance of Runner
	 * Important: because it is not singleton,
	 *	using multiple instances of this class
	 *	suggest manual switch field current to
	 *	each of runners, when its nested components
	 *	can need to access its fields
	 */
	private static Runner currentInstance;
	public static Runner inst() {
		return currentInstance;
	}
	
	public Graphics2D graphics() {
		return mainFrame.mainCanvas.graphics();
	}
	
	public final MainFrame mainFrame;
	public final World world;
	public final Renderer renderer;
	public final Server server;
	public final Physics physics;
	public final BonusSpawner bonusSpawner;
	
	public InfoTip infoTick, infoRendererFPS, infoPhysFPS;
	
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
		}
	}
	
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
				Thread.sleep(Settings.waitInterval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
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
	
	protected void showGreeting() {
		InfoTip info = new InfoTip("Wait for client AI");
		info.position.assign(world.width/2, world.height/2);
		mainFrame.mainCanvas.render();
		// test clients:
		for(int i=0; i<Settings.playersCount; i++) {
			new Thread(new ClientRunner()).start();
		}
		/*for(int i=0; i<(Settings.playersCount); i++) {
			new Thread(new testBot.ClientRunner()).start();
		}*/
		//
		clearUnits();
		server.acceptClients();
		// vehicles:
		for(int i=0; i<server.clients.size(); i++) {
			Vehicle v = new Vehicle(server.clients.get(i).player);
			physics.collideForce.placeNoCollide(v, Settings.Vehicle.placeTryCount);
		}
		// stats:
		ArrayList<InfoTip> tips = new ArrayList<InfoTip>();
		for(ClientListener listener : server.clients) {
			InfoTip playerTip = new InfoTip("l");
			playerTip.color = listener.player.vehicles.get(0).getColor();
			listener.player.statsTip = playerTip;
			listener.player.changeScore(0);
			tips.add(playerTip);
		}
		TipPlacer.placeTips(tips, new Vector2d(0, 0));
	}

	protected void prepareGame() {
		// edges:
		new HalfPlane(new Vector2d(0, 0), 0);
		new HalfPlane(new Vector2d(world.width, 0), Math.PI);
		new HalfPlane(new Vector2d(0, world.height), 3*Math.PI/2);
		new HalfPlane(new Vector2d(0, 0), Math.PI/2);
		
		// test:
		Obstacle c = new Obstacle(100);
		c.mass = Math.pow(c.radius,2)*Math.PI*0.01;
		c.position.assign(Math.random()*world.width, Math.random()*world.height);
		c.speed.assign(Math.random()-0.5, Math.random()-0.5);
		c.speed.scale(20);
		for(int i=0; i<50; i++) {
			c = new Obstacle(10);
			c.mass = Math.pow(c.radius,2)*Math.PI*0.01;
			c.position.assign(Math.random()*world.width, Math.random()*world.height);
			c.speed.assign(Math.random()-0.5, Math.random()-0.5);
			c.speed.scale(2);
		}
		
		infoTick = new InfoTip(String.format("Ticks=%s", tick));
		infoTick.position.assign(1100, 0);
		infoTick.isStatic = true;
		infoTick.color = Color.red;
		
		infoRendererFPS = new InfoTip("");
		infoRendererFPS.isStatic = true;
		infoRendererFPS.position.assign(1100, 16);
		infoRendererFPS.color = Color.red;
		renderer.updateFPS();
		
		infoPhysFPS = new InfoTip("");
		infoPhysFPS.isStatic = true;
		infoPhysFPS.position.assign(1100, 32);
		infoPhysFPS.color = Color.red;
		physics.updateFPS();
	}
	
	protected void showStats() {
		clearUnits();
		InfoTip info = new InfoTip("Game over");
		info.position = new Vector2d(world.width/2, world.height/2);
		mainFrame.mainCanvas.render();
	}

	protected void tick() {
		bonusSpawner.tick();
		world.toJSON();
		server.tick();
		physics.tick();
		ArrayList<Unit> objects = new ArrayList<Unit>(physics.objects);
		for(Unit unit : objects)
			if(unit instanceof Tickable)
				((Tickable)unit).tick();
		mainFrame.mainCanvas.render();
		tick++;
		world.tick = tick;
		updateTick();
	}
	
	public void start() {
		showGreeting();
		delay(Settings.waitBeforeDuration);
		prepareGame();
		while(tick < Settings.maxTicksCount)
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
