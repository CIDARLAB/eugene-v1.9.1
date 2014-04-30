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

package org.cidarlab.eugene.dom.components.types;

import java.util.ArrayList;
import java.util.List;

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.Property;
import org.cidarlab.eugene.dom.relation.Participant;
import org.cidarlab.eugene.exception.EugeneException;


public class PartType 
	extends Component 
	implements Participant {

	private static final long serialVersionUID = -6622147586177538971L;

	public PartType(String name) {
		super(name);
	}

	public PartType(String name, List<Property> properties) {
		super(name, properties);
	}
	
	public boolean contains(NamedElement objElement) {
		if (objElement != null && objElement instanceof Property) {
			Property objProperty = (Property) objElement;

			// iterate over all properties
			for (Property p : this.getProperties()) {
				if (p.getName().equals(objProperty.getName())) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PartType ").append(this.getName()).append(" (");

		for(Property objProperty : this.getProperties()) {
			sb.append(objProperty.toString());
			sb.append(", ");
		}

		// remove the last semicolon
		if(!this.getProperties().isEmpty()) {
			sb.deleteCharAt(sb.toString().length()-2);
			sb.deleteCharAt(sb.toString().length()-1);
		}
		
		sb.append(");");
		return sb.toString();
	}

//	public ArrayList<Part> getInstances() {
//		return new ArrayList<Part>(SymbolTables.getParts(this));
//	}

//	public ArrayList<String> getInstanceNames() {
//
//		ArrayList<String> lstPartInstanceNames = new ArrayList<String>();
//
//		Collection<Part> colPartInstances = SymbolTables.getParts(this);
//		if (null != colPartInstances && !colPartInstances.isEmpty()) {
//			Iterator<Part> it = colPartInstances.iterator();
//			while (it.hasNext()) {
//				Part objPart = it.next();
//				lstPartInstanceNames.add(objPart.getName());
//			}
//		}
//
//		return lstPartInstanceNames;
//	}

	public boolean compareTo(PartType objPart) {
		if (this == objPart) {
			return true;
		}
		return false;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {
		if (null != objElement && objElement instanceof PartType) {
			this.setProperties(
					new ArrayList<Property>(((PartType) objElement).getProperties()));
		} else {
			throw new EugeneException("I cannot assign the "
					+ objElement + " element to a part type!");
		}
	}

	@Override
	public String toSequence() throws EugeneException {
		return "<" + this.getName() + ">";
	}

	@Override
	public int size() {
		return this.getProperties().size();
	}

	@Override
	public Property get(int index) {
		if (index >= 0 && index < this.getProperties().size()) {
			return this.getProperties().get(index);
		}
		return null;
	}

	@Override
	public Property get(String sPropertyName) {
		for (Property p : this.getProperties()) {
			if (p.getName().equals(sPropertyName)) {
				return p;
			}
		}
		return (Property) null;
	}

	@Override
	public boolean equals(NamedElement objComponent) {
		if (objComponent == null) {
			return false;
		} else if (objComponent instanceof Part) {
			Part objPart = (Part) objComponent;
			return this.equals(objPart.getPartType());
		} else if (!(objComponent instanceof PartType)) {
			return false;
		}

		PartType objPartType = (PartType) objComponent;
		if (!objPartType.getName().equals(this.sName)) {
			return false;
		}

		// compare the list of properties
		if (this.getProperties().size() != objPartType.getProperties().size()) {
			return false;
		}

		int i = 0;
		for(Property prop : this.getProperties()) {
			if (!prop.equals(objPartType.getProperties().get(i))) {
				return false;
			}
			i++;
		}
		return true;
	}

	@Override
	public void add(NamedElement objElement)
			throws EugeneException {
		if (objElement instanceof Property) {
			this.getProperties().add((Property) objElement);
		} else {
			throw new EugeneException("Cannot assign "
					+ objElement.getClass().toString() + " to a Part Type!");
		}
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		// set the idx-th property to the given element
		if (idx >= 0 && idx < this.getProperties().size()
					&& objElement instanceof Property) {
			this.getProperties().set(idx, (Property) objElement);
		} else {
			throw new EugeneException(objElement
					+ " is not a Property and hence cannot be assigned to the "
					+ (idx + 1) + " property of the " + this.getName()
					+ " Part Type");
		}

	}

	@Override
	public void set(String sPropertyName, NamedElement objElement)
			throws EugeneException {
		throw new EugeneException(
				"Cannot set the property value of a Part Type!");
	}

	public boolean isInstanceOf(String sType) {
		return false;
	}
}
