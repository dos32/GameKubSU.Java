package game.utils;

public class TimeWatch {
	
	private TimeWatch() {}
	
	//private static long start;
	private static long predEventTime;
	
	public static void start() {
		//start = System.nanoTime();
		predEventTime = System.nanoTime();
	}
	
	public static void event(String label) {
		long endTime = System.nanoTime();
		System.out.printf("%s: %.3f\n", label, (endTime - predEventTime)/1e6);
		predEventTime = System.nanoTime();
	}
	
}
