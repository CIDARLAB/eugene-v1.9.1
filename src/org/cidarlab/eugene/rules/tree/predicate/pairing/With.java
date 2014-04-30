package org.cidarlab.eugene.rules.tree.predicate.pairing;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.RuleOperator;
import org.cidarlab.eugene.rules.tree.predicate.BinaryPredicate;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.Count;
import JaCoP.constraints.Or;
import JaCoP.constraints.And;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XneqC;
import JaCoP.core.IntVar;
import JaCoP.core.Store;


public class With 
		extends BinaryPredicate {

	public With(long A, long B) 
			throws EugeneException {
		super(A, B);
	}
	
	public boolean evaluate(long[] elements) {		
		return (-1)!=ArrayUtils.indexOf(elements, this.getB()) && (-1)!=ArrayUtils.indexOf(elements, this.getA());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try { 
		sb.append(SymbolTables.getNameById(this.getA())).append(" ")
			.append(RuleOperator.WITH).append(" ")
			.append(SymbolTables.getNameById(this.getB()));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public boolean evaluate() 
			throws EugeneException {
		if(-1 == this.getA()) {
			throw new EugeneException("The "+RuleOperator.WITH+" requires a left-hand-side rule operand!");
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(this.getA()));
	}

	@Override
	public boolean evaluate(long n) 
			throws EugeneException {
		long[] device_components = SymbolTables.getAllDeviceComponentIds(n);
		//System.out.println("[Contains.evaluate("+SymbolTables.getNameById(n)+")] -> "+
		//		this.toString()+" -> "+Arrays.toString(device_components) + " -> "+this.evaluate(device_components));
		//return true;
		return this.evaluate(device_components);
	}

	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return this.evaluate(SymbolTables.getDeviceComponentIds(sDeviceName));
	}

	@Override
	public String getOperator() {
		return RuleOperator.WITH.toString();
	}


	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		
		// a WITH b ::= CONTAINS a AND CONTAINS b 
		
		// OPTION 1: using global constraints
		
		IntVar aCounter = (IntVar)store.findVariable("CONTAINS_"+this.getA()+"-counter");
		if(null == aCounter) {
			aCounter = new IntVar(store, "CONTAINS_"+this.getA()+"-counter", 1, components.size());
		}
		store.impose(new Count(variables, aCounter, (int)this.getA()));

		
		IntVar bCounter = (IntVar)store.findVariable("CONTAINS_"+this.getB()+"-counter");
		if(null == bCounter) {
			bCounter = new IntVar(store, "CONTAINS_"+this.getB()+"-counter", 1, components.size());
		}
		store.impose(new Count(variables, bCounter, (int)this.getB()));

		return null;

//		// OPTION 2: using primitive constraints
//		System.out.println("IMPOSING "+this.toString());
//		int a = (int)this.getA();
//		int b = (int)this.getB();
//		int NR_OF_VARIABLES = variables.length;
//		
//		PrimitiveConstraint[] pcA = new PrimitiveConstraint[NR_OF_VARIABLES];		
//		PrimitiveConstraint[] pcB = new PrimitiveConstraint[NR_OF_VARIABLES];		
//
//		for(int p=0; p<NR_OF_VARIABLES; p++) {
//			pcA[p] = new And(
//						new XeqC(variables[p], a), 
//						new XneqC(variables[p], b));
//			pcB[p] = new And(
//						new XeqC(variables[p], b), 
//						new XneqC(variables[p], a));
//		}
//		
//		return new And(
//				new Or(pcA),
//				new Or(pcB));		
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		/*
		 * ON device : A WITH B
		 * 
		 * -> iff A is in the device, then B must be in there too
         *
		 * truth table:
		 * A   B    
		 * 0   0   true
		 * 0   1   true
		 * 1   0   false
		 * 1   1   true
		 */
		
		// Q: what's the difference to A THEN B ???
		
		return false;
	}

}
