package game.engine;

import game.utils.Vector2d;

import java.awt.Color;

public final class Settings {
	public static int tickDuration = 12;
	public static int maxTicksCount = 5000;
	public static int extraTicksCount = 500;
	public static int waitBeforeDuration = 100;
	public static int waitAfterDuration = 5000;
	public static int waitInterval = 1;
	
	/**
	 * Count of ticks to wait before shutdown the game since all bots became dead
	 */
	public static int waitWhenAllDead = 300;

	public static int playersCount = 6;
	
	public static int StatusBarZIndex = 10000;
	
	public final static class Connection {
		public static int buffer_size = 1<<20;
	}

	public final static class Server {
		public static int acceptTimeout = 30000;
		public static int tickTimeout = 100;
		public static int port = 4000;
		public static String ip = "127.0.0.1";
	}

	public final static class World {
		public static double width = 1024.0d, height = 640.0d;
	}

	public final static class Vehicle {
		public static int maxHealth = 100;
		public static double maxNitro = 250;
		public static double maxNitroBoost = 1; // Max power of nitro
		public static double powerCoeff = 0.07;
		public static double turnCoeff = 0.07 * Math.PI / 180;
		public static double maxPowerFactor = 1;
		public static double maxTurnFactor = 1;
		public static double damageFactor = 7e0;
		public static double defaultRadius = 10;
		public static int placeTryCount = 10;
		public final static class HealthBar {
			public static int height = 4;
			public static int descent = 7;
			public static Color defaultColor = Color.blue;
			public static Color borderColor = Color.black;
		}
		public final static class NitroBar {
			public static int height = 4;
			public static int descent = 7;
			public static Color defaultColor = Color.green;
			public static Color borderColor = Color.black;
		}
	}

	public final static class Frame {
		public static int contentWidth = (int) World.width,
				contentHeight = (int) World.height;
		public static String title = "Game Engine v2.0";
	}
	
	public final static class Physics {
		public static double defaultFrictionCoeff = 0.01,
				defaultMass = 1;
	}
	
	public final static class Renderer {
		public static boolean drawImages = true;
	}
	
	public final static class PerfMonitor {
		public final static class FPS {
			public static int resetPeriod = 500; // number of ticks, after that stats reset
			public static long realTimeSpan = (long)1e9; // span of real time in which stats must be recalculated; ns
		}
	}
	
	public final static class BonusSpawner {
		public static double probability = 0.005; // Probability that bonus spawns in tick
		public static double pFlag = 0.6;
		public static double pMedKit = 0.3;
		public static double pNitro = 0.1;
		public static int placementTries = 100;
		public static double defaultRadius = 10;
	}
	
	public final static class Bonus {
		public final static class Medkit {
			public static int healthSize = 20;
			public static int goalPoints = 10;
		}
		public final static class Flag {
			public static int goalPoints = 100;
		}
		public final static class Nitro {
			public static double nitroCount = 150;
			public static int goalPoints = 15;
		}
		public static int defaultLifeTime = 500;
	}
	
	public final static class InfoTip {
		public static double defaultTextSize = 14;
	}
	
	public final static class AnimatedTip {
		public static Color dmgColor = Color.red,
				healColor = Color.blue,
				goalColor = Color.orange,
				nitroColor = Color.green;
		public static int defaultLifeTime = 50; // life time in ticks
		public static double startTextSize = 16,
				endTextSize = 12;
		public static Vector2d defaultSpeed = new Vector2d(0, -0.1);
		public static int defaultZIndex = 1000;
		public static double stackIndent = 1;
	}

}
