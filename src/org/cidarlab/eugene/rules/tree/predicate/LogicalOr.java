package org.cidarlab.eugene.rules.tree.predicate;

import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.LogicalOperator;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.IfThen;
import JaCoP.constraints.Not;
import JaCoP.constraints.Or;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.Reified;
import JaCoP.constraints.XeqC;
import JaCoP.core.BooleanVar;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class LogicalOr 
	implements LogicalPredicate {
	
	private Predicate A;
	private Predicate B;
	
	public LogicalOr(Predicate A, Predicate B) {
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
		return LogicalOperator.OR.toString();
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getA()).append(" OR ").append(this.getB());
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
//		if(predicateA instanceof LogicalPredicate) {
//			cA = ((LogicalPredicate)predicateA).toJaCoP(store, variables, device, components);
//		} else if (predicateA instanceof CountingPredicate) {
//			cA = ((CountingPredicate)predicateA).toJaCoPNot(store, variables, device, components);
//		} else {
			cA = predicateA.toJaCoP(store, variables, device, components);
//		}
	
		Constraint cB = null;
//		if(predicateB instanceof LogicalPredicate) {
//			cB = ((LogicalPredicate)predicateB).toJaCoPOr(store, variables, device, components);
//		} else if (predicateB instanceof CountingPredicate) {
//			cB = ((CountingPredicate)predicateB).toJaCoP(store, variables, device, components);
//		} else {
			cB = predicateB.toJaCoP(store, variables, device, components);
//		}
	
		if(cA instanceof PrimitiveConstraint && cB instanceof PrimitiveConstraint) {
//			System.out.println("XXXX -> "+cA+" OR "+cB);
			return new Or((PrimitiveConstraint)cA, (PrimitiveConstraint)cB);
//			
//			BooleanVar bVar = new BooleanVar(store);
//			store.impose(new Reified((PrimitiveConstraint)cA, bVar));
//			store.impose(new Reified((PrimitiveConstraint)cB, bVar));
			
		} else if(cA instanceof PrimitiveConstraint && !(cB instanceof PrimitiveConstraint)) {
			
			/*
			 * only if cA is false, cB should be evaluated
			 * -> using Reified constraints
			 */

			/*
			 * IF NOT a THEN b
			 */
			IntVar bVar = new BooleanVar(store);
			store.impose(new Reified(new Not((PrimitiveConstraint)cA), bVar));									
			store.impose(cB);


		} else if(!(cA instanceof PrimitiveConstraint) && cB instanceof PrimitiveConstraint) {
			IntVar bVar = new BooleanVar(store);
			store.impose(new Reified(new Not((PrimitiveConstraint)cB), bVar));									
			store.impose(cA);
		} else {
			
			/*
			 * here we need an Reified constraint
			 */
			
			if(null != cA) {		
				store.impose(cA);
			}
			
			if(null != cB) {
				store.impose(cB);
			}
		}

		return cB;
	}

	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {
		boolean b = this.getA().evaluate(l);
//		System.out.println("[LogicalOr.evaluate] -> "+Arrays.toString(l)+" -> "+b);
		if(!b) {
			b = b || this.getB().evaluate(l);
		}	
		return b;
	}
	
	@Override
	public boolean evaluate(long deviceId) 
			throws EugeneException {
		boolean b = this.getA().evaluate(deviceId);

		if(!b) {
			b = b || this.getB().evaluate(deviceId);
		}
		return b;
	}
	
	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		boolean b = this.getA().evaluate(sDeviceName);

		if(!b) {
			b = b || this.getB().evaluate(sDeviceName);
		}
		
		return b;
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		boolean b = this.getA().evaluate(device);

		if(!b) {
			b = b || this.getB().evaluate(device);
		}
		
		return b;
	}

	@Override
	public Constraint toJaCoPNot(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constraint toJaCoPAnd(
			Store store, IntVar[] variables,
			Device device, List<Component> components) 
					throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constraint toJaCoPOr(Store store, IntVar[] variables, Device device,
			List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constraint toJaCoPXor(Store store, IntVar[] variables,
			Device device, List<Component> components) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

}
