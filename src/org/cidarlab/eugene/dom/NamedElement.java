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

package org.cidarlab.eugene.dom;

import java.io.Serializable;

import org.cidarlab.eugene.exception.EugeneException;


/**
 * 
 * @author Ernst Oberortner
 */
public abstract class NamedElement 
	implements Serializable {

	private static final long serialVersionUID = 1660684454032759291L;

	protected String sName;

	protected NamedElement() {
	}

	public NamedElement(String sName) {
		this.sName = sName;
	}

	public String getName() {
		return this.sName;
	}

	public boolean compareTo(NamedElement objElement) {
		if (this == objElement) {
			return true;
		}
		return false;
	}

	public void setName(String sName) {
		this.sName = sName;
	}
	
	public abstract void assign(NamedElement objElement)
			throws EugeneException;

	public abstract void set(String sElementName, NamedElement objElement)
			throws EugeneException;

	public abstract void set(int idx, NamedElement objElement)
			throws EugeneException;

	public abstract boolean equals(NamedElement objElement);

	public abstract int size();

	public abstract NamedElement get(int index) throws EugeneException;

	public abstract NamedElement get(String sName) throws EugeneException;
}
