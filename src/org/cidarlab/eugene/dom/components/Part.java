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

package org.cidarlab.eugene.dom.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.Variable;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.relation.Participant;
import org.cidarlab.eugene.exception.EugeneException;


public class Part 
	extends Component 
	implements Participant {

	private static final long serialVersionUID = 8209305624499801226L;
	
	private HashMap<String, PropertyValue> hmPropertiesValues;
	
	private PartType objPartType;

	// this constructor is used for imported Parts
	// (e.g. from the partsregistry)
	public Part(String sName) {
		super(sName);
		this.objPartType = null;
		this.hmPropertiesValues = new HashMap<String, PropertyValue>();
	}

	public Part(PartType objPartType, String sName) {
		super(sName);
		this.objPartType = objPartType;
		this.hmPropertiesValues = new HashMap<String, PropertyValue>();
	}

	public Part(String sName, Part objPart) 
			throws EugeneException {
		super(sName);
		this.hmPropertiesValues = new HashMap<String, PropertyValue>();
		this.assign(objPart);
		this.sName = sName;
	}

	public PartType getPartType() {
		return objPartType;
	}

	/** OPERATIONS FOR PROPERTIES **/
	public Property getProperty(String sPropertyName) {
//		System.out.println("[Part.getProperty] -> "+sPropertyName);
		
		// first we have to get the Part out of the Symbol Tables
		PartType p = this.getPartType();
		if (null != p) { // this shouldn't happen
			return p.getProperty(sPropertyName);
		}
		return null;
	}

	public Property getProperty(int idx) {
//		System.out.println("[Part.getProperty] -> idx: "+idx);
		if (idx >= 0 && idx < this.getProperties().size()) {
			return this.getProperties().get(idx);
		}
		return null;
	}

	/** OPERATIONS FOR PROPERTY VALUES ***/
	public void setPropertyValue(
			String sPropertyName, PropertyValue objPropertyValue) {

		Property objProperty = this.getProperty(sPropertyName);
		if (null != objProperty) {
			if (this.hmPropertiesValues.containsKey(sPropertyName)) {
				this.hmPropertiesValues.remove(sPropertyName);
			}
			objPropertyValue.setName(sPropertyName);

			this.hmPropertiesValues.put(sPropertyName, objPropertyValue);
		}
	}

	public void setPropertiesValues(
			HashMap<String, PropertyValue> hmPropertiesValues) {
		// check if property exists
		this.hmPropertiesValues = hmPropertiesValues;
	}

	public ArrayList<PropertyValue> getPropertyValues() {
		ArrayList<PropertyValue> lst = new ArrayList<PropertyValue>();
		if (this.objPartType != null) {
			List<Property> lstProperties = this.objPartType.getProperties();
			for(Property prop : lstProperties) {
				PropertyValue val = this.getPropertyValue(
						prop.getName());
				if (null != val) {
					lst.add(val);
				}
			}
		}
		return lst;
	}

	public String getInstanceName() {
		return this.getName();
	}

	public void addProperty(Property objProperty) {
		this.getProperties().add(objProperty);
	}

	public void addProperties(ArrayList<Property> lstProperties) {
		this.getProperties().addAll(lstProperties);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.objPartType != null) {
			sb.append(this.objPartType.getName()).append(" ");
		} else {
			sb.append("Part ");
		}

		sb.append(this.getName()).append(" (");

		if (null != this.hmPropertiesValues
				&& !this.hmPropertiesValues.isEmpty()) {
			Iterator<String> it = this.hmPropertiesValues.keySet().iterator();
			while (it.hasNext()) {
				String sPropertyName = it.next();
				PropertyValue objValue = this.hmPropertiesValues
						.get(sPropertyName);
				sb.append(".").append(sPropertyName).append("(");

				if (objValue != null) {
					sb.append("\"").append(objValue.getValue()).append("\"");
				}
				sb.append(")");

				if (it.hasNext()) {
					sb.append(",");
				}
			}
		}

		sb.append(");");
		return sb.toString();
	}

	public String toSequence() {
		if(null != this.objPartType.getProperties()) {
			// iterate over all properties
			for(Property prop : this.objPartType.getProperties()) {
				if(prop.getName().equalsIgnoreCase(EugeneConstants.SEQUENCE_PROPERTY)) {
					if(null != this.hmPropertiesValues.get(prop.getName())) {
						return this.hmPropertiesValues.get(prop.getName()).getTxt().toLowerCase();
					}
				}
	 		}
		}
		
		return null;
	}

	public boolean compareTo(Part objPartInstance) {
		if (this == objPartInstance) {
			return true;
		}
		return false;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {

		// System.out.println(this.getName()+".assign("+objElement+")");

		this.hmPropertiesValues = new HashMap<String, PropertyValue>();

		if (objElement instanceof Part) {
			Part objRightPart = (Part) objElement;

			/* assign the part type */
			this.objPartType = objRightPart.getPartType();

			/* assign the properties */
			this.lstProperties = new ArrayList<Property>();
			for(Property prop : objRightPart.getProperties()) {
				this.lstProperties.add(prop); 
			}

			/* assign the property values */
			HashMap<String, PropertyValue> hmRightValues = objRightPart.getPropertyValuesHashMap();
			Iterator<String> it = hmRightValues.keySet().iterator();
			while (it.hasNext()) {
				PropertyValue rightValue = hmRightValues.get(it.next());

				PropertyValue leftValue = new PropertyValue(
						rightValue.getName(), rightValue.getType());
				leftValue.setValue(rightValue);

				this.hmPropertiesValues.put(leftValue.getName(), leftValue);
			}

			/* assign the sequence value */
			this.setSequence(objRightPart.getSequence());

		} else {
			throw new EugeneException("Cannot assign a "
					+ objElement.getClass() + " value to a Part!");
		}
	}
	
	public HashMap<String, PropertyValue> getPropertyValuesHashMap() {
		return this.hmPropertiesValues;
	}

	@Override
	public int size() {
		return this.hmPropertiesValues.size();
	}

	public PropertyValue getPropertyValue(int idx) {
//		System.out.println(this.getName()+".getPropertyValue("+idx+")");
		// get the idx-th property
		Property objProperty = this.getProperty(idx);
		if (null != objProperty) {
			PropertyValue pv = this.hmPropertiesValues.get(objProperty.getName());
			if(null == pv) {
				pv = new PropertyValue(objProperty.getName(), objProperty.getType());
			}
			return pv;
		}
		return null;
	}

	public PropertyValue getPropertyValue(String sPropertyName) {
//		System.out.println(this.getName()+".getPropertyValue("+sPropertyName+")");
		Property objProperty = this.getProperty(sPropertyName);
		if(null != objProperty) {
			PropertyValue pv = this.hmPropertiesValues.get(objProperty.getName());
			if(null == pv) {
				pv = new PropertyValue(objProperty.getName(), objProperty.getType());
			}
			return pv;
		}
		return null;
	}

	@Override
	public boolean contains(NamedElement objElement) {
		if (objElement instanceof Property) {
			Property objProperty = (Property) objElement;
			if (this.hmPropertiesValues.containsKey(objProperty.getName())) {
				return true;
			}

			// check if the part type contains the given property
			return this.objPartType.contains(objProperty);
			// return this.lstProperties.contains(objProperty);

			/**
			 * for(int i=0; i<lstProperties.size(); i++) {
			 * if(lstProperties.get(i).getName().equals(objProperty.getName()))
			 * { return true; } }
			 **/
		}
		return false;
	}

	@Override
	public boolean equals(NamedElement objComponent) {
		// System.out.println("[Part.equals] "+this.getName()+" vs "+objComponent);
		if (objComponent instanceof PartType) {
			return ((PartType) objComponent).equals(this.getPartType());
		} else if (!(objComponent instanceof Part)) {
			return false;
		}

		Part objPart = (Part) objComponent;
		if (!this.getPartType().equals(objPart.getPartType())) {
			return false;
		}

		if (this.getName().equals(objPart.getName())) {
			return true;
		}

		// compare the list of properties
		if (this.lstProperties.size() != objPart.lstProperties.size()) {
			return false;
		}

		// now, compare both property values
		Iterator<String> it = this.hmPropertiesValues.keySet().iterator();
		while (it.hasNext()) {
			PropertyValue objPropertyValue = this.hmPropertiesValues.get(it
					.next());
			if (objPropertyValue != null) {
				if (objPart.getPropertyValue(objPropertyValue.getName()) == null) {
					return false;
				}
				if (!objPropertyValue.equals(objPart
						.getPropertyValue(objPropertyValue.getName()))) {
					return false;
				}
			}
		}

		if (!this.getName().equals(objPart.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public void add(NamedElement objElement)
			throws EugeneException {
		if (objElement != null && objElement instanceof Property) {
			this.lstProperties.add((Property) objElement);
		} else {
			throw new EugeneException("Cannot assign "
					+ objElement.getClass().toString() + " to a Part Type!");
		}
	}

	@Override
	public NamedElement get(int idx) {
		if (idx >= 0 && idx < this.objPartType.getProperties().size()) {
			// get the property
			Property objProperty = this.objPartType.getProperties().get(idx);
			PropertyValue objPropertyValue = this.hmPropertiesValues
					.get(objProperty.getName());
			if (objPropertyValue == null) {
				return new PropertyValue(objProperty.getName(),
						objProperty.getType());
			} else {
				return objPropertyValue;
			}
		}
		return null;
	}

	@Override
	public NamedElement get(String sPropertyName) {
		Property objProperty = this.objPartType.get(sPropertyName);
		if (objProperty != null) {
			
//			System.out.println("[Part.get] -> "+objProperty);
			PropertyValue objPropertyValue = 
					this.hmPropertiesValues.get(objProperty.getName());

			if (objPropertyValue == null) { // i.e. the property value is not
											// set
				objPropertyValue = new PropertyValue(objProperty.getName(),
						objProperty.getType());
				return objPropertyValue;
			}
//			System.out.println("[Part.get] -> returning "+objPropertyValue);
			return objPropertyValue;
		}
		return null;
	}

	/**
	 * @Override public int indexOf(NamedElement objElement) {
	 *           if(objElement!=null && objElement instanceof Property) { return
	 *           this.lstProperties.indexOf(objElement); } return -1; }
	 * @Override public int lastIndexOf(NamedElement objElement) {
	 *           if(objElement!=null && objElement instanceof Property) {
	 *           for(int i=this.lstProperties.size()-1;i>=0;i++) {
	 *           if(this.lstProperties
	 *           .get(i).getName().equals(objElement.getName())) { return i; } }
	 *           } return -1; }
	 **/

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		// set the idx-th property to the given element
		if (idx >= 0 && idx < this.lstProperties.size()
				&& objElement instanceof Property) {
			this.lstProperties.set(idx, (Property) objElement);
		} else if (idx >= 0 && idx < this.lstProperties.size()
				&& objElement instanceof Variable) {
			Property objProperty = this.lstProperties.get(idx);
			if (this.hmPropertiesValues.containsKey(objProperty.getName())) {
				this.hmPropertiesValues.remove(objProperty.getName());
			}
			this.hmPropertiesValues.put(objProperty.getName(),
					((Variable) objElement).toPropertyValue(this));
		} else {
			throw new EugeneException("Cannot assign the "
					+ objElement + " element to the " + (idx + 1)
					+ " property of the " + this.getName() + " Part");
		}
	}

	@Override
	public void set(String sPropertyName, NamedElement objElement)
			throws EugeneException {

		// first, check if the part contains the given property

		for(Property property  : this.objPartType.getProperties()) {
			if (property.getName().equals(sPropertyName)) {
				if (objElement instanceof Variable) {
					Variable objVariable = (Variable) objElement;
					PropertyValue pv = new PropertyValue(objVariable.getName(),
							objVariable.getType());
					pv.setValue(objVariable);

					this.setPropertyValue(sPropertyName, pv);
					
					return;
				} else {
					throw new EugeneException("Cannot assign a "
							+ objElement.getClass()
							+ " element to a Part's Property!");
				}
			}
		}

		throw new EugeneException("The " + this.getName()
					+ " Part does not have a " + objElement.getName()
					+ " Property!");
	}

	public void setValue(Property objProperty, PropertyValue objValue) {
		if (this.contains(objProperty)) {
			this.hmPropertiesValues.put(objProperty.getName(), objValue);
		}
	}

	public boolean isInstanceOf(String sType) {
		if (this.objPartType != null && null != sType) {
			return this.objPartType.getName().equals(sType);
		}
		return false;
	}
}
