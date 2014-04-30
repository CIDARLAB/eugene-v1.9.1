package org.cidarlab.eugene.rules.tree.predicate.counting;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.RuleOperator;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.Count;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

/*
 * ? CONTAINS B
 */
		
public class Contains 
		extends CountingPredicate {

	public Contains(long B) 
			throws EugeneException {
		super(-1, B);
	}
	
	public Contains(long A, long B) 
		throws EugeneException {
		super(A, B);
	}
	
	public boolean evaluate(long[] l) {
		return (-1)!=ArrayUtils.indexOf(l, this.getB());
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {

		Component componentB = SymbolTables.getComponent(this.getB());
		if((null != componentB && (componentB instanceof Device || componentB instanceof PartType))) {
			
			int idxB = device.getComponents().indexOf(componentB);

			return idxB != (-1);			
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(device.getName()));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
	
		try {
			if(-1 != this.getA()) {
				sb.append(SymbolTables.getNameById(this.getA())).append(" ");
			}
			sb.append(RuleOperator.CONTAINS).append(" ").append(
					SymbolTables.getNameById(this.getB()));
		} catch (EugeneException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	@Override
	public boolean evaluate() 
			throws EugeneException {
		if(-1 == this.getA()) {
			throw new EugeneException("The CONTAINS rule requires a left-hand-side rule operand!");
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(this.getA()));
	}

	@Override
	public boolean evaluate(long n) 
			throws EugeneException {

		long[] device_components = SymbolTables.getAllDeviceComponentIds(n);
		return this.evaluate(device_components);
	}

	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return this.evaluate(SymbolTables.getDeviceComponentIds(sDeviceName));
	}

	@Override
	public String getOperator() {
		return RuleOperator.CONTAINS.toString();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
//		System.out.println("imposing  CONTAINS "+this.getComponentB().getName()+" "+(int)this.getB()+" -> "+components.size());

		/*
		 * CONTAINS B
		 */

		IntVar counter = new IntVar(store, "CONTAINS_"+this.getB()+"-counter", 1, components.size());
		store.impose(new Count(variables, counter, (int)this.getB()));
		
		return null;

	}
	
	@Override
	public Constraint toJaCoPNot(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		/*
		 * NOT CONTAINS B
		 */

		IntVar counter = new IntVar(store,"NOTCONTAINS_"+this.getB()+"-counter", 0, 0); 
		Constraint c = new Count(variables, counter, (int)this.getB());
		return c;
	}
}
