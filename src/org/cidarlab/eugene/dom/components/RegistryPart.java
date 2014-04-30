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

public class RegistryPart extends Part {

	private static final long serialVersionUID = 6215915877990056351L;

	private String sID;
	private String sShortName;
	private String sPartType;
	private String sURL;
	private String sDNASequence;

	public RegistryPart(String sName) {
		super(sName);
	}

	public String getID() {
		return sID;
	}

	public void setID(String iD) {
		sID = iD;
	}

	public String getShortName() {
		return sShortName;
	}

	public void setShortName(String sShortName) {
		this.sShortName = sShortName;
	}

	public void setPartType(String sPartType) {
		this.sPartType = sPartType;
	}

	public String getURL() {
		return sURL;
	}

	public void setURL(String sURL) {
		this.sURL = sURL;
	}

	public String getDNASequence() {
		return sDNASequence;
	}

	public void setDNASequence(String sDNASequence) {
		this.sDNASequence = sDNASequence;
	}

	public String getName() {
		return sName;
	}

	public void setName(String sName) {
		this.sName = sName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getName()).append(" (").append(this.sDNASequence);
		sb.append(");");
		return sb.toString();
	}
}
