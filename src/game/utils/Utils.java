package game.utils;

public final class Utils {
	
	private Utils() {
	}
	
	/**
	 * Trims value to interval [a,b]
	 */
	public static double trimToInterval(double value, double a, double b) {
		return Math.max(a, Math.min(value, b));
	}
	
}
