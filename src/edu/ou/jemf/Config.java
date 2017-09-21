package edu.ou.jemf;

public class Config {

	public static double window[] = { 45d, 65d };

	public static double JEMFFunction(double x) {
		double y;
		y = x * (1.0 + Math.random());
		return y;
	}

}
