package org.cidarlab.eugene.solver.stats.jacop;

public class Measurement {

	private String key;
	private double value;
	
	public Measurement(String key, double value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	public double getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.key+": "+this.value;
	}
}
