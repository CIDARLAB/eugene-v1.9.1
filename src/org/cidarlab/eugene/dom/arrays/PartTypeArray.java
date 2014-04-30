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
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;


public class PartTypeArray 
		extends ComponentArray {

	private static final long serialVersionUID = 828785012421359413L;
	private ArrayList<PartType> lstPartTypes;

	public PartTypeArray(String sName) {
		super(sName);
		lstPartTypes = new ArrayList<PartType>();
	}

	public ArrayList<PartType> getPartTypes() {
		return this.lstPartTypes;
	}

	public void add(PartType objPartType) {
		if (objPartType != null) {
			this.lstPartTypes.add(objPartType);
		}
	}

	public PartType get(int idx) {
		if (idx >= 0 && idx < lstPartTypes.size()) {
			return lstPartTypes.get(idx);
		}
		return null;
	}

	public PartType get(String sPartTypeName) {
		if (sPartTypeName != null) {
			for (int i = 0; i < lstPartTypes.size(); i++) {
				if (sPartTypeName.equals(lstPartTypes.get(i).getName())) {
					return lstPartTypes.get(i);
				}
			}
		}
		return null;
	}

	@Override
	public boolean equals(NamedElement objElement) {
		if (objElement != null && objElement instanceof PartTypeArray) {
			PartTypeArray objPartTypeArray = (PartTypeArray) objElement;
			if (objPartTypeArray.size() == this.size()) {
				for (int i = 0; i < lstPartTypes.size(); i++) {
					if (!objPartTypeArray.get(i).equals(
							this.lstPartTypes.get(i))) {
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
		return this.lstPartTypes.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PartType[] ").append(this.getName()).append(" [");
		for (int i = 0; i < lstPartTypes.size(); i++) {
			sb.append(lstPartTypes.get(i).getName());
			if (i < lstPartTypes.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean contains(PartType objPartType) {
		return lstPartTypes.contains(objPartType);
	}

	public boolean contains(String sPartTypeName) {
		if (sPartTypeName != null) {
			for (int i = 0; i < lstPartTypes.size(); i++) {
				if (sPartTypeName.equals(lstPartTypes.get(i).getName())) {
					return true;
				}
			}
		}
		return false;
	}

	public int indexOf(PartType objPartType) {
		return this.lstPartTypes.indexOf(objPartType);
	}

	public int indexOf(String sPartTypeName) {
		if (sPartTypeName != null) {
			for (int i = 0; i < lstPartTypes.size(); i++) {
				if (sPartTypeName.equals(lstPartTypes.get(i).getName())) {
					return i;
				}
			}
		}
		return -1;
	}

	public int lastIndexOf(PartType objPartType) {
		return this.lstPartTypes.lastIndexOf(objPartType);
	}

	public int lastIndexOf(String sPartTypeName) {
		if (sPartTypeName != null) {
			for (int i = lstPartTypes.size() - 1; i >= 0; i--) {
				if (sPartTypeName.equals(lstPartTypes.get(i).getName())) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {

		if (objElement == null || !(objElement instanceof PartTypeArray)) {
			throw new EugeneException("" + "The " + objElement
					+ " is not an array of Part Types!");
		}

		this.lstPartTypes = new ArrayList<PartType>();

		PartTypeArray objPartTypeArray = (PartTypeArray) objElement;
		ArrayList<PartType> lst = objPartTypeArray.getPartTypes();
		for (int i = 0; i < lst.size(); i++) {
			this.lstPartTypes.add(lst.get(i));
		}
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		if (objElement instanceof PartType) {
			if (idx >= 0 && idx < this.lstPartTypes.size()) {
				this.lstPartTypes.set(idx, (PartType) objElement);
			} else {
				throw new EugeneException("Array index ("
						+ idx + ") is out of bounds!");
			}
		} else {
			throw new EugeneException(objElement.getName()
					+ " is not a Part Type!");
		}
	}

	@Override
	public void set(String sPartTypeName, NamedElement objElement)
			throws EugeneException {
		if (this.lstPartTypes != null && !this.contains(sPartTypeName)) {
			if (objElement instanceof PartType) {
				PartType objPartType = this.get(sPartTypeName);
				objPartType.assign((PartType) objElement);
			} else {
				throw new EugeneException("I cannot place a "
						+ objElement.getClass() + " element into the "
						+ this.getName() + " Part Type list!");
			}
		} else {
			throw new EugeneException("The " + this.getName()
					+ " Part Type list does not contain a Part Type named "
					+ sPartTypeName + "!");
		}
	}

	@Override
	public void add(NamedElement objElement)
			throws EugeneException {
		if (!(objElement instanceof PartType)) {
			throw new EugeneException("I cannot add the "
					+ objElement.getName() + " to the " + this.getName()
					+ " part type array!");
		}
		if (this.lstPartTypes == null) {
			this.lstPartTypes = new ArrayList<PartType>();
		}
		lstPartTypes.add((PartType) objElement);
	}

	public Device assemble() 
			throws EugeneException {
		// assemble all parts into one device
		Device objDevice = new Device(this.getName() + "_Device");
		for (int i = 0; i < lstPartTypes.size(); i++) {
			objDevice.add(lstPartTypes.get(i));
		}
		return objDevice;
	}

	@Override
	public ArrayList<PartType> getComponents() {
		return this.lstPartTypes;
	}

	@Override
	public void add(String sComponent) throws EugeneException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void remove(int idx) 
			throws EugeneException {
		if(null != this.lstPartTypes) {
			if(idx>=0 && idx<this.lstPartTypes.size()) {
				this.lstPartTypes.remove(idx);
			} else {
				throw new EugeneException(idx+" is an invalid index!");
			}
		}
	}
}
