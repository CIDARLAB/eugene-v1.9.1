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

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;


public class DeviceInstance extends Device {

	private static final long serialVersionUID = -8672375572242932363L;
	private ArrayList<Component> lstComponents;
	private Device objInstanceOf;

	// the isValid flag marks the device if it passes all defined rules
	// one rule is violated => isValid=false
	// no rule is violated => isValid=true
	// this information is used for the SBOL serialization of the device
	private boolean isValid;

	private DeviceInstance(String sName, Device objInstanceOf) {
		super(sName);
		lstComponents = new ArrayList<Component>();
		this.objInstanceOf = objInstanceOf;
	}

	public static DeviceInstance newInstance(String sName, Device objDevice) {
		return new DeviceInstance(sName, objDevice);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(objInstanceOf.getName()).append(" ").append(this.getName())
				.append("(");
		for (int i = 0; i < this.lstComponents.size(); i++) {
			sb.append(this.lstComponents.get(i).getName());
			if (i != this.lstComponents.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	public Device getDevice() {
		return this.objInstanceOf;
	}

	/*** GET FUNCTIONS ***/
	@Override
	public Component get(int idx) {
		if (idx >= 0 && idx < this.lstComponents.size()) {
			return this.lstComponents.get(idx);
		}
		return null;
	}

	public void setComponents(ArrayList<Component> lstComponents)
			throws EugeneException {
		if (lstComponents != null) {
			for (int i = 0; i < lstComponents.size(); i++) {

				// for the time being
				this.lstComponents.add(lstComponents.get(i));

				// later: this.add(lstComponents.get(i));
			}
		}
	}

	public void add(Component objComponent) {
		// check if the component is a part of the appropriate part type
		if (this.lstComponents == null) {
			this.lstComponents = new ArrayList<Component>();
		}
	}

	public String toSequence() throws EugeneException {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.lstComponents.size(); i++) {
			Component objComponent = this.lstComponents.get(i);

			if (objComponent instanceof DeviceInstance) {
				DeviceInstance d = (DeviceInstance) objComponent;
				sb.append(d.toSequence());
			} else if (objComponent instanceof PartType) {
				PartType p = (PartType) objComponent;
				sb.append("<").append(p.getName()).append(">");
			} else if (objComponent instanceof Part) {
				Part pi = (Part) objComponent;
				String sSequence = pi.toSequence();
				if (null == sSequence) {
					throw new EugeneException("Part Instance " + pi.getName()
							+ " does not have a DNA sequence!");
				} else {
					sb.append(sSequence);
				}
			}
		}
		return sb.toString();
	}

	public boolean compareTo(DeviceInstance objDevice) {
		if (this == objDevice) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(NamedElement objComponent) {
		if (!(objComponent instanceof DeviceInstance)) {
			return false;
		}
		DeviceInstance objDeviceInstance = (DeviceInstance) objComponent;
		if (!objDeviceInstance.getDevice().equals(this.getDevice())) {
			return false;
		}

		ArrayList<Component> lstThisComponents = this.getAllComponents();
		ArrayList<Component> lstComponents = objDeviceInstance
				.getAllComponents();

		if (lstComponents.size() != lstThisComponents.size()) {
			return false;
		}
		for (int i = 0; i < lstThisComponents.size(); i++) {
			if (!lstThisComponents.get(i).equals(lstComponents.get(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean isInstanceOf(Component objComponent) {
		if (objComponent != null && objComponent instanceof Device
				&& !(objComponent instanceof DeviceInstance)) {
			if (this.objInstanceOf.equals((Device) objComponent)) {
				return true;
			}
		}
		return false;
	}

	public int size() {
		return this.lstComponents.size();
	}
}
