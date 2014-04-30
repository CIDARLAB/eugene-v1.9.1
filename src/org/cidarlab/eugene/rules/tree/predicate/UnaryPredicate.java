package org.cidarlab.eugene.rules.tree.predicate;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.exception.EugeneException;


/* 
 * Unary predicates are rule predicates that MUST have at least one rule operand 
 * i.e. the right-hand-side (RHS) of the rule MUST be a given
 * 
 * In Eugene, the following rules are unary rules:
 * CONTAINS, STARTSWITH, ENDSWITH
 */
public abstract class UnaryPredicate 
		extends BinaryPredicate {
	
	public UnaryPredicate(long A, long B) 
			throws EugeneException {
		super(A, B);
	}
	
	public UnaryPredicate(long B) 
			throws EugeneException {
		super(-1, B);
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

	public long getA() {
		return this.A;
	}
	
	/* creates a bit-mask that specifies the positioning of A and B */
//	public boolean[] getBitmask(int N);
}
