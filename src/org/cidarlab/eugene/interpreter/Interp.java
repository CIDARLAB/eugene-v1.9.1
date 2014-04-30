/*
Copyright (c) 2012 Boston University.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL BOSTON UNIVERSITY BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
BOSTON UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

BOSTON UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND BOSTON UNIVERSITY HAS
NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.
 */

package org.cidarlab.eugene.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.algorithm.Permutor;
import org.cidarlab.eugene.algorithm.Product;
import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.data.pigeon.Pigeon;
import org.cidarlab.eugene.data.sbol.SBOLExporter;
import org.cidarlab.eugene.data.sbol.SBOLImporter;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.SavableElement;
import org.cidarlab.eugene.dom.Variable;
import org.cidarlab.eugene.dom.arrays.ComponentArray;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.arrays.GeneratedDeviceArray;
import org.cidarlab.eugene.dom.arrays.PartArray;
import org.cidarlab.eugene.dom.arrays.PartTypeArray;
import org.cidarlab.eugene.dom.arrays.PropertyArray;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.dom.collection.CollectionOps;
import org.cidarlab.eugene.dom.collection.EugeneCollection;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.Property;
import org.cidarlab.eugene.dom.components.types.DeviceType;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.functions.Function;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.fact.relation.Binds;
import org.cidarlab.eugene.fact.relation.Drives;
import org.cidarlab.eugene.fact.relation.Induces;
import org.cidarlab.eugene.fact.relation.Matches;
import org.cidarlab.eugene.fact.relation.Ortho;
import org.cidarlab.eugene.fact.relation.Relation;
import org.cidarlab.eugene.fact.relation.Represses;
import org.cidarlab.eugene.factory.DeviceFactory;
import org.cidarlab.eugene.output.ResultSet;
import org.cidarlab.eugene.rules.RuleMemo;
import org.cidarlab.eugene.rules.tree.Indexer;

import com.rits.cloning.Cloner;


public class Interp {

	private ResultSet objResultSet;
	private Cloner cloner;
	
	public Interp() {
		this.objResultSet = new ResultSet();
		this.cloner = new Cloner();
	}

	/*** CREATIONAL METHODS ***/
	public Variable createVariable(String sName, String sType)
			throws EugeneException {
		if (SymbolTables.contains(sName)) {
			throw new EugeneException("There exists already an element named " + sName);
		}

		Variable objVariable = EugeneBuilder.buildVariable(sName, sType);
		if (null != objVariable && null != sName) {
			SymbolTables.put(objVariable);
		}
		return objVariable;
	}

	public Property createProperty(String sName, String sType)
			throws EugeneException {
		
		if (SymbolTables.contains(sName)) {
			throw new EugeneException("There exists already an element named "
					+ sName);
		}
		Property objProperty = EugeneBuilder.buildProperty(sName, sType);
		SymbolTables.put(objProperty);

		return objProperty;
	}

	public PartType createPartType(String sName, List<NamedElement> lstElements)
			throws EugeneException {

		if (SymbolTables.contains(sName)) {
			throw new EugeneException("There exists already an element named "
					+ sName);
		}

		List<Property> lstProperties = (List<Property>) null;
		if (null != lstElements && !lstElements.isEmpty()) {
			lstProperties = new ArrayList<Property>(lstElements.size());
			for (NamedElement objElement : lstElements) {
				if (objElement instanceof Property) {
					Property objProperty = (Property) objElement;
					if (!lstProperties.contains(objProperty)) {
						lstProperties.add(objProperty);
					} else {
						throw new EugeneException("The part type " + sName
								+ " contains the property "
								+ objProperty.getName() + " already!");
					}
				} else {
					throw new EugeneException("The " + objElement.getName()
							+ " element is not a Property!");
				}
			}
		}

		PartType objPartType = EugeneBuilder
				.buildPartType(sName, lstProperties);
		SymbolTables.put(objPartType);
		return objPartType;
	}

	public Part createPart(PartType objPartType, String sName, List<?> lstValues)
			throws EugeneException {

		if (SymbolTables.contains(sName)) {
			throw new EugeneException("There exists already an element named "
					+ sName);
		}

		
		// iterate over the lstValues list
		List<PropertyValue> lstPropertyValues = null;
		if (null != lstValues) {
			lstPropertyValues = new ArrayList<PropertyValue>();

			int i = 0;
			Iterator<?> it = lstValues.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				if (obj instanceof NamedElement) {

					Property objProperty = null;
					PropertyValue objPropertyValue = null;

					NamedElement objElement = (NamedElement) obj;
					if (objElement instanceof PropertyValue) {
						objPropertyValue = (PropertyValue) objElement;

						// check if the part type has this property
						objProperty = new Property(objPropertyValue.getName(),
								objPropertyValue.getType());

						if (!objPartType.contains(objProperty)) {
							throw new EugeneException(
									"The "
											+ objPartType.getName()
											+ " part type does not contain a property named "
											+ objProperty.getName() + "!");
						}
					} else if (objElement instanceof Variable) {
						Variable objVariable = (Variable) objElement;

						if (i >= objPartType.size()) {
							throw new EugeneException("The "
									+ objPartType.getName()
									+ " part type does contain only "
									+ objPartType.size() + " properties!");
						}
						objProperty = objPartType.get(i);
						objPropertyValue = new PropertyValue(
								objProperty.getName(), objVariable.getType());
						objPropertyValue.setValue(objVariable);

					} else {
						throw new EugeneException("The " + objElement.getName()
								+ " element is not a valid property value!");
					}

					// compare both types...
					if (!objPropertyValue.getType().equals(
							objPartType.get(objProperty.getName()).getType())) {
						throw new EugeneException("The "
								+ objProperty.getName() + " property of the "
								+ objPartType.getName()
								+ " part type is not of type "
								+ objPropertyValue.getType() + "!");
					}

					lstPropertyValues.add(objPropertyValue);
				}
				i++;
			}
		}

		Part objPart = EugeneBuilder.buildPart(sName, lstPropertyValues,
				objPartType);
		
		SymbolTables.put(objPart);
		
		return objPart;
	}

	public PropertyValue createPropertyValue(String sName, NamedElement objValue)
			throws EugeneException, EugeneException {
		if (null == objValue) {
			throw new EugeneException(
					"I cannot assign a NULL value to a property!");
		}

		if (objValue instanceof Variable) {
			return EugeneBuilder.buildPropertyValue(sName, (Variable) objValue);
		} else if (!(objValue instanceof PropertyValue)) {
			throw new EugeneException("The " + objValue.getName()
					+ " element is not of an appropriate property value!");
		}

		return EugeneBuilder
				.buildPropertyValue(sName, (PropertyValue) objValue);
	}

	public Device createDevice(
			String sName, List<NamedElement> lstElements, char[] directions)
			throws EugeneException {
		
		if (SymbolTables.contains(sName)) {
			throw new EugeneException(
					"There exists already an element named " + sName);
		}

		/** COMPONENTS **/
		ArrayList<Component> lst = new ArrayList<Component>();
		List<Property> lstProperties = new ArrayList<Property>();
		if (null != lstElements && !lstElements.isEmpty()) {
			lst = new ArrayList<Component>(lstElements.size());
			for (NamedElement objElement : lstElements) {
				if (null == objElement) {
					throw new EugeneException("Something went wrong!");
				} else if(!(objElement instanceof Component) && 
						  !(objElement instanceof Property)) {
					throw new EugeneException(
							"I cannot add the " + objElement.getName() + " element to the " + sName + " device!");
				}
				
				if(objElement instanceof Component) {
					lst.add(((Component) objElement));
				} else if(objElement instanceof Property) {
					lstProperties.add((Property)objElement);
				}
			}
		}
		
		/** DIRECTIONS **/
		if(null == directions) {
			directions = new char[lst.size()];
			for(int i=0; i<directions.length; i++) {
				directions[i] = '+';
			}
		}
		
//		System.out.println("[Interp.createDevice] -> "+sName+" "+Arrays.toString(directions));
		Device objDevice = EugeneBuilder.buildDevice(sName, lst, lstProperties, directions);
//		System.out.println("[Interp.createDevice] -> "+objDevice);
		SymbolTables.put(objDevice);
		return objDevice;
	}

	public DeviceType createDeviceType(
			String sName, List<NamedElement> lstElements, char[] directions) 
					throws EugeneException {
		if (SymbolTables.contains(sName)) {
			throw new EugeneException("There exists already an element named "
					+ sName);
		}

		for (NamedElement objElement : lstElements) {
			if (null == objElement
					|| !(objElement instanceof Part
							|| objElement instanceof PartType
							|| objElement instanceof Device || objElement instanceof EugeneCollection)) {
				throw new EugeneException("I cannot add the "
						+ objElement.getName() + " element to the " + sName
						+ " device type!");
			}
		}

		DeviceType objDeviceType = EugeneBuilder.buildDeviceType(sName,
				lstElements);
		SymbolTables.put(objDeviceType);
		return objDeviceType;
	}

	public Rule createRule(
			String sName, Device device, Token token, CommonTree tree) 
					throws EugeneException {

		// check if Rule does not exist already
		if (null != sName && SymbolTables.contains(sName)) {
			throw new EugeneException(
					"There exists already an element named " + sName + "!");
		}

		Rule objRule = EugeneBuilder.buildRule(sName, device, token, tree);

		if(null != objRule) {
			SymbolTables.put(objRule);
		}
		
		return objRule;
	}

	public void createRelation(String lhs, String relation, String rhs) 
		throws EugeneException {
		
		// first, we need to check if lhs and rhs exist
		long lhsId = SymbolTables.getId(lhs);
		if(lhsId == -1) {
			throw new EugeneException("I don't know "+lhs);
		}
		long rhsId = SymbolTables.getId(rhs);
		if(rhsId == -1) {
			throw new EugeneException("I don't know "+rhs);
		}
				
		if(Relation.REPRESSES.toString().equals(relation)) {
			SymbolTables.put(new Represses(lhsId, rhsId));
		} else if(Relation.INDUCES.toString().equals(relation))  {
			SymbolTables.put(new Induces(lhsId, rhsId));
		} else if(Relation.DRIVES.toString().equals(relation))  {
			SymbolTables.put(new Drives(lhsId, rhsId));
		} else if(Relation.BINDS.toString().equals(relation))  {
			SymbolTables.put(new Binds(lhsId, rhsId));
		} else if(Relation.ORTHO.toString().equals(relation))  {
			SymbolTables.put(new Ortho(lhsId, rhsId));
		} else if(Relation.MATCHES.toString().equals(relation))  {
			SymbolTables.put(new Matches(lhsId, rhsId));
		}
	}
	
	public org.cidarlab.eugene.dom.collection.EugeneCollection createCollection(String sName,
			java.util.Collection<NamedElement> setElements)
			throws EugeneException {
		if (SymbolTables.contains(sName)) {
			throw new EugeneException("There exists already an element named "
					+ sName + "!");
		}

		// check if all elements of the setElements set are instances of the
		// CollectionElement class
		java.util.Collection<CollectionElement> colElements = (java.util.Collection<CollectionElement>) null;
		if (null != setElements && !setElements.isEmpty()) {

			colElements = new Vector<CollectionElement>(setElements.size());

			for (NamedElement objElement : setElements) {
				if (null == objElement) {
					throw new EugeneException(
							"I cannot add a NULL element to the " + sName
									+ " collection!");
				} else if (!(objElement instanceof CollectionElement)) {
					throw new EugeneException("The " + objElement.getName()
							+ " element cannot be added to the " + sName
							+ " collection!");
				}
				colElements.add((CollectionElement) objElement);
			}
		}

		// create an instance of the Collection class
		org.cidarlab.eugene.dom.collection.EugeneCollection objCollection = EugeneBuilder
				.buildCollection(sName, colElements);

		SymbolTables.put(objCollection);

		return objCollection;
	}

	public ComponentArray createArray(String sType, String sName)
			throws EugeneException {
//		System.out.println("[Interp.createArray] -> "+sType+" "+sName);
		
		// check if the sName is taken already
		if (null != SymbolTables.get(sName)) {
			throw new EugeneException(
					"There exists already an element named " + sName);
		}

		NamedElement element = null;
		
		ComponentArray objArray = (ComponentArray) null;
		if (EugeneConstants.DEVICEARRAY.equals(sType)) {
			// it's a device array
			objArray = new DeviceArray(sName);
			
		} else if(EugeneConstants.PARTARRAY.equals(sType)) {
			// it's a part array
			
		} else if(EugeneConstants.PARTTYPEARRAY.equals(sType)) {
			// it's a parttype array
			
		} else if(EugeneConstants.PROPERTYARRAY.equals(sType)) {
			// it's a property array
			
		} else if(EugeneConstants.RULEARRAY.equals(sType)) {
			// it's a rule array
			
		} else if(null != (element = (NamedElement)SymbolTables.get(sType))) {
			// it's an array of user defined parts
			if(element instanceof Part) {
				Part part = (Part)element;
				
				// TODO: create an array of the specified part
				
			} else {
				throw new EugeneException("I cannot create an array of type "+element.getName()+"!");
			}
		}

		if (null != objArray) {
			SymbolTables.put(objArray);
		}

		return objArray;
	}

	public void remove(String sElementName, NamedElement objIdx) 
			throws EugeneException {
		
		int idx = -1;
		if(null != objIdx && objIdx instanceof Variable) {
			try {
				idx = Integer.valueOf(((Variable)objIdx).getValue());
			} catch(Exception e) {
				throw new EugeneException(objIdx+" is an invalid index!");
			}
						
		} else {
			throw new EugeneException(objIdx+" is an invalid index!");
		}
		
		NamedElement objElement = null;
		if(null != sElementName && null != (objElement = SymbolTables.get(sElementName))) {
			
			// check if the element is of type array
			if(objElement instanceof ComponentArray) {

				((ComponentArray)objElement).remove(idx);
				
			} else if(objElement instanceof Variable && 
					(EugeneConstants.TXTLIST.equals(((Variable)objElement).getType()) || 
							EugeneConstants.NUMLIST.equals(((Variable)objElement).getType()))) {
				
			} else if(objElement instanceof Device) {
				((Device)objElement).remove(idx);
				//System.out.println("[Interp.remove] -> Device "+objElement+".remove("+idx+")");
			}
			 
			
		} else {
			throw new EugeneException("I cannot find "+sElementName+"!");
		}
		
	}
	
	/** ADD methods **/
	public void addProperty(String sComponent, String sProperty) 
			throws EugeneException {

		// first, get the property
		NamedElement objProperty = SymbolTables.get(sProperty);
		if(null != objProperty) {
			if(!(objProperty instanceof Property)) {
				throw new EugeneException(sProperty+" is not a Property!");
			}
		} else {
			throw new EugeneException("I don't know anything about "+sProperty);
		}

		// second, get the part/parttype
		NamedElement objComponent = SymbolTables.get(sComponent);
		if(null != objComponent) {
			if(objComponent instanceof Component) {
				((Component)objComponent).addProperty((Property)objProperty);
			} else {
				throw new EugeneException(sComponent+" is neither a Device, Part Type, nor a Part!");
			}
		} else {
			throw new EugeneException("I don't know anything about "+sComponent);
		}
	}
	
	public void addProperties(String sName, List<NamedElement> lstElements) 
			throws EugeneException {
		if(null == lstElements) {
			return;
		}
		
		// first, check if we've received properties
		List<Property> lstProperties = new ArrayList<Property>(lstElements.size());
		for(NamedElement element : lstElements) {
			if(!(element instanceof Property)) {
				throw new EugeneException(element.getName()+" is not a Property!");
			}
			lstProperties.add((Property)element);
		}
		
		// second, get the part/parttype
		NamedElement objComponent = SymbolTables.get(sName);
		if(null != objComponent) {
			if(objComponent instanceof Component) {				
				((Component)objComponent).addProperties((List<Property>)lstProperties);
			} else {
				throw new EugeneException(objComponent.getName()+" is neither a Device, Part Type, nor a Part!");
			}
		} else {
			throw new EugeneException("I don't know anything about "+sName);
		}
	}
	
	/**** ASSIGNMENTS ****/
	public void assign(CommonTree tree, NamedElement objAssignElement) 
			throws EugeneException {
		
//		System.out.println("[Interp.assign] -> "+tree.toStringTree()+" = "+objAssignElement);
		
		if(null != tree && null != objAssignElement) {
			
			NamedElement base_element = null;
			
			if(tree.getChildCount() > 0) {

				traverseAndAssign(tree, objAssignElement);

			} else {
				base_element = SymbolTables.get(tree.getText());
				if(null != base_element) {
					base_element.assign(objAssignElement);
				} else {
					throw new EugeneException("I don't know "+tree.getText());
				}				
			}
			
			/* finally, we need to update the base_element in the symbol-tables */
			SymbolTables.remove(base_element);
			SymbolTables.put(base_element);
			
		}

	}
	
	/*
	 * INORDER TRAVERSAL
	 * 
	 *             [
	 *         /       \
	 *       .           idx
	 *    /     \
	 *   p     propX
	 *         
	 */
//	private static NamedElement current_element;
//	private static NamedElement parent_element;
	
	private void traverseAndAssign(Tree tree, NamedElement objAssignElement) 
			throws EugeneException {
		/*
		 * let's go down to the left-most leaf
		 */
		if(null != tree) {
			
			/* first, we move left to the left-most leaf...
			 * i.e. the node that consists the element's name 
			 */
			Tree tmp = tree;
			while(tmp.getChildCount() > 0) {
				tmp = tmp.getChild(0);
			}

			// then, we retrieve the element from the symboltables
			NamedElement element = SymbolTables.get(tmp.getText());
				
			if(null == element) {
				throw new EugeneException("I don't know "+tree.getText());
			}
			
			
//			System.out.println("tmp.getText -> "+tmp.getText()+" -> "+tmp.getParent()+" -> "+tmp.getParent().getParent());
			
			/* 
			 * next, we move up the tree  
			 */
			while(!tmp.getParent().isNil() && !tmp.getParent().getParent().isNil()) {

				tmp = tmp.getParent();
//				System.out.println(" -> "+tmp.getText());
				
				if("[".equals(tmp.getText())) {
					int idx = Indexer.toIndex(tmp.getChild(1).getText());
					if(element instanceof Part) {
						element = ((Part)element).getPropertyValue(idx);
					} else {
						element = element.get(idx);
					}
				} else if(".".equals(tmp.getText())) {
					if(element instanceof Part) {
						element = ((Part)element).getPropertyValue(tmp.getChild(1).getText());
					} else {
						element = element.get(tmp.getChild(1).getText());
					}
				}

				if(element == null) {
					throw new EugeneException("Invalid assignment!");
				}
			}

			if(!tmp.getParent().isNil()) {
				tmp = tmp.getParent();
			}
			
			// ASSIGN
			if("[".equals(tmp.getText())) {
				int idx = Indexer.toIndex(tmp.getChild(1).getText());
				element.set(idx, objAssignElement);
			} else if(".".equals(tmp.getText())) {
				element.set(tmp.getChild(1).getText(), objAssignElement);
			}

		}
		
	}
	
	public NamedElement clone(NamedElement objElement) {
		if(null != objElement) {
			return this.cloner.deepClone(objElement);
		}
		return null;
	}
	
	public void assign(String sName, NamedElement objAssignElement)
			throws EugeneException {
		
		if(null == objAssignElement) {
			return;
		}
		
		// 1. lookup the sName element in the symbol tables
		NamedElement objElement = SymbolTables.get(sName);
		if(null == objElement) {
			objElement = cloner.deepClone(objAssignElement);
			objElement.setName(sName);
			SymbolTables.put(objElement);
		} else {
			objElement.assign(objAssignElement);
		}
		
	}

	// method: getPartVariable
	// description: this method returns a Property's value of a given PartType
	// instance
	// in: sInstanceName...the name of the PartType instance
	// sPropertyName...the name of the Property
	// out: the value of the Property, i.e. an object of the Variable class
	public Variable getPartPropertyValue(String sPartName, String sPropertyName) {
		NamedElement objElement = SymbolTables.get(sPartName);
		if (null == objElement || !(objElement instanceof Part)) {
			return null;
		}
		Part objPartType = (Part) objElement;
		Variable objVariable = objPartType.getPropertyValue(sPropertyName);
		return objVariable;
	}

	// ASSIGNMENT
	// ID = <expression>
	public void assignTo(String sLeftName, NamedElement objRightElement)
			throws EugeneException, EugeneException {

		// get the type of the assignment's left side (i.e. PropertyValue,
		// PartType, PartType Instance, ...)
		NamedElement objLeftElement = SymbolTables.get(sLeftName);
		String sLeftType = null;

		// get the type of the assignment's right side
		String sRightType = this.getElementType(objRightElement);

		if (null == objLeftElement) {
			sLeftType = this.getElementType(objRightElement);
		} else {
			sLeftType = this.getElementType(objLeftElement);
		}

		if (sRightType != null && sLeftType != null
				&& sLeftType.equals(sRightType)) {
			if (EugeneConstants.DEVICE.equals(sLeftType)) {
				Device objDevice = new Device(sLeftName);
				objDevice.assign((Device) objRightElement);

				if (SymbolTables.contains(objDevice.getName())) {
					SymbolTables.remove(objDevice);
				}
				SymbolTables.put(objDevice);
			} else if (EugeneConstants.PARTTYPE.equals(sLeftType)) {
				PartType objPartType = new PartType(sLeftName);
				objPartType.assign((PartType) objRightElement);
				if (SymbolTables.contains(objPartType.getName())) {
					SymbolTables.remove(objPartType);
				}
				SymbolTables.put(objPartType);
			} else if (EugeneConstants.PART.equals(sLeftType)) {

				Part objRightPart = (Part) objRightElement;

				Part objPart = new Part(objRightPart.getPartType(), sLeftName);
				objPart.assign(objRightPart);

				if (SymbolTables.contains(objPart.getName())) {
					SymbolTables.remove(objPart);
				}
				SymbolTables.put(objPart);
			} else if (EugeneConstants.PROPERTY.equals(sLeftType)) {
				Property objProperty = new Property(sLeftName);
				objProperty.assign((Property) objRightElement);
				if (!SymbolTables.contains(objProperty.getName())) {
					SymbolTables.remove(objProperty);
				}
				SymbolTables.put(objProperty);
			} else if (EugeneConstants.VARIABLE.equals(sLeftType)) {
				Variable objRightVariable = (Variable) objRightElement;
				Variable objLeftVariable = (Variable) objLeftElement;
				if (objLeftVariable == null) {
					// create new variable
					objLeftVariable = new Variable(sLeftName,
							objRightVariable.getType());
					objLeftVariable.setValue(objRightVariable);

					// and put it into the symbol tables
					SymbolTables.put(objLeftVariable);
				}

				// compare the types of both variables
				if (objLeftVariable.getType()
						.equals(objRightVariable.getType())) {
					objLeftVariable.setValue(objRightVariable);
				} else {
					throw new EugeneException(
							objLeftVariable.getName() + " is not of type "
									+ objRightVariable.getType());
				}
				if (SymbolTables.contains(objLeftVariable.getName())) {
					SymbolTables.remove(objLeftVariable);
				}
				SymbolTables.put(objLeftVariable);
			} else if (EugeneConstants.DEVICEARRAY.equals(sLeftType)) {

				DeviceArray objDeviceArray = new DeviceArray(sLeftName);
				objDeviceArray.setDeviceNames(new ArrayList<String>(
						((DeviceArray) objRightElement).getDeviceNames()));
				SymbolTables.put(objDeviceArray);

			} else if (EugeneConstants.PARTTYPEARRAY.equals(sLeftType)) {
				PartTypeArray objPartTypeArray = new PartTypeArray(sLeftName);

				ArrayList<PartType> lstPartTypes = ((PartTypeArray) objRightElement)
						.getPartTypes();
				for (int i = 0; i < lstPartTypes.size(); i++) {
					objPartTypeArray.add(lstPartTypes.get(i));
				}

				SymbolTables.put(objPartTypeArray);
			} else if (EugeneConstants.PARTARRAY.equals(sLeftType)) {
				PartArray objPartArray = new PartArray(sLeftName);

				ArrayList<Part> lstParts = ((PartArray) objRightElement)
						.getParts();
				for (int i = 0; i < lstParts.size(); i++) {
					objPartArray.add(lstParts.get(i));
				}

				SymbolTables.put(objPartArray);
			} else if (EugeneConstants.PROPERTYARRAY.equals(sLeftType)) {
				PropertyArray objPropertyArray = new PropertyArray(sLeftName);

				ArrayList<Property> lstProperties = ((PropertyArray) objRightElement)
						.getProperties();
				for (int i = 0; i < lstProperties.size(); i++) {
					objPropertyArray.add(lstProperties.get(i));
				}

				SymbolTables.put(objPropertyArray);
			} else if (EugeneConstants.RULE.equals(sLeftType)) {
				throw new EugeneException(
						"We are currently working on the assignment of Rules");
			}
		} else {
			throw new EugeneException("I cannot assign the "
					+ objRightElement + " element to " + sLeftName);
		}
	}

	// <component>.<element> = <element>
	public void assignTo(String sLeftComponent, String sLeftElement,
			NamedElement objRightElement)
			throws EugeneException {

		NamedElement objLeftComponent = SymbolTables.get(sLeftComponent);
		if (null == objLeftComponent) {
			throw new EugeneException("I don't know "
					+ sLeftComponent);
		} else if (objLeftComponent instanceof Device) {
			// the left side's element must be a PartType or PartType Instance
			throw new EugeneException(
					"We are currently working on the assignment to PartTypes or PartType Instances of Devices!");
		} else if (objLeftComponent instanceof Part) {
			// the left side's element must be a Property
			Part objLeftPart = (Part) objLeftComponent;

			PartType objPartType = objLeftPart.getPartType();

			Property objLeftProperty = objPartType.getProperty(sLeftElement);
			if (null == objLeftProperty) {
				throw new EugeneException("The "
						+ objPartType.getName()
						+ " Part Type does not contain a Property named "
						+ sLeftElement);
			}

			if (objRightElement instanceof Variable) {
				Variable objRightVariable = (Variable) objRightElement;

				// finally, compare both types
				if (!objRightVariable.getType().equals(
						objLeftProperty.getType())) {
					throw new EugeneException(
							"Invalid assignment to Property "
									+ objLeftProperty.getName()
									+ " of PartType Instance "
									+ objLeftPart.getName() + " => "
									+ objLeftProperty.getType() + " != "
									+ objRightVariable.getType());
				}

				objLeftPart.setPropertyValue(objLeftProperty.getName(),
						((Variable) objRightElement)
								.toPropertyValue(objLeftPart));

			} else {
				throw new EugeneException(sLeftComponent + "."
						+ sLeftElement + " = " + objRightElement
						+ " is an invalid assignment!");
			}
		}
	}

	public Variable assignVariable(String sName, Variable objRightVariable) {
		Variable objLeftVariable = new Variable(sName,
				objRightVariable.getType());
		objLeftVariable.setValue(objRightVariable);
		return objLeftVariable;
	}

	public PropertyValue assignPropertyValue(
			PropertyValue objLeftPropertyValue,
			PropertyValue objRightPropertyValue) {
		objLeftPropertyValue.setValue(objRightPropertyValue);
		return objLeftPropertyValue;
	}

	private String getElementType(NamedElement objElement) {
		if (objElement instanceof Device) {
			return EugeneConstants.DEVICE;
		} else if (objElement instanceof DeviceArray) {
			return EugeneConstants.DEVICEARRAY;
		} else if (objElement instanceof PartType) {
			return EugeneConstants.PARTTYPE;
		} else if (objElement instanceof PartTypeArray) {
			return EugeneConstants.PARTTYPEARRAY;
		} else if (objElement instanceof Part) {
			return EugeneConstants.PART;
		} else if (objElement instanceof PartArray) {
			return EugeneConstants.PARTARRAY;
		} else if (objElement instanceof Property) {
			return EugeneConstants.PROPERTY;
		} else if (objElement instanceof PropertyArray) {
			return EugeneConstants.PROPERTYARRAY;
		} else if (objElement instanceof Variable) {
			return EugeneConstants.VARIABLE;
		} else if (objElement instanceof Rule) {
			return EugeneConstants.RULE;
		}
		return null;
	}

	public String getVariable(Variable objVariable) {
		if (null != objVariable) {
			if (EugeneConstants.TXT.equals(objVariable.getType())) {
				return objVariable.getTxt();
			} else if (EugeneConstants.TXTLIST.equals(objVariable.getType())) {
				return objVariable.getTxtList().toString();
			} else if (EugeneConstants.NUM.equals(objVariable.getType())) {
				return new Double(objVariable.getNum()).toString();
			} else if (EugeneConstants.NUMLIST.equals(objVariable.getType())) {
				return objVariable.getNumList().toString();
			} else if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				return new Boolean(objVariable.getBoolean()).toString();
			}
		}
		return null;
	}

	// <element> + <element>
	public NamedElement add(NamedElement leftElement, NamedElement rightElement) 
			throws EugeneException {
		
//		System.err.println("[interp.add] -> "+leftElement+" + "+rightElement);
		
		if(leftElement==null) {
            return rightElement;
        } else if(rightElement==null) {
            return leftElement;
        } else if(leftElement instanceof Component && rightElement instanceof Component) { 
        	
        	if(leftElement instanceof Device) {        		
	        	List<Component> lst = 
	        			new ArrayList<Component>(((Device)leftElement).getComponents());
        		char[] directions = ((Device)leftElement).getDirections();
        		
	        	if(rightElement instanceof Device) {
    	    	    // <device> + <device> -> <device>            	
		        	lst.addAll(((Device)rightElement).getComponents());
		        	
		        	directions = ArrayUtils.addAll(directions, 
		        			((Device)rightElement).getDirections());
        		} else if(rightElement instanceof PartType ||
        				rightElement instanceof Part) {
    	    	    // <device> + [<part type> | <port>] -> <device>            	
        			lst.add((Component)rightElement);
        		}
        		
        		return EugeneBuilder.buildDevice(
						(String)null, 
						lst,
						directions);        		
        	} else if(leftElement instanceof PartType || 
        			leftElement instanceof Part) {        		

            	List<Component> lst = new ArrayList<Component>();
    	    	lst.add((Component)leftElement);
    	    	char[] directions = new char[1];
    	    	directions[0] = '+';
    	    	
        		if(rightElement instanceof PartType ||
        				rightElement instanceof Part) {
	    		    // <PartType> + <PartType> -> <Device>
	    	    	lst.add((Component)rightElement);
	    	    	directions = ArrayUtils.add(directions, '+');
        		} else if (rightElement instanceof Device) {
        			lst.addAll(((Device)rightElement).getComponents());
	    	    	directions = ArrayUtils.addAll(
	    	    			directions, ((Device)rightElement).getDirections());
        		}

        		return EugeneBuilder.buildDevice(
        				(String)null, 
        				lst,
        				directions);
    	    }        	
	    } else if(leftElement instanceof Rule && rightElement instanceof Rule) {
		    // <rule> + <rule>
	    } else if(leftElement instanceof Variable && rightElement instanceof Variable) {
		    // <variable> + <variable>
	    	return ((Variable)leftElement).add((Variable)rightElement);
//	    } else if(leftElement instanceof EugeneCollection && rightElement instanceof EugeneCollection) {
//		    // <collection> + <collection>
//	        return CollectionOps.union((EugeneCollection)leftElement, (EugeneCollection)rightElement);
	    } else if(leftElement instanceof DeviceArray) {
	    	
	    	if(leftElement instanceof GeneratedDeviceArray &&
	    			rightElement instanceof GeneratedDeviceArray) {
	    		
		    	/*
		    	 * concatenation of two device arrays 
		    	 * being generated by Eugene's product() or permute() function
		    	 */
	    		return EugeneBuilder.unionDeviceArrays((GeneratedDeviceArray)leftElement, 
	    				(GeneratedDeviceArray)rightElement);
	    		
	    	} else if(rightElement instanceof Device) {
		    		DeviceArray x = new DeviceArray(null);
		    		x.addAll((DeviceArray)leftElement);
		    		try {
						x.add((Device)rightElement);
					} catch (EugeneException e) {
						throw new EugeneException("[Interp.add] "+e.getMessage());
					}
		    		return x;
		    } else if (rightElement instanceof DeviceArray) {
		    		((DeviceArray)leftElement).addAll((DeviceArray)rightElement);
		    		return ((DeviceArray)leftElement);
		    }
	    }
		
		throw new EugeneException("I cannot add "+leftElement.getName()+" and "+rightElement.getName());
	}

	// <element> - <element>
	public NamedElement subtract(NamedElement leftElement,
			NamedElement rightElement) {
		if (leftElement == null) {
			return rightElement;
		} else if (rightElement == null) {
			return leftElement;

		} else if (leftElement instanceof Device
				&& rightElement instanceof Device) {
			// <device> - <device>
		} else if (leftElement instanceof PartType
				&& rightElement instanceof PartType) {
			// <PartType> - <PartType>
		} else if (leftElement instanceof Part && rightElement instanceof Part) {
			// <PartType instance> - <PartType instance>
		} else if (leftElement instanceof Rule && rightElement instanceof Rule) {
			// <rule> - <rule>
		} else if (leftElement instanceof Variable
				&& rightElement instanceof Variable) {
			// <property value> - <property value>
			return subtractVariables((Variable) leftElement,
					(Variable) rightElement);
		} else if (leftElement instanceof EugeneCollection
				&& rightElement instanceof EugeneCollection) {
			// <collection> / <collection>
			return CollectionOps.minus((EugeneCollection) leftElement,
					(EugeneCollection) rightElement);
		}

		return null;
	}

	public Variable subtractVariables(Variable val1, Variable val2) {
		Variable retVal = null;
		if (EugeneConstants.NUM.equals(val1.getType())
				&& EugeneConstants.NUM.equals(val2.getType())) {

			retVal = new Variable(null, EugeneConstants.NUM);
			retVal.setNum(val1.getNum() - val2.getNum());
		}
		return retVal;
	}

	// <element> * <element>
	public NamedElement multiply(NamedElement leftElement,
			NamedElement rightElement) {
		if (leftElement == null) {
			return rightElement;
		} else if (rightElement == null) {
			return leftElement;
		} else if (leftElement instanceof Variable
				&& rightElement instanceof Variable) {
			Variable objLeft = (Variable) leftElement;
			Variable objRight = (Variable) rightElement;
			if (EugeneConstants.NUM.equals(objLeft.getType())
					&& EugeneConstants.NUM.equals(objRight.getType())) {
				Variable objRetVal = new Variable("MULTIPLY-RESULT",
						EugeneConstants.NUM);
				objRetVal.setNum(objLeft.getNum() * objRight.getNum());
				return objRetVal;
			}
		}

		return null;
	}

	// <element> / <element>
	public NamedElement divide(NamedElement leftElement,
			NamedElement rightElement) {
		if (leftElement == null) {
			return rightElement;
		} else if (rightElement == null) {
			return leftElement;
		} else if (leftElement instanceof Variable
				&& rightElement instanceof Variable) {
			Variable objLeft = (Variable) leftElement;
			Variable objRight = (Variable) rightElement;
			if (EugeneConstants.NUM.equals(objLeft.getType())
					&& EugeneConstants.NUM.equals(objRight.getType())) {
				Variable objRetVal = new Variable("DIVISION-RESULT",
						EugeneConstants.NUM);
				objRetVal.setNum(objLeft.getNum() / objRight.getNum());
				return objRetVal;
			}
		}
		return null;
	}

	public boolean and(NamedElement leftElement, NamedElement rightElement)
			throws EugeneException {
		boolean bLeft = false, bRight = false;

		// evaluate the left element
		if (leftElement instanceof Rule) {
			bLeft = org.cidarlab.eugene.rules.RuleEngine.evaluateIfRule((Rule) leftElement);
		} else if (leftElement instanceof Variable) {
			Variable objVariable = (Variable) leftElement;
			if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				bLeft = objVariable.getBoolean();
			} else {
				throw new EugeneException("The value of "
						+ leftElement.getName()
						+ " at the left side is not of type BOOLEAN!");
			}
		} else {
			throw new EugeneException(
					"Cannot apply the AND operator, because the element at the left side "
							+ leftElement.getName()
							+ " is not a Rule, nor a Variable!");
		}

		// evaluate the right element
		if (rightElement instanceof Rule) {
			bRight = org.cidarlab.eugene.rules.RuleEngine.evaluateIfRule((Rule) rightElement);
		} else if (rightElement instanceof Variable) {
			Variable objVariable = (Variable) rightElement;
			if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				bRight = objVariable.getBoolean();
			} else {
				throw new EugeneException("The value of "
						+ rightElement.getName()
						+ " at the right side is not of type BOOLEAN!");
			}
		} else {
			throw new EugeneException(
					"Cannot apply the AND operator, because the element at the right side "
							+ rightElement.getName()
							+ " is not a Rule nor a Variable!");
		}

		return bLeft & bRight;
	}

	// <element> OR <element>
	public boolean or(NamedElement leftElement, NamedElement rightElement)
			throws EugeneException {
		boolean bLeft = false, bRight = false;

		// evaluate the left element
		if (leftElement instanceof Rule) {
			bLeft = org.cidarlab.eugene.rules.RuleEngine.evaluateIfRule((Rule) leftElement);
		} else if (leftElement instanceof Variable) {
			Variable objVariable = (Variable) leftElement;
			if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				bLeft = objVariable.getBoolean();
			} else {
				throw new EugeneException("The value of "
						+ leftElement.getName()
						+ " at the left side is not of type BOOLEAN!");
			}
		} else {
			throw new EugeneException(
					"Cannot apply the OR operator, because the element at the left side "
							+ leftElement.getName()
							+ " is not a Rule nor a Variable");
		}

		// evaluate the right element
		if (rightElement instanceof Rule) {
			bRight = org.cidarlab.eugene.rules.RuleEngine.evaluateIfRule((Rule) rightElement);
		} else if (rightElement instanceof Variable) {
			Variable objVariable = (Variable) rightElement;
			if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				bRight = objVariable.getBoolean();
			} else {
				throw new EugeneException("The value of "
						+ rightElement.getName()
						+ " at the right side is not of type BOOLEAN!");
			}
		} else {
			throw new EugeneException(
					"Cannot apply the OR operator, because the element at the right side "
							+ rightElement.getName()
							+ " is not a Rule nor a Variable!");
		}

		return bLeft | bRight;
	}

	// <element> XOR <element>
	public boolean xor(NamedElement leftElement, NamedElement rightElement)
			throws EugeneException {
		boolean bLeft = false, bRight = false;

		// evaluate the left element
		if (leftElement instanceof Rule) {
			bLeft = org.cidarlab.eugene.rules.RuleEngine.evaluateIfRule((Rule) leftElement);
		} else if (leftElement instanceof Variable) {
			Variable objVariable = (Variable) leftElement;
			if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				bLeft = objVariable.getBoolean();
			} else {
				throw new EugeneException("The value of "
						+ leftElement.getName()
						+ " at the left side is not of type BOOLEAN!");
			}
		} else {
			throw new EugeneException(
					"Cannot apply the XOR operator, because the element at the left side "
							+ leftElement.getName()
							+ " is not a Rule nor a Variable!");
		}

		// evaluate the right element
		if (rightElement instanceof Rule) {
			bRight = org.cidarlab.eugene.rules.RuleEngine.evaluateIfRule((Rule) rightElement);
		} else if (rightElement instanceof Variable) {
			Variable objVariable = (Variable) rightElement;
			if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				bRight = objVariable.getBoolean();
			} else {
				throw new EugeneException("The value of "
						+ rightElement.getName()
						+ " at the right side is not of type BOOLEAN!");
			}
		} else {
			throw new EugeneException(
					"Cannot apply the XOR operator, because the element at the right side "
							+ rightElement.getName()
							+ " is not a Rule nor a Variable!");
		}

		return bLeft ^ bRight;
	}

	// <element> NOT <element>
	public NamedElement not(NamedElement objElement) {
		if (objElement instanceof Variable) {
			Variable objVariable = (Variable) objElement;
			if (EugeneConstants.BOOLEAN.equals(objVariable.getType())) {
				objVariable.setBoolean(!objVariable.getBoolean());
				return objVariable;
			}
		}
		return null;
	}

	public boolean isDevice(String sDeviceName) {
		NamedElement objElement = SymbolTables.get(sDeviceName);
		if (objElement != null && objElement instanceof Device) {
			return true;
		}
		return false;
	}

	public boolean isFunction(String sFunctionName) {
		NamedElement objElement = SymbolTables.get(sFunctionName);
		if (objElement != null && objElement instanceof Function) {
			return true;
		}
		return false;
	}

	public boolean isPartType(String sPartTypeName) {
		NamedElement objElement = SymbolTables.get(sPartTypeName);
		if (objElement != null && objElement instanceof PartType) {
			return true;
		}
		return false;
	}

	public boolean isPart(String sPartName) {
		NamedElement objElement = SymbolTables.get(sPartName);
		if (objElement != null && objElement instanceof Part) {
			return true;
		}
		return false;
	}

	/*** get wrapper ***/
	public NamedElement get(String sName) {
//		System.out.println("[Interp.get] -> "+sName);
		if (null != sName) {
			return SymbolTables.get(sName);
		}
		return (NamedElement) null;
	}

	public NamedElement get(NamedElement objElement, NamedElement objIdx)
			throws EugeneException {
//		System.out.println("[Interp.get] -> "+objElement.getName()+", ids: "+objIdx);
		if (objIdx instanceof Variable) {

		}

		throw new EugeneException("Invalid use of the get() function!");
	}

	// <objElement> . get ( <sName> )
	// e.g. device.get("promoter")
	public NamedElement get(NamedElement objElement, String sName)
			throws EugeneException {
		
		if (null == objElement) {
			throw new EugeneException("Invalid use of the get() function!");
		} else if(objElement instanceof Variable) {
			Variable objVar = (Variable)objElement;
			return objVar.get(sName);
		} else if (!(objElement instanceof Component)
				&& !(objElement instanceof ComponentArray)
				&& !(objElement instanceof org.cidarlab.eugene.dom.collection.EugeneCollection)) {
			throw new EugeneException(
					"INVALID! " + objElement.getName() + " is not a Component, Array, nor Collection!");
		}

		if (null == sName) {
			throw new EugeneException("INVALID!");
		}

/**
		NamedElement obj = SymbolTables.get(sName);
		if (null != obj) {
			System.out.println("[Interp.get] "+objElement.getName()+".get("+sName+")");
			if (obj instanceof Variable) {
				Variable objVar = (Variable) obj;
				if (EugeneConstants.NUM.equals(objVar.getType())) {
					return this.get(objElement, (int) objVar.getNum());
				} else if (EugeneConstants.TXT.equals(objVar.getType())) {
					return this.get(objElement, objVar.getTxt());
				}
			}
		}
 **/		
//		System.out.println("[Interp.get] "+objElement.getName()+".get("+sName+")");
		NamedElement retElement = objElement.get(sName);
//		System.out.println("[Interp.get] "+objElement.getName()+".get("+sName+") -> returning "+retElement);
		return retElement;
	}

	public NamedElement get(String sName, String sElementName)
			throws EugeneException {

		if (null != sName && !sName.isEmpty() && null != sElementName
				&& !sElementName.isEmpty()) {
			NamedElement objElement = SymbolTables.get(sName);
			if (null == objElement) {
				throw new EugeneException("I don't know anything about "
						+ sName + "!");
			}
			return this.get(objElement, sElementName);
		}
		return (NamedElement) null;
	}

	// <objElement> . get ( <objIdx> )
	// e.g. array.get(1);
	public NamedElement get(NamedElement objElement, double idx)
			throws EugeneException {
		if (null == objElement) {
			throw new EugeneException("Invalid use of the get() function!");
		} else if(objElement instanceof Variable) {
			Variable objVar = (Variable)objElement;
			return objVar.get((int)idx);
		} else if (!(objElement instanceof Component)
				&& !(objElement instanceof ComponentArray)
				&& !(objElement instanceof EugeneCollection)) {
			throw new EugeneException(
					"The get() function is not available on the "
							+ objElement.getName() + " element!");
		}

		return objElement.get((int) idx);
	}

	public NamedElement get(String sName, double idx) 
				throws EugeneException {
//		System.out.println("[Interp.get] "+sName+" ["+idx+"]");
		
		if (null != sName && !sName.isEmpty() && idx >= 0) {
			NamedElement objElement = SymbolTables.get(sName);
			if (null == objElement) {
				throw new EugeneException("I don't know anything about "
						+ sName + "!");
			}
			return this.get(objElement, idx);
		}
		return (NamedElement) null;
	}

	/**** RULE EVALUATION METHODS ****/
	public void evaluateRules(
			NamedElement objDevice, List<NamedElement> lst, boolean bAssert)
			throws EugeneException {

		if (null != objDevice && !(objDevice instanceof Device)) {
			throw new EugeneException(
					"The " + objDevice.getName() + " element is not a device!");
		}

		long[] deviceIds = null;
		if (null == objDevice) {
			deviceIds = SymbolTables.getDeviceIds();
		} else {
			deviceIds = new long[1];
			deviceIds[0] = SymbolTables.getId(objDevice.getName());
		}

		List<Rule> lstRules = new ArrayList<Rule>();
		if (null == lst) {
			lstRules = new ArrayList<Rule>(SymbolTables.getRules());
		} else {
			for(NamedElement element : lst) {
				if (null != element && !(element instanceof Rule)) {
					throw new EugeneException(
							"The " + element.getName() + " element is not a rule!");
				} else {
					lstRules.add((Rule)element);
				}
			}
		}

//		System.out.println(lstRules);
//		System.out.println(ArrayUtils.toString(deviceIds));

		
		RuleMemo memo = org.cidarlab.eugene.rules.RuleEngine.evaluateRulesOnDevices(lstRules, deviceIds);
		List<String> lstViolations = memo.getViolations();
		if(!memo.getViolations().isEmpty()) {
			for(String s : lstViolations) {
				System.err.println(s);
			}
			
			if(bAssert) {
				throw new EugeneException("Rule Violations!");
			}
		}
	}

	/*** PRODUCT/PERMUTE **/
	public DeviceArray generateDevices(String sFunction,
			NamedElement objElement, NamedElement objCap, String sDegree)
			throws Exception {

		if (objElement instanceof Device) {
			return this.generateDevices(
					sFunction, (Device) objElement, objCap, sDegree);
		} else if (objElement instanceof org.cidarlab.eugene.dom.collection.EugeneCollection) {
			return this.generateDevicesFromCollection(sFunction,
					(EugeneCollection) objElement, objCap, sDegree);
		}
		throw new EugeneException("The " + objElement.getName()
				+ " element is not a Device nor a Collection!");
	}

	private DeviceArray generateDevicesFromCollection(String sFunction,
			EugeneCollection objCollection, NamedElement objCap, String sDegree)
			throws Exception {

		DeviceArray objDeviceArray = null;
		if (EugeneConstants.PERMUTE.equals(sFunction)) {
			objDeviceArray = new DeviceArray(objCollection.getName()
					+ "_PERMUTE");
		} else {
			objDeviceArray = new DeviceArray(objCollection.getName()
					+ "_PRODUCT");
		}

		// todo: generate devices out of the collection's elements
		for (Device d : objCollection.getDevices()) {
			objDeviceArray.addAll(this.generateDevices(sFunction, d, objCap,
					sDegree));
		}

		return objDeviceArray;
	}

	public DeviceArray generateDevices(
			String sFunction, String sDeviceName, String sRules, NamedElement objCap, NamedElement objDepth) 
					throws EugeneException {
		
		int nCap = -1;
		if (null != objCap && objCap instanceof Variable) {
			Variable objVar = (Variable) objCap;
			if (!EugeneConstants.NUM.equals(objVar.getType())) {
				throw new EugeneException(
						objVar.toString() + " is not a numeric value!");
			}
			try {
				nCap = (int) objVar.getNum();
			} catch (NumberFormatException nfe) {
				throw new EugeneException(
						objVar.getValue() + " is not a valid decimal number!");
			}
		}

		if(EugeneConstants.PERMUTE.equalsIgnoreCase(sFunction)) {
			return Permutor.permute(sDeviceName, nCap);
		} else if(EugeneConstants.PRODUCT.equalsIgnoreCase(sFunction)) {
			return Product.product(sDeviceName, nCap);
		}
		
		return null;
	}
	
	public DeviceArray generateDevices(
			String sFunction, NamedElement objElement, String sRules, NamedElement objCap, NamedElement objDepth) 
					throws Exception {

		// function
		if(null == sFunction || 
				(!EugeneConstants.PERMUTE.equals(sFunction) && !EugeneConstants.PRODUCT.equals(sFunction))) {
			throw new EugeneException(sFunction+" is an invalid function!");
		}
		
		// objElement
		Device objDevice = null;
		if(null != objElement) {
			if(objElement instanceof Device) {
				objDevice = (Device)objElement;
			} else {
				throw new EugeneException(objElement.getName()+" is not a Device!");
			}
		} else {
			throw new EugeneException("Invalid Device!");
		}
		
		// sRules

		// sDepth
		int nDepth = -1;
		if (null != objDepth && objDepth instanceof Variable) {
			Variable objVar = (Variable) objDepth;
			if (!EugeneConstants.NUM.equals(objVar.getType())) {
				throw new EugeneException(
						objVar.toString() + " is not a numeric value!");
			}
			try {
				nDepth = (int) objVar.getNum();
			} catch (NumberFormatException nfe) {
				throw new EugeneException(
						objVar.getValue() + " is not a valid decimal number!");
			}
			
			if(nDepth<0 || nDepth>objDevice.getMaxDepth()) {
				throw new EugeneException (nDepth+" is too deep! check the device again!");
			}
		}
		
		// objValue
		int nCap = -1;
		if (null != objCap && objCap instanceof Variable) {
			Variable objVar = (Variable) objCap;
			if (!EugeneConstants.NUM.equals(objVar.getType())) {
				throw new EugeneException(
						objVar.toString() + " is not a numeric value!");
			}
			try {
				nCap = (int) objVar.getNum();
			} catch (NumberFormatException nfe) {
				throw new EugeneException(
						objVar.getValue() + " is not a valid decimal number!");
			}
		}


		List<Component> lstComponents = new ArrayList<Component>();

		if(nDepth == -1) {
			lstComponents = objDevice.getAllComponents();
		} else {
			lstComponents = objDevice.getComponentsAtDepth(nDepth);
		}
		
		
		// if degree token = null, set to strict. else string degree =
		// degreeToken.text
		List<Device> lstDevices = null;
		if (null != sRules && EugeneConstants.STRICT.equals(sRules.toLowerCase())) {
			lstDevices = DeviceFactory.generateDevices(sFunction, 
					lstComponents, SymbolTables.getRules(), nCap, objDevice.getName());
		} else {
			lstDevices = DeviceFactory.generateDevices(sFunction,
					lstComponents, (java.util.Collection<Rule>) null, nCap, objDevice.getName());
		}
		
		DeviceArray objDeviceArray = EugeneBuilder.buildDeviceArray(
				objDevice.getName() + "_" + sFunction.toUpperCase(), 
				lstDevices);

		return objDeviceArray;
	}

	/****
	 * // product(Device, Rules, N, strict/flexible) public DeviceArray
	 * product(NamedElement objElement, NamedElement objCap, String sDegree)
	 * throws Exception {
	 * 
	 * if(null == objElement) { throw new
	 * EugeneException("Invalid element to produce devices!"); }
	 * 
	 * //System.out.println("[Interp.product] -> "+objElement.getName()+", "+
	 * objCap+", "+sDegree);
	 * 
	 * if(!(objElement instanceof Device)) { throw new EugeneException("The " +
	 * objElement.getName() + " element is not a Device!"); } Device objDevice =
	 * (Device)objElement;
	 * 
	 * int nCap = -1; if(null != objCap && objCap instanceof Variable) {
	 * Variable objVar = (Variable)objCap;
	 * if(!EugeneConstants.NUM.equals(objVar.getType())) { throw new
	 * EugeneException(objVar.toString()+" is not a numeric value!"); } try {
	 * nCap = (int)objVar.getNum(); } catch(NumberFormatException nfe) { throw
	 * new EugeneException(objVar.getValue()+" is not a valid decimal number!");
	 * } }
	 * 
	 * DeviceArray objDeviceArray = (DeviceArray)null;
	 * 
	 * //if degree token = null, set to strict. else string degree =
	 * degreeToken.text if(null!=sDegree &&
	 * EugeneConstants.STRICT.equals(sDegree.toLowerCase())) { objDeviceArray =
	 * DeviceFactory.product(objDevice, nCap, SymbolTables.getRules()); } else {
	 * objDeviceArray = DeviceFactory.product(objDevice, nCap,
	 * (java.util.Collection<Rule>)null); }
	 * 
	 * return objDeviceArray; }
	 ****/

	
	/*** WRAPPER STATEMENTS ***/
	public Device getDeviceDepth(String sDeviceName, NamedElement objDepth) 
			throws EugeneException {
		
		Device objDevice = null;
		
		if(null != sDeviceName && !sDeviceName.isEmpty()) {
			NamedElement objElement = SymbolTables.get(sDeviceName);
			if(null != objElement) {
				if(objElement instanceof Device) {
					objDevice = (Device)objElement;
				} else {
					throw new EugeneException(sDeviceName+" is not a device!");
				}
			} else {
				throw new EugeneException("I don't know anything about "+sDeviceName+"!");
			}			
		}
		
		int nDepth = -1;
		if(null != objDepth) {
			
			if(objDepth instanceof Variable) {
				if(EugeneConstants.NUM.equals(((Variable)objDepth).getType())) {
					nDepth = (int)((Variable)objDepth).getNum();
				} else {
					throw new EugeneException(objDepth.getName() + " is not a NUM value!");
				}
			} else {
				throw new EugeneException("Invalid depth!");
			}
			
			if(nDepth<0 || nDepth>objDevice.getMaxDepth()) {
				throw new EugeneException(nDepth+" is too deep! please check the device again!");
			}
		} else {
			throw new EugeneException("Invalid depth!");
		}
		
		if(nDepth < 1) {
			return objDevice;
		}
		
//		System.out.println("[Interp.getDeviceDept] "+objDevice.getName()+".getDirectionsAtDepth("+nDepth+") -> "+
//				Arrays.toString(objDevice.getDirectionsAtDepth(nDepth)));
		
		Device d = EugeneBuilder.buildDevice(
				null, 
				objDevice.getComponentsAtDepth(nDepth),
				objDevice.getDirectionsAtDepth(nDepth)); 
		
//		System.out.println("[Interp.getDeviceDept] "+d.getName()+" -> directions: "+
//				Arrays.toString(d.getDirections()));
		
		return d;
	}
			
	public Variable getDeviceMaxDepth(String sDeviceName) 
			throws EugeneException {
		Device objDevice = null;
		
		if(null != sDeviceName && !sDeviceName.isEmpty()) {
			NamedElement objElement = SymbolTables.get(sDeviceName);
			if(null != objElement) {
				if(objElement instanceof Device) {
					objDevice = (Device)objElement;
				} else {
					throw new EugeneException(sDeviceName+" is not a device!");
				}
			} else {
				throw new EugeneException("I don't know anything about "+sDeviceName+"!");
			}			
		}

		Variable objVariable = EugeneBuilder.buildVariable(null, EugeneConstants.NUM);
		objVariable.setNum(objDevice.getMaxDepth());
		return objVariable;
	}
	
	public double sizeOf(NamedElement objElement) 
			throws EugeneException {
		if(null != objElement) {
			//System.out.println("[Interp.sizeOf] -> "+objElement.getName()+" -> "+objElement.size());
			return objElement.size();
		}
		return -1;
	}
	
	public double sizeOf(String sName) 
				throws EugeneException {

		NamedElement objElement = SymbolTables.get(sName);
		if (null == objElement) {
			throw new EugeneException(" => I don't know anything about " + sName);
		}

		if (objElement instanceof Component) {
			return ((Component) objElement).size();
		} else if (objElement instanceof ComponentArray) {
			return ((ComponentArray) objElement).size();
		} else if (objElement instanceof EugeneCollection) {
			return ((EugeneCollection) objElement).size();
		} else if (objElement instanceof Variable) {
			Variable objVar = (Variable)objElement;
			if(EugeneConstants.TXT.equals(objVar.getType())) {
				return objVar.getTxt().length();
			} else if(EugeneConstants.NUMLIST.equals(objVar.getType())) {
				return objVar.getNumList().size();
			} else if(EugeneConstants.TXTLIST.equals(objVar.getType())) {
				return objVar.getTxtList().size();
			}
		}

		throw new EugeneException("I cannot determine the size of " + sName);
	}

	/*** SAVE Statement **/
	public void save(String sName, NamedElement objElement)
			throws EugeneException {

		// get the ID from the symbol tables
		if (null == objElement) {
			throw new EugeneException(
					"I cannot save a NULL object to the result set!");
		}

		if (!(objElement instanceof SavableElement)) {
			throw new EugeneException(
					"The "+ objElement.getName() + 
					" element is not a variable and hence cannot be saved into the result set!");
		}

		if (null != sName) {
			NamedElement objName = SymbolTables.get(sName);
			if (null == objName) {
				throw new EugeneException("I don't know anything about "
						+ sName);
			} else if (!(objName instanceof Variable)) {
				throw new EugeneException("The " + sName
						+ " element is not a variable!");
			}

			Variable objVariable = (Variable) objName;
			if (!EugeneConstants.TXT.equals(objVariable.getType())) {
				throw new EugeneException("The " + sName
						+ " variable is not of type txt!");
			}

			sName = (String) objVariable.getTxt();
		} else {
			sName = objElement.getName();
		}
		
		objResultSet.save(sName, (SavableElement) objElement);
	}

	public ResultSet getResultSet() {
		return this.objResultSet;
	}

	/*** CLEAN UP FUNCTION ***/
	public void cleanUp() {
		if (null != this.objResultSet) {
			this.objResultSet.clear();
			this.objResultSet = null;
		}
	}

	// SBOL Import
	public NamedElement importSBOL(String sFile) throws EugeneException {
		if (null == sFile || sFile.isEmpty()) {
			return (NamedElement) null;
		}

		if (sFile.startsWith("\"") && sFile.endsWith("\"")) {
			sFile = sFile.substring(1, sFile.length() - 1);
		}

		NamedElement objElement = SBOLImporter.importSBOL(sFile);
		if (null != objElement) {
			SymbolTables.put(objElement);
		}
		return objElement;
	}

	public void exportToSBOL(String sName, String sFileName)
			throws EugeneException {
		NamedElement objElement = this.get(sName);
		if (objElement == null) {
			throw new EugeneException("I don't know anything about " + sName
					+ "!");
		} else if (objElement instanceof ComponentArray) {
			SBOLExporter.serialize((ComponentArray) objElement, sFileName);
		} else if (objElement instanceof Device) {
			SBOLExporter.serialize((Device) objElement, sFileName);
		} else if (objElement instanceof org.cidarlab.eugene.dom.collection.EugeneCollection) {

			SBOLExporter.serialize((EugeneCollection)objElement, sFileName);

		} else {
			throw new EugeneException(
					"I cannot export the "
							+ sName
							+ " element to SBOL! "
							+ "Only collections, arrays, devices, parts, and part types are allowed!");
		}
	}
	
	public void pigeon(String name) 
			throws EugeneException {
		
		if(null == name || name.isEmpty()) {
			throw new EugeneException("Invalid name!");
		}
		
		/*
		 * retrieve the object from the symboltables
		 */
		NamedElement element = SymbolTables.get(name);
		if(null == element) {
			throw new EugeneException("I cannot find an element named "+name+"!");
		}
		
		if(!(element instanceof Device || element instanceof EugeneCollection || element instanceof ComponentArray ||
				element instanceof Part || element instanceof PartType)) {
			throw new EugeneException("I cannot visualize "+name+"!");
		}
		
		Pigeon.draw(element);
	}
	
	public void importFromRegistry(String name) 
			throws Exception {
//        Part objPart = new SBOLRegistryImporter().importPart(name);
//        System.out.println(objPart);
//        if(null!=objPart) {
//            SymbolTables.put(objPart);
//        } else {
//            throw new EugeneException("Cannot import "+name+"!");
//        }
	}

	/*** COMPARATIVE EXPRESSION ***/
	public Variable compare(NamedElement objLeft, String sOperator,
			NamedElement objRight) throws EugeneException {

		boolean b = false;
		if (EugeneConstants.EQUALS.equals(sOperator) || "==".equals(sOperator)) {
			b = objLeft.equals(objRight);
		} else if (EugeneConstants.NOTEQUALS.equalsIgnoreCase(sOperator)
				|| "!=".equals(sOperator)) {
			b = !objLeft.equals(objRight);
		} else if (EugeneConstants.INSTANCEOF.equalsIgnoreCase(sOperator)) {

			if (objLeft instanceof Component && objRight instanceof Component) {
				b = ((Component) objLeft).isInstanceOf(((Component) objRight)
						.getName());
			}
		} else if (objLeft instanceof Variable && objRight instanceof Variable) {
			Variable objVar1 = (Variable) objLeft;
			Variable objVar2 = (Variable) objRight;

			if (!EugeneConstants.NUM.equals(objVar1.getType())
					|| !EugeneConstants.NUM.equals(objVar2.getType())) {
				throw new EugeneException("The " + sOperator
						+ " operator is only applicable on numeric values!");
			}

			if (EugeneConstants.LT.equals(sOperator)) {
				b = ((Variable) objLeft).lt(objRight);
			} else if (EugeneConstants.LEQ.equals(sOperator)) {
				b = ((Variable) objLeft).leq(objRight);
			} else if (EugeneConstants.GEQ.equals(sOperator)) {
				b = ((Variable) objLeft).geq(objRight);
			} else if (EugeneConstants.GT.equals(sOperator)) {
				b = ((Variable) objLeft).gt(objRight);
			}
		}

		return EugeneBuilder.buildVariable(String.valueOf(b));
	}

	public boolean isInstanceOf(NamedElement objElement, String sType)
			throws EugeneException {
		if (null == objElement) {
			throw new EugeneException(
					"I cannot apply the INSTANCEOF operator on a NULL value!");
		}
		if (null == sType || sType.isEmpty()) {
			throw new EugeneException("Invalid type given!");
		}

		// objElement instanceof {Device, Part, ID}

		if (objElement instanceof Device) { // Device
			return EugeneConstants.DEVICE.equals(sType);
		} else if (objElement instanceof PartType) { // Part
			return EugeneConstants.PART.equals(sType);
		} else if (objElement instanceof Part) {
			// check if the sType is an Part (ID)
			NamedElement objType = SymbolTables.get(sType);
			if (null == objType) {
				throw new EugeneException("I don't know anything about "
						+ sType);
			}

			if (objType instanceof PartType) {
				// check if the part's (i.e. the objElement) part type is equal
				// to the given part type
				return ((Part) objElement).getPartType().getName()
						.equals(((PartType) objType).getName());
			}
			return false;
		}
		throw new EugeneException(
				"The INSTANCEOF operator is currently available only on Devices, Parts, and Part Types!");
	}
	
	public String toSequence(NamedElement objElement) 
			throws EugeneException {
        if(!(objElement instanceof Component) && !(objElement instanceof ComponentArray)) {
        	throw new EugeneException(objElement.getName() + " is not a Device nor Part!");
        }
        
    	StringBuilder sb = new StringBuilder();
    	String NEWLINE = System.getProperty("line.separator");
        if(objElement instanceof Component) {
        	return ((Component)objElement).toSequence();
        } else if(objElement instanceof DeviceArray) {
        	
        	for(Device device : ((DeviceArray)objElement).getComponents()) {
        		sb.append(device.toSequence()).append(NEWLINE);
        	}
        	
        } else if(objElement instanceof PartArray) {
        	
        	for(Part part : ((PartArray)objElement).getComponents()) {
        		sb.append(part.toSequence()).append(NEWLINE);
        	}
        	
        }
        return sb.toString();
	}
}
