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
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.exception.EugeneException;


public class PartArray 
		extends ComponentArray {

	private static final long serialVersionUID = 1159317451726589059L;
	private ArrayList<Part> lstParts;

	public PartArray(String sName) {
		super(sName);
		lstParts = new ArrayList<Part>();
	}

	public ArrayList<Part> getParts() {
		return this.lstParts;
	}

	public void add(Part objPart) {
		if (objPart != null && objPart instanceof Part) {
			this.lstParts.add(objPart);
		}
	}

	public Part get(int idx) {
		if (idx >= 0 && idx < lstParts.size()) {
			return lstParts.get(idx);
		}
		return null;
	}

	public Part get(String sPartObjName) {
		if (sPartObjName != null) {
			for (int i = 0; i < lstParts.size(); i++) {
				if (sPartObjName.equals(lstParts.get(i).getName())) {
					return lstParts.get(i);
				}
			}
		}
		return null;
	}

	@Override
	public boolean equals(NamedElement objElement) {
		if (objElement != null && objElement instanceof PartArray) {
			PartArray objPartArray = (PartArray) objElement;
			if (objPartArray.size() == this.size()) {
				for (int i = 0; i < lstParts.size(); i++) {
					if (!objPartArray.get(i).equals(this.lstParts.get(i))) {
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
		return this.lstParts.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PartObj[] ").append(this.getName()).append(" [");
		for (int i = 0; i < lstParts.size(); i++) {
			sb.append(lstParts.get(i).getName());
			if (i < lstParts.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean contains(Part objPart) {
		return lstParts.contains(objPart);
	}

	public boolean contains(String sPartObjName) {
		if (sPartObjName != null) {
			for (int i = 0; i < lstParts.size(); i++) {
				if (sPartObjName.equals(lstParts.get(i).getName())) {
					return true;
				}
			}
		}
		return false;
	}

	public int indexOf(Part objPart) {
		return this.lstParts.indexOf(objPart);
	}

	public int indexOf(String sPartObjName) {
		if (sPartObjName != null) {
			for (int i = 0; i < lstParts.size(); i++) {
				if (sPartObjName.equals(lstParts.get(i).getName())) {
					return i;
				}
			}
		}
		return -1;
	}

	public int lastIndexOf(Part objPart) {
		return this.lstParts.lastIndexOf(objPart);
	}

	public int lastIndexOf(String sPartName) {
		if (sPartName != null) {
			for (int i = lstParts.size() - 1; i >= 0; i--) {
				if (sPartName.equals(lstParts.get(i).getName())) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {

		if (objElement == null || !(objElement instanceof PartArray)) {
			throw new EugeneException("" + "The " + objElement
					+ " is not an array of Parts!");
		}

		this.lstParts = new ArrayList<Part>();

		PartArray objPartArray = (PartArray) objElement;
		ArrayList<Part> lst = objPartArray.getParts();
		for (int i = 0; i < lst.size(); i++) {
			this.lstParts.add(lst.get(i));
		}
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		if (objElement instanceof Part) {
			if (idx >= 0 && idx < this.lstParts.size()) {
				this.lstParts.set(idx, (Part) objElement);
			} else {
				throw new EugeneException("Array index ("
						+ idx + ") is out of bounds!");
			}
		} else {
			throw new EugeneException(objElement.getName()
					+ " is not a Part!");
		}
	}

	@Override
	public void set(String sPartName, NamedElement objElement)
			throws EugeneException {
		if (this.lstParts != null && !this.contains(sPartName)) {
			if (objElement instanceof Part) {
				Part objPart = this.get(sPartName);
				objPart.assign((Part) objElement);
			} else {
				throw new EugeneException("I cannot place a "
						+ objElement.getClass() + " element into the "
						+ this.getName() + " Part list!");
			}
		} else {
			throw new EugeneException("The " + this.getName()
					+ " Part list does not contain a Part named " + sPartName
					+ "!");
		}
	}

	@Override
	public void add(NamedElement objElement)
			throws EugeneException {
		if (!(objElement instanceof Part)) {
			throw new EugeneException("I cannot add the "
					+ objElement.getName() + " to the " + this.getName()
					+ " part array!");
		}
		if (this.lstParts == null) {
			this.lstParts = new ArrayList<Part>();
		}
		lstParts.add((Part) objElement);
	}

	@Override
	public ArrayList<Part> getComponents() {
		return this.lstParts;
	}

	@Override
	public void add(String sComponent) throws EugeneException {

	}

	@Override
	public void remove(int idx) 
			throws EugeneException {
		if(null != this.lstParts) {
			if(idx>=0 && idx<this.lstParts.size()) {
				this.lstParts.remove(idx);
			} else {
				throw new EugeneException(idx+" is an invalid index!");
			}
		}
	}
}
