package org.cidarlab.eugene.rules.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.LogicalOperator;
import org.cidarlab.eugene.rules.PredicateBuilder;
import org.cidarlab.eugene.rules.tree.predicate.ExpressionPredicate;
import org.cidarlab.eugene.rules.tree.predicate.LogicalNot;
import org.cidarlab.eugene.rules.tree.predicate.Precedence;
import org.cidarlab.eugene.rules.tree.predicate.Predicate;
import org.cidarlab.eugene.rules.tree.predicate.RuleID;


public class RuleTreeParser {

	private static final Set<String> setLogicalBooleanOperators = new HashSet<String>(
			Arrays.asList(new String[] { "AND", "OR", "XOR" }));

	/*
	 * Counting Rules ...
	 * to constraint the number of occurences ...
	 */
	private static final Set<String> setCountingRules = new HashSet<String>(
			Arrays.asList(new String[] { 
					 "CONTAINS", "NOTCONTAINS", "NOTMORETHAN", "MORETHAN", "EXACTLY"}));
	
	/*
	 * Relational Rules ... 
	 * for regulatory interactions
	 */
	private static final Set<String> setRelationalRules = new HashSet<String>(
			Arrays.asList(new String[] { 
					"REPRESSES", "INDUCES", "DRIVES", "BINDS", "MATCHES"}));

	/*
	 * Pairing Rules ...
	 * to constraint pairs of domain values
	 */
	private static final Set<String> setPairingRules = new HashSet<String>(
			Arrays.asList(new String[] { 
					"WITH", "NOTWITH", "THEN", "NOTTHEN"}));
	
	/*
	 * Positional Rules
	 */
	private static final Set<String> setPositionalRules = new HashSet<String>(
			Arrays.asList(new String[] { 
					"BEFORE", "ALL_BEFORE", "SOME_BEFORE", 
					"AFTER", "ALL_AFTER", "SOME_AFTER",
					"NEXTTO", "ALL_NEXTTO", "SOME_NEXTTO",
					"LEFTTO", "ALL_LEFTTO", "SOME_LEFTTO",
					"RIGHTTO", "ALL_RIGHTTO", "SOME_RIGHTTO",
					"STARTSWITH", "ENDSWITH", }));

	/*
	 * Expressional Rules
	 * for mathematical expressions ... e.g. X + Y < 5
	 */
	private static final Set<String> setExpressionalRules = new HashSet<String>(
			Arrays.asList(new String[] { "NOTEQUALS", "!=", "EQUALS", "==", "<", "<=", ">=", ">" })); 

	private static final Set<String> setExpressionOperator = new HashSet<String>(
			Arrays.asList(new String[] { "+", "-", "*", "/" }));

	
	public static boolean evaluate(Predicate predicate) {
		return true;
	}
	
	public static Predicate buildTree(CommonTree t) 
			throws EugeneException {
		
		if (null != t) {
			if ("NOT".equals(t.getText()) || "!".equals(t.getText())) {
				List<Predicate> lstChildren = new ArrayList<Predicate>();
				
				Predicate childPredicate= buildTree((CommonTree) t.getChild(0));
				lstChildren.add(childPredicate);

				return (LogicalNot)PredicateBuilder.build(
						LogicalOperator.NOT, 
						childPredicate);
				
			} else if ("(".equals(t.getText())) {
				
				return new Precedence(
						buildTree((CommonTree) t.getChild(0)));
				
				/*
				 * AND, OR, XOR
				 */
			} else if (isLogicalBooleanOperator(t.getText())) {
				
				return PredicateBuilder.buildLogicalPredicate(
						(Predicate)buildTree((CommonTree) t.getChild(0)),
						t.getText(), 
						(Predicate)buildTree((CommonTree) t.getChild(1)));
				
				/*
				 * EXPRESSIONAL RULES
				 */
			} else if(isExpressionalRule(t.getText()) ||
					isExpressionOperator(t.getText())) {
				/* e.g. X + Y > 15 */
				
				Predicate predicate = new ExpressionPredicate(t);
				return predicate;

				/*
				 * COUNTING RULES
				 * e.g. CONTAINS, MORETHAN, EXACTLY ...
				 */
			} else if(isCountingRule(t.getText())) {

				if(t.getChildCount() == 1) {
					return PredicateBuilder.buildCountingPredicate(
							getOperandId((CommonTree)t.getChild(0)),
							t.getText(),
							-1);
				} else if (t.getChildCount() == 2) {
					return PredicateBuilder.buildCountingPredicate(
						getOperandId((CommonTree)t.getChild(0)),
						t.getText(),
						Integer.valueOf(t.getChild(1).getText()));
				}

				throw new EugeneException(
						"Invalid "+t.getText()+" rule!");

				/*
				 * RELATIONAL RULES
				 * e.g. REPRESSES, INDUCES, DRIVES, MATCHES
				 */
			} else if(isRelationalRule(t.getText())) {
				
				return PredicateBuilder.buildRelationalPredicate((CommonTree)t);

				/*
				 * PAIRING RULES
				 * e.g. WITH, THEN ...
				 */
			} else if(isPairingRule(t.getText())) {

				if(t.getChildCount() != 2) {
					throw new EugeneException(
							"Invalid "+t.getText()+" rule! Two operands required!");
				}

				return PredicateBuilder.buildPairingPredicate(
						getOperandId((CommonTree)t.getChild(0)),
						t.getText(),
						getOperandId((CommonTree)t.getChild(1)));

				/*
				 * POSITIONAL RULES
				 * e.g. BEFORE, NEXTTO, STARTSWITH ...
				 */
			} else if(isPositionalRule(t.getText())) {

				if(t.getChildCount() == 2) {
					// binary positioning rule
					return PredicateBuilder.buildPositionalPredicate(
						getOperandId((CommonTree)t.getChild(0)),
						t.getText(),
						getOperandId((CommonTree)t.getChild(1)));
				} else if(t.getChildCount() == 1) {
					// unary positioning rule
					return PredicateBuilder.buildPositionalPredicate(
							-1,
							t.getText(),
							getOperandId((CommonTree)t.getChild(0)));					
				} else {
					throw new EugeneException(
							"Invalid "+t.getText()+" rule!");
				}

				/*
				 * Unknown Rule Operator
				 * i.e. we assume the Rule Operator is a rule itself ...				
				 */
			} else {
				NamedElement objElement = SymbolTables.get(t.getText());
				
				if(null != objElement && objElement instanceof Rule) {
					return new RuleID((Rule)objElement);
				} else {
					throw new EugeneException(
						"Invalid rule operand! " + t.getText());
				}
			}
		}
		
		return null;
	}
	

	private static long getOperandId(CommonTree t) 
			throws EugeneException {

		return SymbolTables.getId(t.getText());
	}
	
	/*
	 * methods to check to which set the given operator belongs to
	 */
	public static boolean isLogicalBooleanOperator(String s) {
		return setLogicalBooleanOperators.contains(s);
	}

	public static boolean isPositionalRule(String s) {
		return setPositionalRules.contains(s);
	}

	public static boolean isRelationalRule(String s) {
		return setRelationalRules.contains(s);
	}	

	public static boolean isPairingRule(String s) {
		return setPairingRules.contains(s);
	}	

	public static boolean isCountingRule(String s) {
		return setCountingRules.contains(s);
	}

	public static boolean isExpressionalRule(String s) {
		return setExpressionalRules.contains(s);
	}

	public static boolean isExpressionOperator(String s) {
		return setExpressionOperator.contains(s);
	}
}
