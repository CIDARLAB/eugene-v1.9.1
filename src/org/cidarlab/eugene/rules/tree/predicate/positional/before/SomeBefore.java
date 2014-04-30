package org.cidarlab.eugene.rules.tree.predicate.positional.before;

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
import JaCoP.constraints.Count;
import JaCoP.constraints.IfThen;
import JaCoP.constraints.Or;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.Reified;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XneqC;
import JaCoP.constraints.XneqY;
import JaCoP.core.BooleanVar;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

/* A BEFORE B 
 * 
 * IF the long[] array, that the evaluate() method receives, CONTAINS A and B, THEN 
 *     A's first occurrence must be before B's first occurrence
 * ELSE
 *     A BEFORE B is true
 * END IF
 * 
 * Note:
 * rules like ``All A's must occur BEFORE all B's'' can be achieved 
 * by using Eugene's new ``FOR ALL'' operator...
 */
public class SomeBefore 
		extends BinaryPredicate {

	public SomeBefore(long A, long B) 
			throws EugeneException {
		super(A,B);
	}
	
	@Override
	public boolean evaluate(long[] l) {
		long idxA = ArrayUtils.indexOf(l, this.getA());
		long idxB = ArrayUtils.indexOf(l, this.getB());
		
//		System.out.println("[Before.evaluate(long[])] evaluating "+
//				this.getA()+" BEFORE "+this.getB()+" ON "+Arrays.toString(l)+" -> "+(idxA < idxB));

		/*
		 * IF the long[] array, that the evaluate() method receives, CONTAINS A and B, THEN
		 */
		boolean b = true;
		if(idxA != (-1) && idxB != (-1)) {
			/* 
			 *   A's first occurrence must be before B's first occurrence
			 */     
			b = idxA < idxB;
		}

//		System.out.println("[Before.evaluate] "+this.getA()+" BEFORE "+this.getB()+" -> "+Arrays.toString(l)+" -> "+b);

		//System.out.println("[Before.evaluate] evaluating "+this.toString()+" ON "+Arrays.toString(l)+" FALSE");
		
//		if(!b) {
//			System.err.println("[Before] violation of "+this.toString()+"....");
//		}
		return b;
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
		if((null != this.componentB && (this.componentB instanceof Device || this.componentB instanceof PartType)) &&
				null != this.componentA && (this.componentA instanceof Device || this.componentA instanceof PartType)) {
			
			int idxA = device.getComponents().indexOf(this.componentA);
			int idxB = device.getComponents().indexOf(this.componentB);

			if(idxA != (-1) && idxB != (-1)) {
				/* 
				 *   A's first occurrence must be before B's first occurrence
				 */     
				return idxA < idxB;
			}
			
			return true;
		}
		
		return this.evaluate(SymbolTables.getDeviceComponentIds(device.getName()));
	}

	@Override
	public String getOperator() {
		return RuleOperator.SOME_BEFORE.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(SymbolTables.getNameById(this.getA()))
				.append(" ").append(RuleOperator.SOME_BEFORE).append(" ")
				.append(SymbolTables.getNameById(this.getB()));
		} catch(Exception e) {
			// do nothing...
		}
		return sb.toString();
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

//		PrimitiveConstraint[] pcA = null;

		try {
			int a = (int)this.getA();
			Component componentA = this.getComponentA();

			int b = (int)this.getB();
//			Component componentB = this.getComponentB();
			
			int N = components.size();

			/*
			 * a SOME_BEFORE b
			 * 
			 * contains(a) && contains(b)
			 * => exists a: position(a) < position(b)
			 */
			
			/*
			 * figure out the last possible position of A
			 */
			int i = lastIndexOf(components, componentA);

			/*
			 * we cannot place any B before A's last possible occurence 
			 */
			PrimitiveConstraint[] pcB = new PrimitiveConstraint[N];
			for(int j=0; j<N; j++) {
				pcB[j] = new XneqC(variables[j], b);
			} 
			
			return new IfThen(
						new XeqC(variables[i], a),
						new And(pcB));
			
//			System.out.println("IMPOSING -> "+this.toString());
//			
//			PrimitiveConstraint pc[] = new PrimitiveConstraint[N];
//			for(int i=0; i<N; i++) {
//				Component component = components.get(i);
//				
//				if(component instanceof Device) {
//					
//				} else if (component instanceof PartType) {
//					if(componentA instanceof PartType) {
//						// ???
//					} else if(componentA instanceof Part) {
//						if(((Part) componentA).getPartType().getName().equals(component.getName())) {
//							
//							if(i<N-1) {
//								
//								pc[i] = new IfThen(
//										new XeqC(variables[i], a),
//										new XneqC(variables[i], b));
//
//							} else {
//								
//								/*
//								 * if we place A at the last position,
//								 * then we're not allowed to place any B before
//								 */
//								PrimitiveConstraint[] pcB = new PrimitiveConstraint[N];
//								for(int j=0; j<N; j++) {
//									pcB[j] = new XneqC(variables[j], b);
//								} 
//								
//								pc[i] = new IfThen(
//											new XeqC(variables[i], a),
//											new And(pcB));
//
//							}
//						} else {
//							// ???
//						}
//					}
//					
//				} else if (component instanceof Part) {
//					if(component.getName().equals(componentA.getName())) {
//						if(i > 0) {
//							PrimitiveConstraint[] pcB = new PrimitiveConstraint[i];
//							for(int j=0; j<i; j++) {
//								pcB[j] = new XneqC(variables[j], b);
//							}
//							
//							pc[i] = new IfThen(
//										new XeqC(variables[i], a),
//										new And(pcB));
//						} else {
//							pc[i] = new IfThen(
//										new XeqC(variables[i], a),
//										new XneqC(variables[i], b));
//						}							
//					}
//				}
//			}			
//			
//			//BooleanVar bVar = new BooleanVar(store);
//			//return new Reified(new And(pc), bVar);
//			
//			return new And(pc);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static int lastIndexOf(List<Component> components, Component X) {
		
		int lastPos = 0;
		for(int i=0; i<components.size(); i++) {
			Component component = components.get(i);
			
			if(component instanceof Device) {
				
				lastPos = lastIndexOf(components, X);
			
			} else if (component instanceof PartType) {
				if(X instanceof PartType) {
					lastPos = i;
				} else if(X instanceof Part) {
					if(((Part) X).getPartType().getName().equals(component.getName())) {
						lastPos = i;
					}
				}
				
			} else if (component instanceof Part) {
				if(X instanceof Part && X.getName().equals(component.getName())) {
					lastPos = i;
				}
				
			} else if(component instanceof Device) {
				lastPos = i;
			}
		}
		
		return lastPos;
		
	}

/*** toJaCop if we use the ``positioning'' approach 	
	@Override
	public Constraint toJaCoP(
			Store store, List<Component> components, IntVar[] variables) {

//		System.out.println("**** "+this.getComponentA().getName()+" BEFORE "+this.getComponentB().getName()+" ****");
		if(variables.length <= 1 || (null != components && components.size()<=1)) {
			IntVar iv = new IntVar(store, "invalid", 1, 1);
			return new XneqC(iv, 1);
		}

		int a = (int)this.getA();
		int b = (int)this.getB();

		IntVar ivA = (IntVar)store.findVariable(String.valueOf(a));
		IntVar ivB = (IntVar)store.findVariable(String.valueOf(b));
		
		return new XltY(ivA, ivB);
	}
***/
	
}
