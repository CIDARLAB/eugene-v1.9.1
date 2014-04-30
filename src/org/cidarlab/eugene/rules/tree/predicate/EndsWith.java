package org.cidarlab.eugene.rules.tree.predicate;

import java.util.Arrays;
import java.util.List;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.RuleOperator;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.XeqC;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

/*
 * ? ENDSWITH B
 * 
 * ? ... the long[] array that the evaluate() method receives
 * B ... the component that must be at the last position of X
 */
public class EndsWith 
	extends UnaryPredicate {

	public EndsWith(long B) 
			throws EugeneException {
		super(B);
	}

	public EndsWith(long A, long B) 
			throws EugeneException {
		super(A, B);
	}
	
	@Override
	public boolean evaluate(long[] l) {
		System.out.println("[Endswith.evaluate] -> "+Arrays.toString(l)+": ENDSWITH "+this.getB());
		boolean b = l[l.length-1] == this.getB();
		
		if(!b) {
			try {
				/* check if B is a Part type */
				Component componentB = SymbolTables.getComponent(this.getB());
				Component lastComponent = SymbolTables.getComponent(l[l.length-1]);
//				System.out.println("[Endswith.evaluate] -> "+componentB.getClass()+" vs "+lastComponent.getClass());
				if(lastComponent instanceof Part && componentB instanceof PartType) {
					b = ((Part)lastComponent).getPartType().equals((PartType)componentB);
				} else if(componentB instanceof Device && lastComponent instanceof Part) {
//					System.out.println("[Endswith.evaluate] -> "+componentB+" vs "+lastComponent);
				} else if(componentB instanceof Device && lastComponent instanceof PartType) {
					Device d = SymbolTables.getDevice(this.getB());
					
				}
			} catch(Exception e) {				
				
				e.printStackTrace();
				return false;
			}
		}
//
//		System.out.println("[EndsWith.evaluate] -> "+b);
		return b;
	}

	@Override
	public boolean evaluate() 
			throws EugeneException {
		if(-1 == this.getA()) {
			throw new EugeneException("The ENDSWITH rule requires a left-hand-side rule operand!");
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
		Device d = (Device)SymbolTables.get(sDeviceName);
		Component componentB = SymbolTables.getComponent(this.getB());
		if(null != componentB && (componentB instanceof Device || componentB instanceof PartType)) {
			List<Component> lst = d.getComponents();
			if(null != lst && !lst.isEmpty()) {
				return componentB.equals(lst.get(lst.size()-1));
			}
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(sDeviceName));
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		Component componentB = SymbolTables.getComponent(this.getB());
		if(null != componentB && (componentB instanceof Device || componentB instanceof PartType)) {
			List<Component> lst = device.getComponents();
			if(null != lst && !lst.isEmpty()) {
				return componentB.equals(lst.get(lst.size()-1));
			}
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(device.getName()));
	}

	@Override
	public String getOperator() {
		return RuleOperator.ENDSWITH.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.getA() != -1) {
			sb.append(this.getA()).append(" ");
		}
		sb.append(RuleOperator.ENDSWITH).append(" ").append(this.getB());
		return sb.toString();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		return new XeqC(variables[variables.length-1], (int)this.getB());
	}

}
