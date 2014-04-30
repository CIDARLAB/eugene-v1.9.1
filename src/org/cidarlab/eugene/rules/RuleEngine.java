package org.cidarlab.eugene.rules;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.tree.predicate.LogicalAnd;
import org.cidarlab.eugene.rules.tree.predicate.BinaryPredicate;
import org.cidarlab.eugene.rules.tree.predicate.LogicalPredicate;
import org.cidarlab.eugene.rules.tree.predicate.LogicalNot;
import org.cidarlab.eugene.rules.tree.predicate.LogicalOr;
import org.cidarlab.eugene.rules.tree.predicate.Precedence;
import org.cidarlab.eugene.rules.tree.predicate.Predicate;
import org.cidarlab.eugene.rules.tree.predicate.RulePredicate;
import org.cidarlab.eugene.rules.tree.predicate.LogicalXor;


public class RuleEngine {

	public static boolean evaluatePermute(Rule objRule, long[] lst) 
			throws EugeneException {
		Predicate predicate = objRule.getPredicate();
		if(predicate instanceof RulePredicate) {
			return ((RulePredicate)predicate).evaluate(lst);
		}
		
		return false;
	}
	
	public static boolean evaluateProduct(List<Rule> rules, long[] elements, int N) 
		throws EugeneException {
		
		if(null != rules && !rules.isEmpty()) {
			if(elements.length == 0) {
				
				// just evaluate STARTSWITH 
				System.out.println("[RuleEngine.evaluateProduct] evaluating STARTSWITH rules on "+Arrays.toString(elements));
				
			} else if(elements.length == N) {
				
				// evaluate all rules...
				System.out.println("[RuleEngine.evaluateProduct] evaluating all rules on "+Arrays.toString(elements));
				
			}
		}
		return true;
	}
	
	public static boolean evaluateIfRule(Rule objRule) 
			throws EugeneException {
		
//		System.out.println("[RuleEngine.evaluateIfRule] -> "+objRule);
		
		if(null != objRule) {
			if(null != objRule.getDevice()) {
				/* 
				 * this should be improved... 
				 * we need to set the device of the rule appropriately whenever the rule is being defined
				 */
				return objRule.getPredicate().evaluate((Device)SymbolTables.get(objRule.getDevice().getName()));
			} else if((-1) != objRule.getDeviceId()) {
				return objRule.getPredicate().evaluate(objRule.getDeviceId());
//						SymbolTables.getAllDeviceComponentIds(objRule.getDeviceId()));
			} else {
				/* here, we have to retrieve all devices */
				Collection<Device> devices = SymbolTables.getDevices();

				boolean b = true;
				if(null != devices) {
					Iterator<Device> it = devices.iterator();
					while(it.hasNext() && b) {
						b = b & evaluate(
							objRule.getPredicate(), 
							SymbolTables.getAllDeviceComponentIds(objRule.getDeviceId()));
					}
				}
				return b;
			}
		}

		return true;
	}

	public static boolean evaluate(List<Rule> lstRules, long[] elements) 
			throws EugeneException {
		
		if(null != lstRules && !lstRules.isEmpty()) {
			boolean b = true;
			
			for(int i=0; i<lstRules.size() && b; i++) {				
				b = b & evaluate(lstRules.get(i).getPredicate(), elements);
			}
			
			return b;
		}
		return true;
		
	}
	
	public static boolean evaluate(Predicate predicate, long[] elements) 
			throws EugeneException {
		
		if(null != predicate) {
		
			//System.out.println("[RuleEngine.evaluate] predicate: "+predicate);
			
			if(predicate instanceof Precedence) {
				
				return evaluate(((Precedence)predicate).getPredicate(), elements);
				
			} else if(predicate instanceof LogicalPredicate) {
				
				/** Traverse logical predicates **/
				return evaluateLogicalPredicate(
						(LogicalPredicate)predicate, elements);
				
			} else if(predicate instanceof RulePredicate) {

				return ((RulePredicate)predicate).evaluate(elements);
				
			}
		}
		
		return true;
	}
	
	private static boolean evaluateLogicalPredicate(LogicalPredicate predicate, long[] elements) 
			throws EugeneException {
		if(null != predicate) {
			/* here we need to perform appropriate actions */
			if(predicate instanceof LogicalNot) {
				return !evaluate(((LogicalNot)predicate).getPredicate(), elements);
			} else if(predicate instanceof LogicalAnd) {
//				System.out.println(((And)predicate).getA()+ " AND "+((And)predicate).getB());
				return evaluate(((LogicalAnd)predicate).getA(), elements) && evaluate(((LogicalAnd)predicate).getB(), elements);
			} else if(predicate instanceof LogicalOr) {
				boolean b = evaluate(((LogicalOr)predicate).getA(), elements);
				if(!b) {
					return b || evaluate(((LogicalOr)predicate).getB(), elements);
				}
				return b;
			} else if(predicate instanceof LogicalXor) {
				return evaluate(((LogicalXor)predicate).getA(), elements) ^ evaluate(((LogicalXor)predicate).getB(), elements);				
			} else if(predicate instanceof RulePredicate) {
				return evaluate((RulePredicate)predicate, elements);
			}
		}
		return true;
	}
	
	
	public static boolean evaluateDevices(List<Device> lstDevices) 
			throws EugeneException {
		return false;		
	}

	public static boolean evaluateRules(List<Rule> lstRules) 
			throws EugeneException {
		return false;		
	}
	
	public static RuleMemo evaluateRulesOnDevices(List<Rule> lstRules, long[] deviceIds) 
			throws EugeneException {
		
		RuleMemo memo = new RuleMemo();
		
		if (!lstRules.isEmpty()) {			
			for (Rule rule : (List<Rule>)lstRules) {				
				if((-1) == rule.getDeviceId()) {
					
					/* iterate over the list of devices */
					for(long deviceId : deviceIds) {
						
						rule.setDeviceId(deviceId);
						
						// evaluate it
						if(!evaluate(rule, deviceId)) {
							memo.addViolation(
									"The device "+SymbolTables.getNameById(deviceId)+" violates the "+rule.toString()+" rule!");
						}
						
					}
					
					rule.setDeviceId(-1);
					
				} else {
					if(!org.cidarlab.eugene.rules.RuleEngine.evaluate(rule)) {
						memo.addViolation("Violation of Rule "+rule.toString()+"!");
					}
				}
			}
		}
		return memo;
	}
	
	public static boolean evaluate(Rule objRule, long nDeviceId) 
			throws EugeneException {		
		if(nDeviceId == objRule.getDeviceId()) {
			return ((RulePredicate)objRule.getPredicate()).evaluate(nDeviceId);
		}
		return true;
	}

	public static boolean evaluate(Rule objRule, Device objDevice) 
			throws EugeneException {
		return false;
	}
	
	public static boolean evaluate(long nDeviceId) 
			throws EugeneException {
		return false;
	}
	
	public static boolean evaluate(Device objDevice) 
			throws EugeneException {		
		return evaluate(SymbolTables.getId(objDevice.getName()));
	}
	
	public static boolean evaluate(Rule objRule) 
			throws EugeneException {
		
		if((-1) != objRule.getDeviceId()) {			
			return evaluate(objRule, objRule.getDeviceId());			
		} else {
			/* evaluate the given rule on all devices */
			
			
		}
		return false;
	}
}
