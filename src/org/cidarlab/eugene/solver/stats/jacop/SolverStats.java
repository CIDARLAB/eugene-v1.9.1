package org.cidarlab.eugene.solver.stats.jacop;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SolverStats {

	private Set<Measurement> measurements;
	
	public SolverStats() {
		this.measurements = new HashSet<Measurement>();
	}
	
	public void add(String key, double value) {
		this.measurements.add(new Measurement(key, value));
	}
	
	public void print() {
		System.out.println("**** STATISTICS ****");
		Iterator<Measurement> it = this.measurements.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
