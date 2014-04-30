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

package org.cidarlab.eugene.dom.characterization;

import java.util.ArrayList;
import java.util.Iterator;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;


public class CharacterizationDevice extends Device {

	private static final long serialVersionUID = -1030580109836219686L;

	// objPart is a reference to the part which is characterized
	private Component objCharacterizedComponent;
	private ArrayList<InductionValue> lstInductions;

	public CharacterizationDevice(Device objDevice)
			throws EugeneException {

		super();

		this.sName = objDevice.getName();
		this.assign(objDevice);
		this.lstInductions = new ArrayList<InductionValue>();
		this.objCharacterizedComponent = null;
	}

	public CharacterizationDevice(Device objDevice, Component objComponent)
			throws EugeneException {

		super();

		this.sName = objDevice.getName();
		this.assign(objDevice);
		this.lstInductions = new ArrayList<InductionValue>();

		if (objComponent == null) {
			throw new EugeneException(
					"You mus specify a component that the " + this.getName()
							+ " device characterizes!");
		} else if (!this.contains(objComponent)) {
			throw new EugeneException("The " + this.getName()
					+ " device does not contain a component named "
					+ objComponent.getName() + "!");
		}

		this.objCharacterizedComponent = objComponent;
	}

	public void setCharacterizedComponent(Component objComponent)
			throws EugeneException {
		if (objComponent == null) {
			throw new EugeneException("You must specify a component that the "
					+ this.getName() + " device characterizes!");
		}

		// check if the device contains the given component
		if (!this.contains(objComponent)) {
			throw new EugeneException("The " + this.getName()
					+ " device does not contain the " + objComponent.getName()
					+ " component!");

		}

		this.objCharacterizedComponent = objComponent;
	}

	public Component getCharacterizedComponent() {
		return this.objCharacterizedComponent;
	}

	public void addInduction(InductionValue objInduction) {
		if (objInduction != null) {
			this.lstInductions.add(objInduction);
		}
	}

	public ArrayList<InductionValue> getInductions() {
		return this.lstInductions;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String NEWLINE = System.getProperty("line.separator");
		sb.append("CharacterizationDevice ").append(this.getName())
				.append(NEWLINE).append(" characterizes ")
				.append(this.getCharacterizedComponent()).append(" {")
				.append(NEWLINE);
		Iterator<InductionValue> it = this.lstInductions.iterator();
		while (it.hasNext()) {
			sb.append(it.next().toString()).append(",").append(NEWLINE);
		}
		sb.append("}").append(NEWLINE);
		return sb.toString();
	}
}
