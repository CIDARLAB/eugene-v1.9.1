package org.cidarlab.eugene.fact;

public class Molecule 
	implements Fact {

	private String name;
	
	public Molecule(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
