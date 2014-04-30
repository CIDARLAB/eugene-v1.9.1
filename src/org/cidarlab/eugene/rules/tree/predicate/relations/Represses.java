package org.cidarlab.eugene.rules.tree.predicate.relations;

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.fact.relation.Relation;
import org.cidarlab.eugene.rules.RuleOperator;
import org.cidarlab.eugene.rules.tree.Indexer;
import org.cidarlab.eugene.rules.tree.predicate.pairing.PairingPredicate;

import JaCoP.constraints.And;
import JaCoP.constraints.Constraint;
import JaCoP.constraints.Or;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XeqC;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class Represses 
	extends PairingPredicate {
	
	public Represses(CommonTree tree) {
		super(tree);
	}
		
	@Override
	public boolean evaluate(long[] l) 
			throws EugeneException {
		/* here we need to parse the tree 
		 * and retrieve the values of the node's information 
		 * from the symboltables
		 * and perform the appropriate actions  
		 */
		
		// currently we just print-out the tree
		return false;
	}
	
	@Override
	public boolean evaluate(long n) 
			throws EugeneException {
		return false;
	}
	
	@Override
	public boolean evaluate(String sDeviceName) 
			throws EugeneException {
		return false;
	}
	
	@Override
	public boolean evaluate() 
			throws EugeneException {
		return false;
	}
	
	@Override
	public String getOperator() {
		return RuleOperator.REPRESSES.toString();
	}

	public String toString() {
		return this.tree.toStringTree();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {

//		System.out.println("IMPOSING "+this.toString()+"...");
		
//		NamedElement element = null;
//		if(components.size() != variables.length) {
//			
//			/*
//			 * this is a current hack
//			 * -> we should pass the device to the toJaCoP methods instead a list of components!
//			 */
//			element = new Device(null, components);
//			
////			System.out.println("[Represses.toJaCoP] -> "+element);
////			
//		}
		
		// here we need to parse the ExpressionTree
		PrimitiveConstraint pc = null;
		try {
			pc = buildConstraint(store, variables, components, tree, device);
			store.impose(pc);
		} catch (EugeneException e) {
			e.printStackTrace();
			pc = null;
		}
		
		return null;
	}
	
	private PrimitiveConstraint buildConstraint(
			Store store, IntVar[] variables, List<Component> components, CommonTree t, NamedElement element) 
			throws EugeneException {
		

		if (null != t) {
			CommonTree lhs = (CommonTree)t.getChild(0);
			CommonTree rhs = (CommonTree)t.getChild(1);
			
			if(null == lhs || null == rhs) {
				throw new EugeneException("Inavlid "+t.getText()+" rule!");
			}
			
//			System.out.println("[Represses.buildConstraint] element -> "+element+", lhs -> "+lhs.toStringTree()+", rhs: "+rhs.toStringTree());

			int lhsIdx = -1;
			Component lhsComponent = null;
			try {
				lhsIdx = Indexer.getIndex(element, lhs);
				if(lhsIdx == -1) {
					lhsComponent = (Component)SymbolTables.get(lhs.getText());
				}
			} catch(EugeneException e) {
				throw new EugeneException(e.getMessage());
			}
			
			// RIGHT-HAND SIDE
			int rhsIdx = -1;
			try {
				rhsIdx = Indexer.getIndex(element, rhs);
			} catch(EugeneException e) {
				throw new EugeneException(e.getMessage());
			}
			
//			if(lhsIdx == -1 || rhsIdx == -1) {
//				throw new EugeneException("Invalid index! ("+lhsIdx+", "+rhsIdx+")");
//			}
			
			if(null == element) {
				if( null != lhsComponent && lhsIdx == -1) {
					return this.buildConstraint(
							store, variables,
							lhsComponent, -1,
							components.get(rhsIdx), rhsIdx);
				} else {
					return this.buildConstraint(
							store, variables,
							components.get(lhsIdx), lhsIdx, 
							components.get(rhsIdx), rhsIdx);
				}
			} else if (element instanceof Device) {
//				System.out.println(lhsIdx+" "+this.getOperator()+" "+rhsIdx);
				
				if( null != lhsComponent && lhsIdx == -1) {
					return this.buildConstraint(
							store, variables,
							lhsComponent, -1,
							components.get(rhsIdx), rhsIdx);
				} else {
					return this.buildConstraint(
							store, variables,
							((Device)element).getAllComponents().get(lhsIdx), lhsIdx, 
							((Device)element).getAllComponents().get(rhsIdx), rhsIdx);
				}
			}
		}
		
		return null;	
	}
	
	private PrimitiveConstraint buildConstraint(
				Store store, IntVar[] variables, 
				Component lhs, int lhsIdx, Component rhs, int rhsIdx) 
			throws EugeneException {
		/*
		 * first, we need to query all parts of the given part type
		 * and their corresponding partners 
		 */
		long[][] pairs = null;
		try {
			if(rhs instanceof Device || rhs instanceof PartType) {
				pairs = SymbolTables.queryPairs(Relation.REPRESSES.toString(), lhs, null);
			} else if(rhs instanceof Part) {
				pairs = SymbolTables.queryPairs(Relation.REPRESSES.toString(), lhs, rhs);
			}
		} catch(Exception e) {
			throw new EugeneException(e.getMessage());
		}
		
//		for(int i=0; i<pairs.length; i++) {
//			System.out.println(Arrays.toString(pairs[i]));
//		}
		
		// then, we iterate over the retrieved parts
		// creating appropriate JaCoP constraints
		
		
		if(null != pairs) {
			PrimitiveConstraint[] pcArray = new PrimitiveConstraint[pairs.length];
			for(int i=0; i<pairs.length; i++) {
				
//				IntVar ivRepressor = null;
//				if(lhsIdx == -1) {
//					ivRepressor = new IntVar(store, (int)pairs[i][0], (int)pairs[i][0]);
//				}
//
//				if(null != ivRepressor) {
//					pcArray[i] = new And(
//							new XeqC(ivRepressor, (int)pairs[i][0]),
//							new XeqC(variables[rhsIdx], (int)pairs[i][1]));
//				} else {
				
					pcArray[i] = new And(
						new XeqC(variables[lhsIdx], (int)pairs[i][0]),
						new XeqC(variables[rhsIdx], (int)pairs[i][1]));
//				}
			}
			return new Or(pcArray);
		}
			
		return null;
	}

	@Override
	public boolean evaluate(Device device) 
			throws EugeneException {
		throw new UnsupportedOperationException("a REPRESSES b is not available yet");
	}
	

}
