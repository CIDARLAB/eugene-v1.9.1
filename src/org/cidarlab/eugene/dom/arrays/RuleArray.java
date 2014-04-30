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
import java.util.Iterator;
import java.util.List;

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;


public class RuleArray 
		extends ComponentArray {

	private ArrayList<String> lstRuleNames;

	public RuleArray(String sName) {
		super(sName);
		lstRuleNames = new ArrayList<String>();
	}

	public RuleArray(String sName, int nSize) {
		super(sName);
		lstRuleNames = new ArrayList<String>(nSize);
	}

	public ArrayList<String> getRuleNames() {
		return this.lstRuleNames;
	}

	public void setRuleNames(ArrayList<String> lstRuleNames) {
		this.lstRuleNames = lstRuleNames;
	}

	@Override
	public int size() {
		if (this.lstRuleNames != null) {
			return this.lstRuleNames.size();
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule[] ").append(this.getName()).append(" [");
		if (null != this.lstRuleNames && !lstRuleNames.isEmpty()) {
			Iterator<String> it = this.lstRuleNames.iterator();
			while (it.hasNext()) {
				sb.append(it.next());
				if (it.hasNext()) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean contains(String sRuleName) {
		return lstRuleNames.contains(sRuleName);
	}

	public void add(Rule objRule) throws EugeneException {

		lstRuleNames.add(objRule.getName());
	}

	public boolean isEmpty() {
		return this.lstRuleNames.isEmpty();
	}

	@Override
	public void add(NamedElement objComponent)
			throws EugeneException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<?> getComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(String sElementName, NamedElement objElement)
			throws EugeneException {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(NamedElement objElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NamedElement get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedElement get(String sName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(String sComponent) throws EugeneException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void remove(int idx) 
			throws EugeneException {
		if(null != this.lstRuleNames) {
			if(idx>=0 && idx<this.lstRuleNames.size()) {
				this.lstRuleNames.remove(idx);
			} else {
				throw new EugeneException(idx+" is an invalid index!");
			}
		}
	}
}
