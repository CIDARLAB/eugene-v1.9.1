package org.cidarlab.eugene.rules;

import org.antlr.runtime.tree.CommonTree;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.tree.predicate.*;
import org.cidarlab.eugene.rules.tree.predicate.counting.Contains;
import org.cidarlab.eugene.rules.tree.predicate.counting.Exactly;
import org.cidarlab.eugene.rules.tree.predicate.counting.MoreThan;
import org.cidarlab.eugene.rules.tree.predicate.pairing.Then;
import org.cidarlab.eugene.rules.tree.predicate.pairing.With;
import org.cidarlab.eugene.rules.tree.predicate.positional.after.AllAfter;
import org.cidarlab.eugene.rules.tree.predicate.positional.after.SomeAfter;
import org.cidarlab.eugene.rules.tree.predicate.positional.before.*;
import org.cidarlab.eugene.rules.tree.predicate.positional.nextto.AllNextTo;
import org.cidarlab.eugene.rules.tree.predicate.positional.nextto.SomeNextTo;
//import org.cidarlab.eugene.rules.tree.predicate.positional.before.Before;
import org.cidarlab.eugene.rules.tree.predicate.relations.Induces;
import org.cidarlab.eugene.rules.tree.predicate.relations.Represses;


public class PredicateBuilder {

	/** LOGICAL OPERATORS **/
	
	/** NOT **/
	public static Predicate build(LogicalOperator op, Predicate predicate) 
			throws EugeneException {
		if(null != op && null != predicate && LogicalOperator.NOT.equals(op)) {
			return new LogicalNot(predicate);
		}
		throw new EugeneException("I cannot build a NOT predicate with the given information!");

	}
	
	/** AND, OR, XOR **/
	public static Predicate buildLogicalPredicate(Predicate A, String op, Predicate B) 
			throws EugeneException {
		if(null != op && null != A && null != B) {
			if(LogicalOperator.AND.toString().equals(op)) {
				return new LogicalAnd(A, B);
			} else if(LogicalOperator.OR.toString().equals(op)) {
				return new LogicalOr(A, B);
			} else if(LogicalOperator.XOR.toString().equals(op)) {
				return new LogicalXor(A, B);
			}
		}
		throw new EugeneException("I cannot build a predicate with the given information!");
	}
	
	
	/*
	 * COUNTING RULES
	 */
	public static Predicate buildCountingPredicate(long A, String op, int N) 
			throws EugeneException {
		if(RuleOperator.CONTAINS.toString().equals(op)) {
			return new Contains(A);
		} else if(RuleOperator.NOTCONTAINS.toString().equals(op)) {
			return new LogicalNot(new Contains(A));
		} else if(RuleOperator.MORETHAN.toString().equals(op)) {
			return new MoreThan(A, (long)N);
		} else if(RuleOperator.NOTMORETHAN.toString().equals(op)) {
			return new LogicalNot(new MoreThan(A, (long)N));
		} else if(RuleOperator.NOTEXACTLY.toString().equals(op)) {
			return new LogicalNot(new Exactly(A, (long)N));
		} else if(RuleOperator.EXACTLY.toString().equals(op)) {
			return new Exactly(A, (long)N);
		}
		throw new EugeneException(op+" is an invalid rule operator!");
	}
	
	/*
	 * POSITIONAL RULES
	 */
	public static Predicate buildPositionalPredicate(long A, String op, long B) 
			throws EugeneException {
		
		/*
		 * BEFORE rules
		 */
		if(RuleOperator.ALL_BEFORE.toString().equals(op) ||
				RuleOperator.BEFORE.toString().equals(op)) {
			return new AllBefore(A, B);
		} else if(RuleOperator.SOME_BEFORE.toString().equals(op)) {
			return new SomeBefore(A, B);
			
		/*
		 * AFTER rules
		 */
		} else if(RuleOperator.ALL_AFTER.toString().equals(op) ||
					RuleOperator.AFTER.toString().equals(op)) {
				return new AllAfter(A, B);				
		} else if(RuleOperator.SOME_AFTER.toString().equals(op)) {
				return new SomeAfter(A, B);
				
		/*
		 * NEXTTO rules
		 */
		} else if(RuleOperator.NEXTTO.toString().equals(op) ||
				RuleOperator.ALL_NEXTTO.toString().equals(op)) {
			return new AllNextTo(A,B);
		} else if(RuleOperator.SOME_NEXTTO.toString().equals(op) ||
				RuleOperator.ALL_NEXTTO.toString().equals(op)) {
			return new SomeNextTo(A,B);
		
		/*
		 * STARTS/ENDS WITH
		 */
		} else if(RuleOperator.STARTSWITH.toString().equals(op)) {
			return new StartsWith(B);
		} else if(RuleOperator.ENDSWITH.toString().equals(op)) {
			return new EndsWith(B);
		}
		
		throw new EugeneException(op+" is an invalid rule operator!");
	}

	/*
	 * PAIRING RULES
	 */
	public static Predicate buildPairingPredicate(long A, String op, long B) 
			throws EugeneException {
		if(RuleOperator.WITH.toString().equals(op)) {
			return new With(A, B);
		} else if(RuleOperator.NOTWITH.toString().equals(op)) {
			return new LogicalNot(new With(A, B));
		} else if(RuleOperator.THEN.toString().equals(op)) {
			return new Then(A, B);
		} else if(RuleOperator.NOTTHEN.toString().equals(op)) {
			return new LogicalNot(new Then(A, B));
		} 
		
		throw new EugeneException(op+" is an invalid pairing rule operator!");
	}
	
	/*
	 * RELATIONAL RULES
	 */
	public static Predicate buildRelationalPredicate(CommonTree t) 
			throws EugeneException {
		
		String sOperator = t.getText();
		if(RuleOperator.REPRESSES.toString().equals(sOperator)) {
			return new Represses(t);
		} else if(RuleOperator.MATCHES.toString().equals(sOperator)) {
			return new Matches(t);
		} else if(RuleOperator.REPRESSES.toString().equals(sOperator)) {
			throw new UnsupportedOperationException(sOperator+" IS NOT YET SUPPORTED!");
		} else if(RuleOperator.DRIVES.toString().equals(sOperator)) {
			throw new UnsupportedOperationException(sOperator+" IS NOT YET SUPPORTED!");
		} else if(RuleOperator.ORTHO.toString().equals(sOperator)) {
			throw new UnsupportedOperationException(sOperator+" IS NOT YET SUPPORTED!");
		} else if(RuleOperator.INDUCES.toString().equals(sOperator)) {
			return new Induces(t);
		}
		
		throw new EugeneException(sOperator+" is an invalid rule operator!");
	}
	
}
