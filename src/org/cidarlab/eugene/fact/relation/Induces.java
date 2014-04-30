package org.cidarlab.eugene.fact.relation;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.fact.BinaryFact;


public class Induces 
	extends BinaryFact {

	public Induces(long A, long B) {
		super(A, B);
	}

	public Induces(String A, String B) {
		super(A, B);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			if(null != this.getAName()) {
				sb.append(this.getAName());
			} else {
				sb.append(SymbolTables.getNameById(this.getAId()));
			}
			
			sb.append(" INDUCES ");
			
			if(null != this.getAName()) {
				sb.append(this.getBName());
			} else {
				sb.append(SymbolTables.getNameById(this.getBId()));
			}
		} catch(Exception e) {
			
		}
		return sb.toString();
	}

	@Override
	public String getRelationType() {
		return Relation.INDUCES.toString();
	}

}
