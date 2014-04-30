package org.cidarlab.eugene.rules.tree.predicate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.Variable;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.Property;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.parser.EugeneParser.propertyDeclaration_return;
import org.cidarlab.eugene.rules.RuleOperator;
import org.cidarlab.eugene.rules.tree.Indexer;
import org.cidarlab.eugene.rules.tree.RuleTreeParser;

import JaCoP.constraints.And;
import JaCoP.constraints.Constraint;
import JaCoP.constraints.IfThen;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XeqY;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XgtC;
import JaCoP.constraints.XgtY;
import JaCoP.constraints.XlteqY;
import JaCoP.constraints.XneqC;
import JaCoP.constraints.XneqY;
import JaCoP.constraints.XplusYgtC;
import JaCoP.constraints.XplusYplusQgtC;
import JaCoP.core.BoundDomain;
import JaCoP.core.Domain;
import JaCoP.core.IntDomain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.ValueEnumeration;

/* Example:
 * X + Y > Z
 * 
 *        >
 *      /   \
 *     +      Z
 *    / \
 *   X   Y
 */
public class ExpressionPredicate
	implements RulePredicate {

	private CommonTree tree;
	
	public ExpressionPredicate(CommonTree tree) {
		this.tree = tree;
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
		return this.toString();
	}

	@Override
	public String toString() {
		return this.tree.toStringTree();
	}

	@Override
	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException {
		// TODO 
		
		//System.out.println("[ExpressionPredicate.toJaCoP] -> "+tree.toStringTree());
		
		if(components.size() != variables.length) {
			return null;
		}
		
		// here we need to parse the ExpressionTree
		PrimitiveConstraint pc = null;
		try {
			pc = buildConstraint(store, variables, components, tree, null);
		} catch (EugeneException e) {
			e.printStackTrace();
			pc = null;
		}
		
		return pc;
	}
	
	private PrimitiveConstraint buildConstraint(
			Store store, IntVar[] variables, List<Component> components, CommonTree t, NamedElement element) 
			throws EugeneException {
		if (null != t) {
			if(RuleTreeParser.isExpressionOperator(t.getText())) {
				
				CommonTree lhs = (CommonTree)t.getChild(0);
				CommonTree rhs = (CommonTree)t.getChild(1);
				
				if(null == lhs || null == rhs) {
					throw new EugeneException("Inavlid "+t.getText()+" rule!");
				}

				Collection<Part> lhsParts = null;
				Variable lhsConstant = null;
				
				Collection<Part> rhsParts = null;
				Variable rhsConstant = null;
				
				if(RuleOperator.EQUALS.toString().equals(t.getText()) || 
						"==".equals(t.getText())) {
					int lhsIdx = -1;
					try {
						lhsIdx = Indexer.getIndex(element, lhs);
					} catch(EugeneException e) {
						throw new EugeneException(e.toString());
					}
					
					// RIGHT-HAND SIDE
					int rhsIdx = -1;
					try {
						rhsIdx = Indexer.getIndex(element, rhs);
					} catch(EugeneException e) {
						throw new EugeneException(e.toString());
					}
					
//					System.out.println("[ExpressionPredicate] -> "+lhsIdx+" "+t.getText()+" "+rhsIdx);

					if(rhsIdx == -1 && lhsIdx!=-1) {
						
						int rhsId = (int)SymbolTables.getId(rhs.getText());
						return new XeqC(variables[lhsIdx], rhsId);
						
					} else if(rhsIdx != -1 && lhsIdx == -1) {
						
						int lhsId = (int)SymbolTables.getId(lhs.getText());
						return new XeqC(variables[rhsIdx], lhsId);
						
					} else if(rhsIdx == -1 && lhsIdx == -1) {

					} else {
						if(!(rhsIdx>=0 && rhsIdx<variables.length &&
							 lhsIdx>=0 && lhsIdx<variables.length)) {
							throw new EugeneException("Invalid indices!");
						}

						return new XeqY(variables[lhsIdx], variables[rhsIdx]);
					}
				} else if(RuleOperator.NOTEQUALS.toString().equals(t.getText()) || 
						"!=".equals(t.getText())) {
					
					int lhsIdx = -1;
					try {
						lhsIdx = Indexer.getIndex(element, lhs);
					} catch(EugeneException e) {
						throw new EugeneException(e.toString());
					}
					
					// RIGHT-HAND SIDE
					int rhsIdx = -1;
					try {
						rhsIdx = Indexer.getIndex(element, rhs);
					} catch(EugeneException e) {
						throw new EugeneException(e.toString());
					}

					if(rhsIdx == -1 && lhsIdx!=-1) {
						
						int rhsId = (int)SymbolTables.getId(rhs.getText());
						return new XneqC(variables[lhsIdx], rhsId);
						
					} else if(rhsIdx != -1 && lhsIdx == -1) {
						
						int lhsId = (int)SymbolTables.getId(lhs.getText());
						return new XneqC(variables[rhsIdx], lhsId);
						
					} else if(rhsIdx == -1 && lhsIdx == -1) {
						// ???
					} else {
						if(!(rhsIdx>=0 && rhsIdx<variables.length &&
							 lhsIdx>=0 && lhsIdx<variables.length)) {
							throw new EugeneException("Invalid indices!");
						}

						return new XneqY(variables[lhsIdx], variables[rhsIdx]);
					}
					
				} else if("<".equals(t.getText()) ||
						"<=".toString().equals(t.getText()) ||
						">=".toString().equals(t.getText()) ||
						">".toString().equals(t.getText())) {

					/*** LEFT-HAND SITE ***/
					/*
					 * 1. the part type
					 */
					PartType pt = (PartType)this.getComponent(components, (CommonTree)tree.getChild(0));
				    IntVar promoterVar = (IntVar)store.findVariable(pt.getName());
					if(null == promoterVar) {
						throw new EugeneException("I cannot find the variable "+pt.getName());
					}
					
					
					PrimitiveConstraint[] pc = null;
					
        			/*
        			 * WE CAN DO THIS BETTER!
        			 * 
        			 * getting the part type's properties and querying every property
        			 * from every part...
        			 * then, we also need to iterate over each part's additional properties
        			 */
        			
        			/*
        			 * and we're also creating varialbes for each part's property values
        			 */
					String partTypeName = pt.getName();
					String sPropertyName = this.getPropertyName((CommonTree)tree.getChild(0));
//					System.out.println("[ExpressionPredicate] -> "+partTypeName);
    				Collection<Part> lstParts = SymbolTables.getParts(pt);
    				for(Part part : lstParts) {
    					
    					long partId = SymbolTables.getId(part.getName());
						IntVar propVar = (IntVar)store.findVariable(partTypeName+"."+sPropertyName);
						if(null == propVar) {
        					propVar = new IntVar(store, partTypeName+"."+sPropertyName);
						} 
    					
    					// iterate over the parts properties
    					Property property = part.getProperty(sPropertyName);
    					if(null != property) {
    						
							PropertyValue pv = part.getPropertyValue(sPropertyName);
							if(null != pv) {
								int value;
	    						if(EugeneConstants.NUM.equals(pv.getType())) {
	    							value = (int)pv.getNum();
	    						} else {
	    							value = pv.getValue().hashCode();
	    						}
	    						propVar.addDom(value, value);
	    						
	    						/*
	    						 * TODO:
	    						 * find out the index of the promoters in the variables' list
	    						 */
	    						
							}
     					}
    					
						if(null == pc) {
							pc = new PrimitiveConstraint[1];
							pc[0] = new IfThen(new XgtC(propVar, 1), new XeqC(variables[0], (int)partId));
						} else {
							pc = ArrayUtils.add(pc, new IfThen(new XgtC(propVar, 1), new XeqC(variables[0], (int)partId)));
						}

					}
					

					return new And(pc);
							
					/*
					 * 3. the index of the part type in the components list
					 */
					
					
//					/*** RIGHT-HAND SITE ***/
//					int d;
//					try { 
//						d = Integer.parseInt(tree.getChild(1).getText());
//					} catch(Exception e) {
//						NamedElement rhsElement = SymbolTables.get(tree.getChild(1).getText());
//						if(null != rhsElement && rhsElement instanceof Variable) {
//							Variable v = (Variable)rhsElement;
//							if(EugeneConstants.NUM.equals(v.getType())) {
//								d = (int)v.getNum();
//							} else {
//								throw new EugeneException(tree.getChild(1).getText()+" is an invalid variable!");
//							}
//						} else {
//							throw new EugeneException(tree.getChild(1).getText()+" is an invalid constant!");
//						}
//					}
//
//					IntVar D = new IntVar(store, "D", -d, -d);
//					
//					System.out.println("[ExpressionPredicate] -> "+pt.getName()+" . "+sPropertyName+" "+tree.getText()+" "+d);
//					
//					/* get the idx of the part type */
//					PrimitiveConstraint[] pc = null;
//					int idx = 0;
//					for(Component component : components) {
//						if(component.getName().equals(pt.getName())) {
//
//							// get all parts of the part type
//							Collection<Part> parts = SymbolTables.getParts(pt);
//							if(null != parts && !parts.isEmpty()) {
//								for(Part part : parts) {
//									PropertyValue pv = part.getPropertyValue(sPropertyName);
//									if(EugeneConstants.NUM.equals(pv.getType())) {
//										
//										IntVar iv = variables[idx];
//										System.out.println(iv.dom());
//										System.out.println(part.getName()+" . "+sPropertyName+" -> "+pv.getNum());
//										IntVar propVal = new IntVar(store, part.getName()+"_"+sPropertyName, (int)pv.getNum(), (int)pv.getNum());
//										iv.domain.searchConstraints.add(new XplusYplusQgtC(variables[idx], propVal, D, 0));
//										
////										if(null == pc) {
////											pc = new PrimitiveConstraint[1];
////											pc[0] = new XplusYplusQgtC(variables[idx], propVal, D, 0);
////										} else {
////											pc = ArrayUtils.add(pc, new XplusYplusQgtC(variables[idx], propVal, D, 1));
////										}
//
//									}
//
//								}
//							}
//							
//						}
//						idx++;
//					}
//					pc = ArrayUtils.remove(pc, 0);

//					return new And(pc);
				}

			}
		}
		
		return null;	
	}

	private Component getComponent(List<Component> components, CommonTree tree) 
			throws EugeneException {

		/* first, get the component of the component list */
		for(Component component : components) {
			if(tree.getText().equals(component.getName())) {
				return component;
			}
		}

		throw new EugeneException("I cannot find "+tree.getText()+" in the list of "+components+"!");
	}
	
	private String getPropertyName(CommonTree tree) {		
		return tree.getChild(0).getChild(0).getText();
	}

	public IntVar traverseExpressionTree(Store store, IntVar[] variables, List<Component> components, Tree tree) 
			throws EugeneException {
		if(null != tree) {
			if(RuleTreeParser.isExpressionOperator(tree.getText())) {

				System.out.println("expressionPredicate.traverseExpressionTree -> "+tree.getText()+" -> "+tree.getChildCount());
				
				IntVar lhs = traverseExpressionTree(store, variables, components, tree.getChild(0));
				IntVar rhs = traverseExpressionTree(store, variables, components, tree.getChild(1));
				
				if("+".equals(tree.getText())) {
					
				} else if("-".equals(tree.getText())) {
					
				} else if("*".equals(tree.getText())) {
					
				} else if("/".equals(tree.getText())) {
					
				}
				
				return null;
			} else if(tree.getChildCount() == 0) {
				
				/*
				 * this could either be a constant 
				 * or a variable
				 */
				
				int d;
				try { 
					d = Integer.parseInt(tree.getText());
				} catch(Exception e) {
					NamedElement element = SymbolTables.get(tree.getText());
					if(null != element && element instanceof Variable) {
						Variable v = (Variable)element;
						if(EugeneConstants.NUM.equals(v.getType())) {
							d = (int)v.getNum();
						} else {
							throw new EugeneException(tree.getText()+" is an invalid variable!");
						}
					} else {
						throw new EugeneException(tree.getText()+" is an invalid constant!");
					}
				}

				return new IntVar(store, (int)d, (int)d);
			}
		}	
		return null;
	}
	
	@Override
	public boolean evaluate(Device device) throws EugeneException {
		// TODO Auto-generated method stub
		return false;
	}
	
//	private int getIndex(NamedElement element, CommonTree t) 
//			throws EugeneException {
//		
//		NamedElement objElement = null;
//		if(element == null) {
//			objElement = SymbolTables.get(t.getText());						
//		} else {
//			objElement = element.get(t.getText());
//		}
//
//		if(objElement == null) {
//			throw new EugeneException("I don't know "+t.getText());						
//		}
//		
//		if(t.getChildCount() == 1) {
//			return calculateIndex((CommonTree)t.getChild(0), objElement, 0);
//		}
//		return -1;
//	}
//	
//	public int calculateIndex(CommonTree tree, NamedElement element, int global_index) 
//			throws EugeneException {
//		if(tree != null) {
//			
//			if("[".equals(tree.getText()) || 
//				".".equals(tree.getText())) {
//
//				if(tree.getChildCount() == 2) {
//
//					int idx1 = calculateIndex((CommonTree)tree.getChild(0), element, global_index);
//					for(int k=0; k<idx1; k++) {
//						global_index += element.get(k).size();
//					}
//					element = element.get(idx1);
//					int idx2 = calculateIndex((CommonTree)tree.getChild(1), element, global_index);
//					return global_index + idx2;
//					
//				} else if(tree.getChildCount() == 1) {
//					//System.out.println("E -> "+global_index);
//					if("[".equals(tree.getText())) {
//						return this.toIndex(tree.getChild(0).getText());
//					} else {
//						return this.elementIndexOf(element, tree.getChild(0).getText());
//					}
//				} else {   // <-- this case should never happen!					
//					throw new EugeneException("I don't know what happened!");
//				}
//			} else if(tree.getChildCount() == 0) {
//				if("[".equals(tree.getParent().getText())) {
//					global_index += this.toIndex(tree.getText());
//				} else if(".".equals(tree.getParent().getText())) {
//					global_index += this.elementIndexOf(element, tree.getText());
//				}
//			}
//		}
//		//System.out.println("???");
//		return 0;
//	}
//	
//	/*
//	 * the elementIndexOf() method returns the index of the name s 
//	 * in the element's components
//	 */
//	private int elementIndexOf(NamedElement element, String s) 
//			throws EugeneException {
//
//		if(null != element && element instanceof Component) {
//			Component component = (Component)element;
//			
//			if(component instanceof Device) {
//				Device device = (Device)component;
//				int idx = 0;
//				for(Component comp : device.getComponents()) {
//					if(comp.getName().equals(s)) {
//						return idx;
//					}
//					idx ++;
//				}
//			}
//		}
//		return -1;
//	}
//	
//	private int toIndex(String s) 
//			throws EugeneException {
//		int idx = -1;
//		try {
//			// the index is either a constant
//			idx = Integer.valueOf(s);
//		} catch(Exception e) {
//			// or a variable ...
//			
//			// do I need to check this here again??
//			NamedElement objIdx = SymbolTables.get(s);
//			if(null != objIdx && objIdx instanceof Variable) {
//				try {
//					idx = Integer.valueOf(((Variable)objIdx).getValue());
//				} catch(Exception exc) {
//					idx = -1;
//				}
//			} else {
//				idx = -1;
//			}
//		}
//		
//		if(idx >= 0) {
//			return idx;
//		} else {				
//			throw new EugeneException(s+" is an invalid index!");
//		}
//	}

}
