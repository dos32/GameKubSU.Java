package game.engine;

public final class Settings {
	public static int tickDuration = 12;
	public static int maxTicksCount = 5000;
	public static int waitBeforeDuration = 1000;
	public static int waitAfterDuration = 1000;
	public static int waitInterval = 1;
	
	public static int playersCount = 2;

	public final static class Server {
		public static int acceptTimeout = 10000;
		public static int timeout = 5000;
		public static int port = 4000;
		public static String ip = "127.0.0.1";
	}

	public final static class World {
		public static double width = 1300.0d, height = 700.0d;
	}

	public final static class Vehicle {
		public static double maxHealth = 100, maxArmor = 200, maxFuel = 100,
				maxNitroPower = 0.05, maxPower = 0.03, maxTurn = 0.03 * Math.PI / 180;
	}

	public final static class Frame {
		public static int contentWidth = (int) World.width,
				contentHeight = (int) World.height;
	}
	
	public final static class Physics {
		public static double defaultFrictionCoeff = 0.01,
				defaultMass = 1;
	}
	
	public final static class Renderer {
	}
	
	public final static class PerfMonitor {
		public final static class FPS {
			public static int resetPeriod = 100; // number of ticks, after that stats reset; ticks
			public static long realTimeSpan = (long)1e9; // span of real time in which stats must be recalculated; ns
		}
	}
}
