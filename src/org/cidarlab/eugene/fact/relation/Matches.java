package org.cidarlab.eugene.fact.relation;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.fact.BinaryFact;


public class Matches 
	extends BinaryFact {

	public Matches(long A, long B) {
		super(A, B);
	}

	public Matches(String A, String B) {
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
			
			sb.append(" MATCHES ");
			
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
		return Relation.MATCHES.toString();
	}

}
