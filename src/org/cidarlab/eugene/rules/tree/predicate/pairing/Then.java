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
import JaCoP.constraints.IfThen;
import JaCoP.constraints.Or;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XeqC;
import JaCoP.core.IntVar;
import JaCoP.core.Store;


public class Then 
		extends BinaryPredicate {

	public Then(long A, long B) 
			throws EugeneException {
		super(A, B);
	}
	
	public boolean evaluate(long[] elements) {	
		/* if A is present */
		if((-1)!=ArrayUtils.indexOf(elements, this.getA())) {
			/* then B must be present too */
			return (-1)!=ArrayUtils.indexOf(elements, this.getB());
		}
		/* else */
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(SymbolTables.getNameById(this.getA()))
				.append(" ").append(RuleOperator.THEN).append(" ")
				.append(SymbolTables.getNameById((this.getB())));
		} catch(Exception e) {}
		return sb.toString();
	}

	@Override
	public boolean evaluate() 
			throws EugeneException {
		if(-1 == this.getA()) {
			throw new EugeneException("The "+RuleOperator.THEN+" requires a left-hand-side rule operand!");
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(this.getA()));
	}

	@Override
	public boolean evaluate(long nDeviceId) 
			throws EugeneException {
		long[] device_components = SymbolTables.getAllDeviceComponentIds(nDeviceId);
		//System.out.println("[Contains.evaluate("+SymbolTables.getNameById(n)+")] -> "+
		//		this.toString()+" -> "+Arrays.toString(device_components) + " -> "+this.evaluate(device_components));
		//return true;
		return this.evaluate(device_components);
	}

	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return this.evaluate(SymbolTables.getId(sDeviceName));
	}

	@Override
	public String getOperator() {
		return RuleOperator.THEN.toString();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

		int a = (int)this.getA();
		int b = (int)this.getB();
		int NR_OF_VARIABLES = variables.length;
		//System.out.println("imposing "+a+" THEN "+b);
		
		/*
		 * a THEN b
		 * 
		 * IF CONTAINS a THEN CONTAINS b
		 */
		PrimitiveConstraint[] pcA = new PrimitiveConstraint[NR_OF_VARIABLES];
		for(int posA = 0; posA<NR_OF_VARIABLES; posA ++) {
			
			PrimitiveConstraint[] pcB = new PrimitiveConstraint[NR_OF_VARIABLES-1];
			for(int posB = 0, i=0; posB<NR_OF_VARIABLES; posB++) {
				if(posB != posA) {
					pcB[i++] = new XeqC(variables[posB], b);
				}			
			}
			
			pcA[posA] = new IfThen(new XeqC(variables[posA], a),
							new Or(pcB));
		}
		
		return new Or(pcA);
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		/*
		 * ON device : A THEN B
		 * 
		 * -> if A is in the device, then B must be in there too
		 * 
		 * truth table:
		 * A   B    
		 * 0   0   true
		 * 0   1   true
		 * 1   0   false
		 * 1   1   true
		 */
		return false;
	}
	
}
