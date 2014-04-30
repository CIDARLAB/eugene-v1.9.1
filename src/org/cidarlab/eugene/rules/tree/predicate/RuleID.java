package org.cidarlab.eugene.rules.tree.predicate;

import java.util.Arrays;
import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.tree.predicate.counting.CountingPredicate;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.Not;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class RuleID 
	implements LogicalPredicate {

	private Rule rule;
	public RuleID(Rule rule) 
			throws EugeneException {
		this.rule = rule;
	}
	
	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {
//		System.out.println("[RuleID.evaluate] "+this.rule.getPredicate()+" ON "+Arrays.toString(l));
		return this.rule.getPredicate().evaluate(l);
	}

	@Override
	public boolean evaluate(long n) 
			throws EugeneException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		return this.rule.getPredicate().evaluate(device);
	}

	@Override
	public String getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.rule.toString());
		return sb.toString();
	}
	
	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		return this.rule.getPredicate().toJaCoP(store, variables, device, components);
	}

	public Constraint toJaCoPNot(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		
//		System.out.println("[RuleID.toJaCoPNot]");
		if(this.rule.getPredicate() instanceof CountingPredicate) {
			return ((CountingPredicate)this.rule.getPredicate()).toJaCoPNot(store, variables, device, components);
		} else {
			Constraint c = this.rule.getPredicate().toJaCoP(store, variables, device, components);
			if(c instanceof PrimitiveConstraint) {
				return new Not((PrimitiveConstraint)c);
			} else {
				store.impose(c);
			}
		}
		
		return null;
	}

	@Override
	public Constraint toJaCoPAnd(
			Store store, IntVar[] variables,
			Device device, List<Component> components) 
					throws EugeneException {
		return this.rule.getPredicate().toJaCoP(store, variables, device, components);
	}

	@Override
	public Constraint toJaCoPOr(Store store, IntVar[] variables, Device device,
			List<Component> components) throws EugeneException {
		return this.rule.getPredicate().toJaCoP(store, variables, device, components);
	}

	@Override
	public Constraint toJaCoPXor(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		return this.rule.getPredicate().toJaCoP(store, variables, device, components);
	}
}
