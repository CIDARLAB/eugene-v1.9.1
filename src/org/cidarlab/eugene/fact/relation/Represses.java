package org.cidarlab.eugene.fact.relation;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.fact.BinaryFact;


public class Represses 
	extends BinaryFact {

	public Represses(long A, long B) {
		super(A, B);
	}

	public Represses(String A, String B) {
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
			
			sb.append(" REPRESSES ");
			
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
		return Relation.REPRESSES.toString();
	}

}
