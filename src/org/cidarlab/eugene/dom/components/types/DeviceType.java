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
import java.util.Iterator;
import java.util.List;

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.arrays.ComponentArray;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.exception.EugeneException;


public class DeviceType 
		extends ComponentArray {

	private static final long serialVersionUID = -388030234178021710L;

	private ArrayList<NamedElement> lstElements;

	public DeviceType(String sName) {
		super(sName);
		this.lstElements = new ArrayList<NamedElement>();
	}

	public DeviceType(String sName, List<NamedElement> lstElements) {
		super(sName);
		this.lstElements = new ArrayList<NamedElement>(lstElements);
	}

	@Override
	public void add(NamedElement objElement)
			throws EugeneException {
		if (null != objElement) {
			this.lstElements.add(objElement);
		}
	}

	@Override
	public ArrayList<NamedElement> getComponents() {
		return this.lstElements;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {
	}

	@Override
	public void set(String sElementName, NamedElement objElement)
			throws EugeneException {
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
	}

	@Override
	public boolean equals(NamedElement objElement) {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public NamedElement get(int index) {
		return null;
	}

	@Override
	public NamedElement get(String sName) {
		return null;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DeviceType ").append(this.getName()).append(" [");
		if (null != this.lstElements && !this.lstElements.isEmpty()) {
			Iterator<NamedElement> it = this.lstElements.iterator();
			while (it.hasNext()) {
				NamedElement objElement = it.next();
				sb.append(objElement.getName());
				if (it.hasNext()) {
					sb.append(", ");
				}
			}
		}
		sb.append("];");
		return sb.toString();
	}

	@Override
	public void add(String sComponent) throws EugeneException {
	}

	@Override
	public void remove(int idx) 
			throws EugeneException {
		// TODO Auto-generated method stub		
	}
}
