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

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.exception.EugeneException;


public class Property 
	extends CollectionElement {

	private static final long serialVersionUID = -7886992777320283721L;
	protected String type;

	public Property(String name, String type) {
		super(name);
		this.type = type;
	}

	public Property(String name) {
		super(name);
		this.type = null;
	}

	public void setType(String sType) {
		// sType must be one of the following:
		// num, num[], txt, txt[], or boolean
		if (null != sType
				&& ("num".equals(sType) || "num[]".equals(sType)
						|| "txt".equals(sType) || "txt[]".equals(sType) || "boolean"
							.equals(sType))) {
			this.type = sType;
		}
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Property ").append(this.getName()).append("(")
				.append(this.type).append(");");
		return sb.toString();
	}

	public boolean compareTo(Property objProperty) {
		if (this == objProperty) {
			return true;
		}
		return false;
	}

	@Override
	public void assign(NamedElement objComponent)
			throws EugeneException {
		if (objComponent instanceof Property) {
			Property objProperty = (Property) objComponent;
			this.type = objProperty.getType();
		} else {
			throw new EugeneException(
					"Cannot assign a " + objComponent.getClass() + " value to a Property!");
		}
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Component get(int index) {
		return null;
	}

	@Override
	public Property get(String sName) {
		return null;
	}

	@Override
	public boolean equals(NamedElement objElement) {
		if (!(objElement instanceof Property)) {
			return false;
		}
		Property objVariable = (Property) objElement;
		if (!this.getType().equals(objVariable.getType())) {
			return false;
		}
		return true;
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		throw new EugeneException(
				"This assignment operation with a given index is not available for Properties!");
	}

	@Override
	public void set(String sPropertyName, NamedElement objElement)
			throws EugeneException {
		throw new EugeneException(
				"This assignment operation with a given name is not available for Properties!");
	}
}