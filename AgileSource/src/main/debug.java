package main;

public class debug {
	
	private static boolean logEnabled = true;
	private static boolean errEnabled = true;

	public static void log(String msg)
	{
		if(logEnabled)
			System.out.println("DEBUG: " + msg);
	}
	
	public static void error(String msg)
	{
		if(errEnabled)
			System.out.println("DEBUG ERROR: " + msg);
	}
}
