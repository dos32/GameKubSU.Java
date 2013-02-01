package game.engine;

import game.model.World;

public class Runner {
	public final MainFrame mainFrame;
	public final World world;
	public final Renderer renderer;
	protected AIListener ailistener;
	public final Physics physics;

	public static void main(String[] args) {
		new Runner();
	}

	public Runner() {
		renderer = new Renderer(this);
		physics = new Physics(this);
		mainFrame = new MainFrame(this);
		world = new World(this);
		world.start();
	}

}
