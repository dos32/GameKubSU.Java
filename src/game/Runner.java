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
import game.utils.Vector2d;

public class Runner {
	public final MainFrame mainFrame;
	public final World world;
	public final Renderer renderer;
	protected AIListener ailistener;
	public final Physics physics;
	
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

	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.Start();
	}

	public Runner() {
		physics = new Physics(this);
		mainFrame = new MainFrame(this);
		renderer = new Renderer(this);
		world = new World(this);
		//world.start();
		
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
		//c.mass = Double.POSITIVE_INFINITY;
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
		/*for(int i=0; i<1; i++)
		{
			Vehicle vehicle = new Vehicle(100, 200);
			vehicle.angle = Math.PI / 6;
			vehicle.angularSpeed = 0.01 * Math.PI / 3;
			vehicle.position.assign(50, 100);
			vehicle.speed.assign(1.1, 0.5);
			addUnit(vehicle);
		}*/
		world.infoTick = new InfoTip(String.format("Ticks=%s", world.tick));
		world.infoTick.isStatic = true;
		world.infoTick.color = Color.red;
		world.addUnit(world.infoTick);
		world.infoFPS = new InfoTip(String.format("FPS=%s", Math.round(renderer.fps)));
		world.infoFPS.isStatic = true;
		world.infoFPS.position.assign(0, 16);
		world.infoFPS.color = Color.red;
		world.addUnit(world.infoFPS);
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
		world.tick++;
		world.infoTick.message = String.format("Ticks=%s", world.tick);
		// TODO move FPS ouput to renderer
		if(world.tick<Settings.Renderer.FPSFramesCount)
			world.infoFPS.message = "FPS calculating...";
		else
			world.infoFPS.message = String.format("FPS=%s", Math.round(renderer.fps));
	}
	
	public void Start() {
		showGreeting();
		delay(Settings.waitBeforeDuration);
		prepareGame();
		while (world.tick < Settings.maxTicksCount)
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

}
