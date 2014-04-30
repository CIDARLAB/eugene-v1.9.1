package org.cidarlab.eugene.rules.tree.predicate;

import java.util.Arrays;
import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.LogicalOperator;
import org.cidarlab.eugene.rules.tree.predicate.counting.CountingPredicate;


import JaCoP.constraints.And;
import JaCoP.constraints.Constraint;
import JaCoP.constraints.Not;
import JaCoP.constraints.Or;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.Reified;
import JaCoP.constraints.XeqC;
import JaCoP.core.BooleanVar;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class LogicalAnd 
	implements LogicalPredicate {
	
	private Predicate A;
	private Predicate B;
	
	public LogicalAnd(Predicate A, Predicate B) {
		this.A = A;
		this.B = B;
	}
	
	public Predicate getA() {
		return this.A;
	}

	public Predicate getB() {
		return this.B;
	}
	
	@Override
	public String getOperator() {
		return LogicalOperator.AND.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getA()).append(" AND ").append(this.getB());
		return sb.toString();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		
		Predicate predicateA = this.getA();
		Predicate predicateB = this.getB();
		
		Constraint cA = null;
		if(predicateA instanceof LogicalPredicate) {
			cA = ((LogicalPredicate)predicateA).toJaCoPAnd(store, variables, device, components);
		} else if(predicateA instanceof CountingPredicate) {
			cA = ((CountingPredicate)predicateA).toJaCoP(store, variables, device, components);
		} else {
			cA = predicateA.toJaCoP(store, variables, device, components);
		}
	
		Constraint cB = null;
		if(predicateB instanceof LogicalPredicate) {
			cB = ((LogicalPredicate)predicateB).toJaCoPAnd(store, variables, device, components);
		} else if(predicateB instanceof CountingPredicate) {
			cB = ((CountingPredicate)predicateB).toJaCoP(store, variables, device, components);
		} else {
			cB = predicateB.toJaCoP(store, variables, device, components);
		}

//		System.out.println("cA -> "+cA.toString());
//		System.out.println("cB-> "+cB.toString());
		if(cA instanceof PrimitiveConstraint && cB instanceof PrimitiveConstraint) {
			return new And((PrimitiveConstraint)cA, (PrimitiveConstraint)cB);
		} 

		if(null != cA) {
			store.impose(cA);
		}
		
		if(null != cB) {
			store.impose(cB);
		}
		
		return null;
	}

	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {
		boolean b = this.getA().evaluate(l);
//		System.out.println("[LogicalAnd.evaluate] -> "+Arrays.toString(l)+" -> "+b);
		if(b) {
			b = b & this.getB().evaluate(l);
		}
		return b;
	}

	@Override
	public boolean evaluate(long deviceId) 
			throws EugeneException {
		boolean b = this.getA().evaluate(deviceId);

		if(b) {
			b = b && this.getB().evaluate(deviceId);
		}
		
		return b;
	}

	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		boolean b = this.getA().evaluate(sDeviceName);

		if(b) {
			b = b && this.getB().evaluate(sDeviceName);
		}
		
		return b;
	}
	
	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		boolean b = this.getA().evaluate(device);

		if(b) {
			b = b && this.getB().evaluate(device);
		}
		
		return b;
	}

	@Override
	public Constraint toJaCoPNot(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		
		Predicate predicateA = this.getA();
		Predicate predicateB = this.getB();
		
		Constraint cA = null;
		if(predicateA instanceof LogicalPredicate) {
			cA = ((LogicalPredicate)predicateA).toJaCoPAnd(store, variables, device, components);
		} else if(predicateA instanceof CountingPredicate) {
			cA = ((CountingPredicate)predicateA).toJaCoPNot(store, variables, device, components);
		} else {
			cA = predicateA.toJaCoP(store, variables, device, components);
		}
	
		Constraint cB = null;
		if(predicateB instanceof LogicalPredicate) {
			cB = ((LogicalPredicate)predicateB).toJaCoPAnd(store, variables, device, components);
		} else if(predicateB instanceof CountingPredicate) {
			cB = ((CountingPredicate)predicateB).toJaCoPNot(store, variables, device, components);
		} else {
			cB = new Not((PrimitiveConstraint)predicateB.toJaCoP(store, variables, device, components));
		}

		if(cA instanceof PrimitiveConstraint && cB instanceof PrimitiveConstraint) {
			return new And((PrimitiveConstraint)cA, (PrimitiveConstraint)cB);
//			BooleanVar bVar = new BooleanVar(store);
//			store.impose(new Reified((PrimitiveConstraint)cA, bVar));
//			store.impose(new Reified((PrimitiveConstraint)cB, bVar));
		} else {
			if(cA != null) {
				store.impose(cA);
			}
			if(cB != null) {
				store.impose(cB);
			}
		}
		
		return null;
	}

	@Override
	public Constraint toJaCoPAnd(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

		return this.toJaCoP(store, variables, device, components);
	}

	@Override
	public Constraint toJaCoPOr(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		return this.toJaCoP(store, variables, device, components);		
	}

	@Override
	public Constraint toJaCoPXor(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		Predicate predicateA = this.getA();
		Predicate predicateB = this.getB();

		return null;
	}

}
