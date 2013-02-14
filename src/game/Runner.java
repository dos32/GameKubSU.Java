package game;

import java.awt.Color;

import game.engine.AIListener;
import game.engine.MainFrame;
import game.engine.Renderer;
import game.engine.Settings;
import game.physics.Physics;
import game.physics.World;
import game.physics.objects.Circle;
import game.physics.objects.HalfPlane;
import game.physics.objects.InfoTip;
import game.physics.objects.Unit;
import game.utils.Vector2d;

public class Runner {
	public final MainFrame mainFrame;
	public final World world;
	public final Renderer renderer;
	protected AIListener ailistener;
	public final Physics physics;
	
	public long tick = 0;

	public Runner() {
		physics = new Physics(this);
		mainFrame = new MainFrame(this);
		renderer = new Renderer(this);
		world = new World(this);
		ailistener = new AIListener(this);
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
		world.infoTick.message = String.format("ticks = %s", tick);
	}
	
	public void addUnit(Unit unit) {
		// TODO
	}
	
	protected void showGreeting() {
		InfoTip info = new InfoTip("Wait for client AI");
		info.position.assign(world.width/2, world.height/2);
		world.addUnit(info);
		mainFrame.mainCanvas.render();
	}

	protected void prepareGame() {
		world.clearUnits();
		// edges:
		world.addUnit(new HalfPlane(new Vector2d(0, 0), 0));
		world.addUnit(new HalfPlane(new Vector2d(world.width, 0), Math.PI));
		world.addUnit(new HalfPlane(new Vector2d(0, world.height), 3*Math.PI/2));
		world.addUnit(new HalfPlane(new Vector2d(0, 0), Math.PI/2));
		// test:
		Circle c = new Circle(100);
		c.mass = Math.pow(c.radius,2)*Math.PI*0.01;
		c.position.assign(Math.random()*world.width, Math.random()*world.height);
		c.speed.assign(Math.random()-0.5, Math.random()-0.5);
		c.speed.scale(20);
		world.addUnit(c);
		for(int i=0; i<1000; i++)
		{
			c = new Circle((Math.random()+0.5)*8);
			c.mass = Math.pow(c.radius,2)*Math.PI*0.01;
			c.position.assign(Math.random()*world.width, Math.random()*world.height);
			c.speed.assign(Math.random()-0.5, Math.random()-0.5);
			c.speed.scale(2);
			world.addUnit(c);
		}
		world.infoTick = new InfoTip(String.format("Ticks=%s", world.tick));
		world.infoTick.isStatic = true;
		world.infoTick.color = Color.red;
		world.addUnit(world.infoTick);
		
		world.infoRendererFPS = new InfoTip("");
		world.infoRendererFPS.isStatic = true;
		world.infoRendererFPS.position.assign(0, 16);
		world.infoRendererFPS.color = Color.red;
		renderer.updateFPS();
		world.addUnit(world.infoRendererFPS);
		
		world.infoPhysFPS = new InfoTip("");
		world.infoPhysFPS.isStatic = true;
		world.infoPhysFPS.position.assign(0, 32);
		world.infoPhysFPS.color = Color.red;
		physics.updateFPS();
		world.addUnit(world.infoPhysFPS);
	}
	
	protected void showStats() {
		world.clearUnits();
		InfoTip info = new InfoTip("Game over");
		info.position = new Vector2d(world.width/2, world.height/2);
		world.addUnit(info);
		mainFrame.mainCanvas.render();
	}

	protected void tick() {
		physics.tick();
		mainFrame.mainCanvas.render();
		tick++;
		// TODO remove field tick from World
		/*world.tick++;
		world.infoTick.message = String.format("Ticks=%s", world.tick);*/
	}
	
	public void Start() {
		showGreeting();
		delay(Settings.waitBeforeDuration);
		prepareGame();
		while(world.tick < Settings.maxTicksCount)
		{
			long t1 = System.currentTimeMillis();
			tick();
			// specially for long ticks instead of delay:
			waitForTime(t1+Settings.tickDuration);
		}
		showStats();
		delay(Settings.waitAfterDuration);
		System.out.println(renderer.dbg_ticks);
		mainFrame.dispose();
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.Start();
	}

}
