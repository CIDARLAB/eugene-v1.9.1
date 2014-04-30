package org.cidarlab.eugene.rules.tree.predicate;

import java.util.Arrays;
import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.tree.predicate.counting.CountingPredicate;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.Not;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XeqC;
import JaCoP.core.IntVar;
import JaCoP.core.Store;


/* 
 * for ( ) 
 */
public class Precedence 
		implements LogicalPredicate {

	private Predicate predicate;
	
	public Precedence(Predicate predicate) {
		this.predicate = predicate;
	}

	public Predicate getPredicate() {
		return this.predicate;
	}
	
	@Override
	public String getOperator() {
		return "(";
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" ( ").append(this.getPredicate()).append(" ) ");
		return sb.toString();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

		if(this.getPredicate() instanceof CountingPredicate) {
			return ((CountingPredicate)this.getPredicate()).toJaCoP(
					store, variables, (Device)device, components);
		} else {
			return this.getPredicate().toJaCoP(
					store, variables, (Device)device, components);
		}
	}

	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {
		return this.getPredicate().evaluate(l);
	}
	
	@Override
	public boolean evaluate(long deviceId) 
			throws EugeneException {
		return this.getPredicate().evaluate(deviceId);
	}
	
	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return this.getPredicate().evaluate(sDeviceName);
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		return this.getPredicate().evaluate(device);
	}

	@Override
	public Constraint toJaCoPNot(
			Store store, IntVar[] variables,
			Device device, List<Component> components) 
					throws EugeneException {
		if(this.predicate instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.predicate).toJaCoPNot(store, variables, device, components);
		} else if(this.predicate instanceof CountingPredicate) {
			return ((CountingPredicate)this.predicate).toJaCoPNot(store, variables, device, components);
		}
		return new Not((PrimitiveConstraint)this.predicate.toJaCoP(store, variables, device, components));
	}

	@Override
	public Constraint toJaCoPAnd(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		if(this.predicate instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.predicate).toJaCoPAnd(store, variables, device, components);
		}
		return this.predicate.toJaCoP(store, variables, device, components);
	}

	@Override
	public Constraint toJaCoPOr(Store store, IntVar[] variables, Device device,
			List<Component> components) throws EugeneException {
		if(this.predicate instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.predicate).toJaCoPOr(store, variables, device, components);
		}
		return this.predicate.toJaCoP(store, variables, device, components);
	}

	@Override
	public Constraint toJaCoPXor(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		if(this.predicate instanceof LogicalPredicate) {
			return ((LogicalPredicate)this.predicate).toJaCoPXor(store, variables, device, components);
		}
		return this.predicate.toJaCoP(store, variables, device, components);
	}

}
