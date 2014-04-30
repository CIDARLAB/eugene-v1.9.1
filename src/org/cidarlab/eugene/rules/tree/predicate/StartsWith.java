package org.cidarlab.eugene.rules.tree.predicate;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.RuleOperator;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XeqY;
import JaCoP.core.IntVar;
import JaCoP.core.Store;


/*
 * ? STARTSWITH B
 * 
 * ? ... the long[] array that the evaluate() method receives
 * B ... the component that must be at the first position of X
 */
public class StartsWith 
	extends UnaryPredicate {

	public StartsWith(long A, long B) 
			throws EugeneException {
		super(A, B);
	}
	
	public StartsWith(long B) 
			throws EugeneException {
		super(B);
	}
	
	@Override
	public boolean evaluate(long[] l) {
		return l[0]==this.getB();
	}
	
	@Override
	public boolean evaluate() 
			throws EugeneException {
		if(-1 == this.getA()) {
			throw new EugeneException("The STARTSWITH rule requires a left-hand-side rule operand!");
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(this.getA()));
	}

	@Override
	public boolean evaluate(long n) 
			throws EugeneException {
		return this.evaluate(SymbolTables.getDeviceComponentIds(n));
	}

	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return this.evaluate(SymbolTables.getDeviceComponentIds(sDeviceName));
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
//		System.out.println("[StartsWith.evaluate] -> "+device);
		Component componentB = SymbolTables.getComponent(this.getB());
		if(null != componentB && (componentB instanceof Device || componentB instanceof PartType)) {
			List<Component> lst = device.getComponents();
			if(null != lst && !lst.isEmpty()) {
//				System.out.println("[StartsWith.evaluate] -> "+componentB.getName()+" equals "+lst.get(0).getName()+" -> "+componentB.equals(lst.get(0).getName()));
				return componentB.equals(lst.get(0));
			}
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(device.getName()));
	}

	@Override
	public String getOperator() {
		return RuleOperator.STARTSWITH.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.getA() != -1) {
			sb.append(this.getA()).append(" ");
		}
		sb.append(RuleOperator.STARTSWITH).append(" ").append(this.getB());
		return sb.toString();
	}


	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		return new XeqC(variables[0], (int)this.getB());
	}

}
