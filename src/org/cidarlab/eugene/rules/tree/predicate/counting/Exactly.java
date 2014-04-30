package org.cidarlab.eugene.rules.tree.predicate.counting;

import java.util.List;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.RuleOperator;
import org.cidarlab.eugene.rules.tree.predicate.BinaryPredicate;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.Count;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class Exactly 
	extends BinaryPredicate {

	public Exactly(long A, long N) 
			throws EugeneException {				
		super(A, N);

	}

	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {

		// count the number of A's occurrences and compare it to N
		
		return false;
	}

	@Override
	public boolean evaluate(long nId) 
			throws EugeneException {
		return this.evaluate(
				SymbolTables.getDeviceComponentIds(nId));		
	}
	
	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return this.evaluate(
				SymbolTables.getDeviceComponentIds(sDeviceName));
	}
	
	@Override
	public boolean evaluate() 
			throws EugeneException {
		throw new EugeneException(this.toString()+" requires information about a Device!");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getA())
				.append(" ").append(RuleOperator.EXACTLY).append(" ")
			.append(this.getB());
		/**
		try {
			sb.append(SymbolTables.getNameById(this.getA()))
					.append(" ").append(RuleOperator.AFTER).append(" ")
				.append(SymbolTables.getNameById(this.getB()));
		} catch (EugeneException e) {
			e.printStackTrace();
			return null;
		}
		**/
		return sb.toString();
	}
	
	@Override
	public String getOperator() {
		return RuleOperator.EXACTLY.toString();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

//		System.out.println("imposing "+this.componentA.getName()+" EXACTLY "+this.getB());

		// a EXACTLY N
		IntVar count = new IntVar(store, this.getA()+"_EXACTLY_"+this.getB()+"-counter", (int)(this.getB()), (int)(this.getB()));
		store.impose(new Count(variables, count, (int)this.getA()));
		//return new Count(variables, count, (int)this.getA());
		return null;
	}

	@Override
	public boolean evaluate(Device device) 
				throws EugeneException {
//		System.out.println("[MoreThan.evaluate] -> "+device);
		int counter = 0;
		long N = this.getB();
		
		Component componentA = SymbolTables.getComponent(this.getA());
		if(null != componentA) {
			if(componentA instanceof Device) {
				/*
				 * TODO: here we need to evaluate the MORETHAN rule for every depth level of the device
				 */
				List<Component> lst = device.getComponents();
				if(null != lst && !lst.isEmpty()) {
					for(Component component : lst) {
						if(componentA.equals(component)) {
							counter ++;
						}						
	 				}
					if(counter == N) {
						return true;
					}
				}
			} else if(componentA instanceof PartType) {
				List<Component> lst = device.getAllComponents();
				if(null != lst && !lst.isEmpty()) {
					for(Component component : lst) {
						if(componentA.equals(component)) {
							counter ++;
						}						
	 				}
					if(counter == N) {
						return true;
					}
				}
			}
			return false;
		}
		
		return this.evaluate(SymbolTables.getDeviceComponentIds(device.getName()));
	}
}
