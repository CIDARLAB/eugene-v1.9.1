package org.cidarlab.eugene.rules.tree.predicate.positional.before;

import java.util.Arrays;
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
public class AllBefore 
		extends BinaryPredicate {

	public AllBefore(long A, long B) 
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
		return RuleOperator.ALL_BEFORE.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(SymbolTables.getNameById(this.getA()))
				.append(" ").append(RuleOperator.ALL_BEFORE).append(" ")
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

			int a = (int)this.getA();
			Component componentA = this.getComponentA();

			int b = (int)this.getB();
			Component componentB = this.getComponentB();
			
			int N = components.size();

			/*
			 * a ALLBEFORE b
			 * 
			 * for all a exists b: position(a) < position(b)
			 */
			
			
			int[] idxA = getPossiblePositions(componentA, components);
			int[] idxB = getPossiblePositions(componentB, components);
			
//			System.out.println(componentA.getName()+" -> "+Arrays.toString(idxA));
//			System.out.println(componentB.getName()+" -> "+Arrays.toString(idxB));
//			System.out.println("IMPOSING -> "+this.toString());
			
			PrimitiveConstraint[] pcA = null;

			if(idxA != null && idxB != null) {
				
				for(int iA=0; iA<idxA.length; iA++) {
					
					//System.out.print(componentA.getName()+" at "+idxA[iA]+" -> ");
					PrimitiveConstraint[] pcB = null;
					for(int iB=0; iB<idxB.length; iB++) {
						//System.out.print(componentB.getName()+" NOT at "+idxB[iB]+" -> ");
						if(idxB[iB] <= idxA[iA]) {
								pcB = addConstraint(
										pcB, new XneqC(variables[idxB[iB]], b));
						}
					}

					//pcA = addConstraint(pcA,
					if(pcB != null) {
						store.impose(new IfThen(
									new XeqC(variables[idxA[iA]], a), 
									new And(pcB)));
					}

//					System.out.println();
				}
			} //else {
			//	throw new EugeneException("Invalid Constraint: " + this.toString());
			//}

			return null;
		
//			return new And(pcA);
	}
	
	private PrimitiveConstraint[] addConstraint(PrimitiveConstraint[] pc, PrimitiveConstraint c) {
		if(pc == null) {
			pc = new PrimitiveConstraint[1];
			pc[0] = c; 
		} else {
			pc = ArrayUtils.add(pc, c);
		}
		
		return pc;
	}

	/*
	 * the getPossiblePositions() method returns 
	 * a list of indices at which positions the Component c
	 * can be placed
	 */
	private int[] getPossiblePositions(Component component, List<Component> lst) {
//		System.out.println("[AllBefore.getPossiblePositions] => "+component.getName()+", "+lst);
		int[] indices = null;
		int i=0;
		for(Component listComponent : lst) {
			if(listComponent instanceof PartType && component instanceof PartType) {
				if(((PartType)listComponent).getName().equals(((PartType)component).getName())) {
					indices = addIndex(indices, i);
				}
			} else if(listComponent instanceof PartType && component instanceof Part) {
				if(((PartType)listComponent).getName().equals(((Part)component).getPartType().getName())) {
					indices = addIndex(indices, i);
				}
			} else if(listComponent instanceof Part && component instanceof PartType) {
				// NOT POSSIBLE
			} else if(listComponent instanceof Part && component instanceof Part) {
				if(((Part)listComponent).getName().equals(((Part)component).getName())) {
					indices = addIndex(indices, i);
				}
				
			/*
			 * Permutations on Devices	
			 */
			} else if(listComponent instanceof Device && component instanceof Device) {
				indices = addIndex(indices, i);
			}
			
			i++;
		}
		
		return indices;
	}
	
	private int[] addIndex(int[] array, int idx) {
		if(array == null) {
			array = new int[1];
			array[0] = idx; 
		} else {
			array = ArrayUtils.add(array, idx);
		}
		return array;
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
