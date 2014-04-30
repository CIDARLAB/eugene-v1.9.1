package org.cidarlab.eugene.rules.tree.predicate.pairing;

import org.antlr.runtime.tree.CommonTree;
import org.cidarlab.eugene.rules.tree.predicate.RulePredicate;

/*
 * for rules like:
 * REPRESSES, INDUCES, BINDS, ORTHO, DRIVES
 */
public abstract class PairingPredicate 
	implements RulePredicate {

	protected CommonTree tree;
	
	public PairingPredicate(CommonTree tree) {
		this.tree = tree;
	}

	
}
