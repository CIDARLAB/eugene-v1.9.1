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

package org.cidarlab.eugene.dom.arrays;

import java.util.ArrayList;

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.Property;
import org.cidarlab.eugene.exception.EugeneException;


public class PropertyArray 
		extends ComponentArray {

	private static final long serialVersionUID = 57323309099947915L;
	private ArrayList<Property> lstProperties;

	public PropertyArray(String sName) {
		super(sName);
		lstProperties = new ArrayList<Property>();
	}

	public ArrayList<Property> getProperties() {
		return this.lstProperties;
	}

	public void add(Property objProperty) {
		if (objProperty != null && objProperty instanceof Property) {
			this.lstProperties.add(objProperty);
		}
	}

	public Property get(int idx) {
		if (idx >= 0 && idx < lstProperties.size()) {
			return lstProperties.get(idx);
		}
		return null;
	}

	public Property get(String sPropertyName) {
		if (sPropertyName != null) {
			for (int i = 0; i < lstProperties.size(); i++) {
				if (sPropertyName.equals(lstProperties.get(i).getName())) {
					return lstProperties.get(i);
				}
			}
		}
		return null;
	}

	@Override
	public boolean equals(NamedElement objElement) {
		if (objElement != null && objElement instanceof PropertyArray) {
			PropertyArray objPropertyArray = (PropertyArray) objElement;
			if (objPropertyArray.size() == this.size()) {
				for (int i = 0; i < lstProperties.size(); i++) {
					if (!objPropertyArray.get(i).equals(
							this.lstProperties.get(i))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {
		return this.lstProperties.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Property[] ").append(this.getName()).append(" [");
		for (int i = 0; i < lstProperties.size(); i++) {
			sb.append(lstProperties.get(i).getName());
			if (i < lstProperties.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean contains(NamedElement objElement) {
		if (objElement != null && objElement instanceof Property) {
			return lstProperties.contains((Property) objElement);
		}
		return false;
	}

	public boolean contains(String sPropertyName) {
		if (sPropertyName != null) {
			for (int i = 0; i < lstProperties.size(); i++) {
				if (sPropertyName.equals(lstProperties.get(i).getName())) {
					return true;
				}
			}
		}
		return false;
	}

	public int indexOf(Property objProperty) {
		return this.lstProperties.indexOf(objProperty);
	}

	public int indexOf(String sPropertyName) {
		if (sPropertyName != null) {
			for (int i = 0; i < lstProperties.size(); i++) {
				if (sPropertyName.equals(lstProperties.get(i).getName())) {
					return i;
				}
			}
		}
		return -1;
	}

	public int lastIndexOf(Property objProperty) {
		return this.lstProperties.lastIndexOf(objProperty);
	}

	public int lastIndexOf(String sPropertyName) {
		if (sPropertyName != null) {
			for (int i = lstProperties.size() - 1; i >= 0; i--) {
				if (sPropertyName.equals(lstProperties.get(i).getName())) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {

		if (objElement == null || !(objElement instanceof PropertyArray)) {
			throw new EugeneException("" + "The " + objElement
					+ " is not an array of Properties!");
		}

		this.lstProperties = new ArrayList<Property>();

		PropertyArray objPropertyArray = (PropertyArray) objElement;
		ArrayList<Property> lst = objPropertyArray.getProperties();
		for (int i = 0; i < lst.size(); i++) {
			this.lstProperties.add(lst.get(i));
		}
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		if (objElement instanceof Property) {
			if (idx >= 0 && idx < this.lstProperties.size()) {
				this.lstProperties.set(idx, (Property) objElement);
			} else {
				throw new EugeneException("Array index ("
						+ idx + ") is out of bounds!");
			}
		} else {
			throw new EugeneException(objElement.getName()
					+ " is not a Property!");
		}
	}

	@Override
	public void set(String sPropertyName, NamedElement objElement)
			throws EugeneException {
		if (this.lstProperties != null && !this.contains(sPropertyName)) {
			if (objElement instanceof Property) {
				Property objProperty = this.get(sPropertyName);
				objProperty.assign((Property) objElement);
			} else {
				throw new EugeneException("I cannot place a "
						+ objElement.getClass() + " element into the "
						+ this.getName() + " Property list!");
			}
		} else {
			throw new EugeneException("The " + this.getName()
					+ " Property list does not contain a Property named "
					+ sPropertyName + "!");
		}
	}

	public void add(NamedElement objElement)
			throws EugeneException {
		if (!(objElement instanceof Property)) {
			throw new EugeneException("I cannot add the "
					+ objElement.getName() + " to the " + this.getName()
					+ " property array!");
		}
		if (this.lstProperties == null) {
			this.lstProperties = new ArrayList<Property>();
		}
		lstProperties.add((Property) objElement);
	}

	public Part assemble() {
		// assemble all properties into one part
		Part objPart = new Part("PropertiesPart");
		for (int i = 0; i < lstProperties.size(); i++) {
			objPart.addProperty(lstProperties.get(i));
		}
		return objPart;
	}

	@Override
	public ArrayList<Property> getComponents() {
		return this.lstProperties;
	}

	@Override
	public void add(String sComponent) throws EugeneException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void remove(int idx) 
			throws EugeneException {
		if(null != this.lstProperties) {
			if(idx>=0 && idx<this.lstProperties.size()) {
				this.lstProperties.remove(idx);
			} else {
				throw new EugeneException(idx+" is an invalid index!");
			}
		}
	}
}
