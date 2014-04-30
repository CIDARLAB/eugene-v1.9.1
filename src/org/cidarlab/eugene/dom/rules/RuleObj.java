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

package org.cidarlab.eugene.dom.rules;

import org.cidarlab.eugene.dom.NamedElement;

public class RuleObj {
	private NamedElement objLeft;
	private String sOperator;
	private NamedElement objRight;

	public RuleObj(NamedElement objLeft, String sOperator, NamedElement objRight) {
		this.objLeft = objLeft;
		this.sOperator = sOperator;
		this.objRight = objRight;
	}

	public NamedElement getLeft() {
		return this.objLeft;
	}

	public NamedElement getRight() {
		return this.objRight;
	}

	public String getOperand() {
		return this.sOperator;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(objLeft).append(" ").append(sOperator).append(" ")
				.append(objRight);
		return sb.toString();
	}
}
