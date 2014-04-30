package org.cidarlab.eugene.solver.stats.jacop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stats {

	private static Measurement[] measurements;

	public static void set(int idx, String key, double value) {
		if(null == measurements) {
			measurements = new Measurement[9];
		}
		
		measurements[idx] = new Measurement(key, value);
	}
	
	public static void print() {
		System.out.println("**** STATISTICS ****");
		for(int i=0; i<measurements.length; i++) {
			System.out.println(measurements[i]);
		}
	}

	public static double[] toArray() {
		double[] array = new double[measurements.length];
		for(int i=0; i<measurements.length; i++) {
			array[i] = measurements[i].getValue();
		}
		return array;			
	}
	
	public static void clear() {
		if(null != measurements) {
			measurements = null;
		}
	}

}
