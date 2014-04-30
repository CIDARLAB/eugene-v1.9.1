package org.cidarlab.eugene.rules.tree.predicate;

import java.util.BitSet;
import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.LogicalOperator;
import org.cidarlab.eugene.rules.tree.predicate.counting.CountingPredicate;


import JaCoP.constraints.Constraint;
import JaCoP.constraints.Not;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XneqC;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class LogicalNot 
	implements LogicalPredicate {

	private Predicate predicate;
	
	public LogicalNot(Predicate predicate) {
		this.predicate = predicate;
	}
	
	public Predicate getPredicate() {
		return this.predicate;
	}
	
	@Override
	public String getOperator() {
		return LogicalOperator.NOT.toString();
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NOT ").append(this.getPredicate()).append("");
		return sb.toString();
	}
	
	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

		if(this.getPredicate() instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		} else if(this.getPredicate() instanceof CountingPredicate) {
			return ((CountingPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		} else {
			Constraint c = this.getPredicate().toJaCoP(store, variables, device, components);

			if(null != c && c instanceof PrimitiveConstraint) {
				return new JaCoP.constraints.Not(
						(PrimitiveConstraint)c);
			}
		}
		
		return null;
	}

	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {
		boolean b = this.getPredicate().evaluate(l);
//		if(!b) {
//			System.err.println("[Not] everything's ok again...");
//		} else {
//			System.err.println("[Not] everything's broken...");
//		}
		return !b;
	}
	
	@Override
	public boolean evaluate(long deviceId) 
			throws EugeneException {
		return !this.getPredicate().evaluate(deviceId);
	}

	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return !this.getPredicate().evaluate(sDeviceName);
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		return !this.getPredicate().evaluate(device);
	}

	@Override
	public Constraint toJaCoPNot(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

		if(this.getPredicate() instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		} else if(this.getPredicate() instanceof CountingPredicate) {
			return ((CountingPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		}
		return new Not((PrimitiveConstraint)this.getPredicate().toJaCoP(store, variables, device, components));
	}

	@Override
	public Constraint toJaCoPAnd(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		if(this.getPredicate() instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		} else if(this.getPredicate() instanceof CountingPredicate) {
			return ((CountingPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		}
		return new Not((PrimitiveConstraint)this.getPredicate().toJaCoP(store, variables, device, components));
	}

	@Override
	public Constraint toJaCoPOr(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		if(this.getPredicate() instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		} else if(this.getPredicate() instanceof CountingPredicate) {
			return ((CountingPredicate)this.getPredicate()).toJaCoP(store, variables, device, components);
		}
		return new Not((PrimitiveConstraint)this.getPredicate().toJaCoP(store, variables, device, components));
	}

	@Override
	public Constraint toJaCoPXor(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		if(this.getPredicate() instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		} else if(this.getPredicate() instanceof CountingPredicate) {
			return ((CountingPredicate)this.getPredicate()).toJaCoPNot(store, variables, device, components);
		}
		return new Not((PrimitiveConstraint)this.getPredicate().toJaCoP(store, variables, device, components));
	}
}
