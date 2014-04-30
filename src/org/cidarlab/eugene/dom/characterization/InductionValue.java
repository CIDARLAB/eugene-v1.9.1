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

import java.io.File;

public class InductionValue {
	private CharacterizationDevice objCharDevice;

	private double nInduction;
	private File objFile;

	public InductionValue(CharacterizationDevice objCharDevice,
			double nInduction, File objFile) {
		this.objCharDevice = objCharDevice;
		this.nInduction = nInduction;
		this.objFile = objFile;
	}

	public CharacterizationDevice getDevice() {
		return this.objCharDevice;
	}

	public double getInduction() {
		return this.nInduction;
	}

	public File getFile() {
		return this.objFile;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(nInduction).append(":").append(objFile.getAbsolutePath());
		return sb.toString();
	}
}
