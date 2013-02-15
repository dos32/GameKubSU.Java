package game.engine;

public final class Settings {
	public static int tickDuration = 12;
	public static int maxTicksCount = 5000;
	public static int waitBeforeDuration = 1000;
	public static int waitAfterDuration = 1000;
	public static int waitInterval = 1;
	
	public static int playersCount = 1;

	public final static class AIListener {
		public static int acceptTimeout = 100;
		public static int timeout = 5000;
		public static int port = 4000;
		public static String ip = "127.0.0.1";
	}

	public final static class World {
		public static double width = 1300.0d, height = 700.0d;
	}

	public final static class Vehicle {
		public static double maxHealth = 100, maxArmor = 200, maxFuel = 100,
				maxNitro = 10, maxSpeed = 1, maxPower = 0.1, maxTurn = 0.01 * Math.PI / 180;
	}

	public final static class Frame {
		public static int contentWidth = (int) World.width,
				contentHeight = (int) World.height;
	}
	
	public final static class Physics {
		public static double defaultFrictionCoeff = 0.003,
				defaultMass = 1;
		public static double FPSMeasureTimeMs = 1000;
	}
	
	public final static class Renderer {
		public static int FPSMeasureTimeMs = 1000;
	}
}
