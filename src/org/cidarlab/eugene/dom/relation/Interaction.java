package org.cidarlab.eugene.dom.relation;

import org.cidarlab.eugene.fact.relation.Relation;

public class Interaction {
	private Participant lhs;
	private Relation relation;
	private Participant rhs;
	
	public Interaction(Participant lhs, Relation relation, Participant rhs) {
		this.lhs = lhs;
		this.relation = relation;
		this.rhs = rhs;
	}
	
	public Participant getLhs() {
		return this.lhs;
	}
	
	public Relation getRelation() {
		return this.relation;
	}
	
	public Participant getRhs() {
		return this.rhs;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getLhs()).append(" ").append(this.getRelation()).append(" ").append(this.getRhs());
		return sb.toString();
	}
}
