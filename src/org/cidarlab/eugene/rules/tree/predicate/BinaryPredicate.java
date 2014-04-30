package org.cidarlab.eugene.rules.tree.predicate;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.exception.EugeneException;


/* 
 * Binary predicates are predicates that MUST have two rule operators (A and B)
 * 
 * In Eugene, the following rules are binary rules:
 * AFTER, BEFORE, NEXTTO, MATCH, ORTHO, WITH, THEN
 */
public abstract class BinaryPredicate 
	implements RulePredicate {
	
	protected long A;
	protected long B;
	protected Component componentA;
	protected Component componentB;
	
	public BinaryPredicate(long A, long B) 
			throws EugeneException {

		this.A = A;
		this.B = B;
		
		if(A != -1) {
			this.componentA = SymbolTables.getComponent(A);
		}

		this.componentB = SymbolTables.getComponent(B);
	}
	
	public long getA() {
		return this.A;
	}
	public long getB() {
		return this.B;
	}
		
	public Component getComponentA() {
		return this.componentA;
	}
	
	public Component getComponentB() {
		return this.componentB;
	}

}
