package org.cidarlab.eugene.rules.tree.predicate.positional.nextto;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.RuleOperator;
import org.cidarlab.eugene.rules.tree.predicate.BinaryPredicate;

import JaCoP.constraints.And;
import JaCoP.constraints.Constraint;
import JaCoP.constraints.IfThen;
import JaCoP.constraints.Or;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XneqC;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

/*
 * A NEXTTO B
 * 
 * Definition:  
 */
public class AllNextTo 
	extends BinaryPredicate {

	public AllNextTo(long A, long B) 
			throws EugeneException {
		super(A, B);
	}
	
	@Override
	public boolean evaluate(long[] l) {
		int idxA = ArrayUtils.indexOf(l, this.getA());
		int idxB = ArrayUtils.indexOf(l, this.getB());

		if(idxA!=(-1) && idxB!=(-1)) {
			return idxA+1==idxB || idxA-1==idxB || idxB+1==idxA || idxB-1==idxA;
		}
		
		return true;
		
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

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		Component componentA = this.getComponentA();
		if(null == componentA) {
			componentA = SymbolTables.getComponent(this.getA());
		}
		
		Component componentB = this.getComponentB();
		if(null == componentB) {
			componentB = SymbolTables.getComponent(this.getB());
		}

		if((null != componentB && (componentB instanceof Device || componentB instanceof PartType)) &&
				null != componentA && (componentA instanceof Device || componentA instanceof PartType)) {
			
			int idxA = device.getComponents().indexOf(componentA);
			int idxB = device.getComponents().indexOf(componentB);

			if(idxA!=(-1) && idxB!=(-1)) {
				return idxA+1==idxB || idxA-1==idxB || idxB+1==idxA || idxB-1==idxA;
			}
			
		}
		return this.evaluate(SymbolTables.getDeviceComponentIds(device.getName()));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(this.componentA.getName())
				.append(" ").append(RuleOperator.ALL_NEXTTO).append(" ")
				.append(this.componentB.getName());
		} catch(Exception e) {}
		return sb.toString();
	}

	@Override
	public String getOperator() {
		return RuleOperator.ALL_NEXTTO.toString();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

		if(variables.length <= 1 || (null != components && components.size()<=1)) {
			IntVar iv = new IntVar(store, "invalid", 1, 1);
			return new XneqC(iv, 1);
		}

		try {
			int a = (int)this.getA();
			int b = (int)this.getB();
			
			int[] idxA = indexOf(components, this.getComponentA());

			/*
			 * a NEXTTO b
			 */
			PrimitiveConstraint pcA = constraintIndices(variables, idxA, a, b);

//			System.out.println("[AllNextTo.toJaCoP] -> "+this.toString());
			return pcA;

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * a NEXTTO b
	 */
	private static PrimitiveConstraint constraintIndices(IntVar[] variables, int[] indices, int a, int b) {
		int N = variables.length;
		PrimitiveConstraint[] pc = new PrimitiveConstraint[indices.length];
		for(int i=0; i<indices.length; i++) {
			int idx = indices[i];
			if(idx > 0 && idx<N-1) {
				
				/*
				 * if a is placed at any position (except the first and the last position),
				 * then place b either at the position immediately before or 
				 *                     at the position immediately after
				 */
				pc[i] = new IfThen(
							new XeqC(variables[idx], a),
							new Or(
									new XeqC(variables[idx-1], b),
									new XeqC(variables[idx+1], b)));
			} else if(idx == 0) {
				/*
				 * if a is placed at the first position,
				 * then place b at the second position
				 */
				pc[i] = new IfThen(
							new XeqC(variables[idx], a),
							new XeqC(variables[idx+1], b));
			} else if(idx == N-1) {
				
				/*
				 * if a is placed at the last position,
				 * then place b at the second last position
				 */
				pc[i] = new IfThen(
								new XeqC(variables[idx], a),
								new XeqC(variables[idx-1], b));
			}
		}
		
		return new And(pc);
	}
	
	private static int[] indexOf(List<Component> components, Component X) {
		
		int[] idx = null;
		for(int i=0; i<components.size(); i++) {
			Component component = components.get(i);
			
			if(component instanceof Device) {
				
				if(X instanceof Device) {
					if(idx != null) {
						idx = ArrayUtils.add(idx, i);
					} else {
						idx = new int[1];
						idx[0] = i;
					}
				} else {
					if(null == idx) {
						idx = indexOf(components, X);
					} else {
						idx = ArrayUtils.addAll(indexOf(components, X), idx);
					}
				}
				
			} else if (component instanceof PartType) {
				if(X instanceof PartType) {					
					if(null == idx) {
						idx = new int[1];
						idx[0] = i;
					} else {
						idx = ArrayUtils.add(idx, i);
					}
				} else if(X instanceof Part) {
					if(((Part) X).getPartType().getName().equals(component.getName())) {
						if(null == idx) {
							idx = new int[1];
							idx[0] = i;
						} else {
							idx = ArrayUtils.add(idx, i);
						}
					}
				}
				
			} else if (component instanceof Part) {
				if(X instanceof Part && X.getName().equals(component.getName())) {
					if(null == idx) {
						idx = new int[1];
						idx[0] = i;
					} else {
						idx = ArrayUtils.add(idx, i);
					}
				}
			}
		}
		
		return idx;
		
	}

}
