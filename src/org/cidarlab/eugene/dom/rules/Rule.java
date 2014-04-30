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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.tree.predicate.Predicate;


/**
 * 
 * @author Ernst Oberortner
 */
public class Rule 
	extends CollectionElement {

	private static final long serialVersionUID = 329956235096352013L;

	// the rule can be specified on a device
	//protected Device objDevice;
	
	protected String operator;

	// protected String sRule;

	// protected boolean NOT=false;

	private CommonTree tree;
	private Token token;

	public Rule(String sRuleName) {
		super(sRuleName);
		this.tree = (CommonTree) null;
		this.token = (Token) null;
	}

	public Rule(String sName, Device device, Token token, CommonTree tree) {
		super(sName);
		this.device = device;
		this.tree = tree;
		this.token = token;
	}

	/*** EUGENE v1.9 ***/
	private Predicate predicate;
	private long nDeviceId;
	private Device device;
	
	public Rule(String sName, Predicate predicate) {
		super(sName);
		this.nDeviceId = -1;
		this.predicate = predicate;
	}

	public Rule(String sName, long nDeviceId, Predicate predicate) {
		super(sName);
		this.nDeviceId = nDeviceId;
		this.predicate = predicate;
		this.device = SymbolTables.getDevice(nDeviceId);
	}
	
	public Rule(String sName, Device device, Predicate predicate) {
		super(sName);
		this.device = device;
		this.predicate = predicate;
		this.nDeviceId = -1;
	}
	
	public Predicate getPredicate() {
		return this.predicate;
	}
	
	public long getDeviceId() {
		return this.nDeviceId;
	}
	
	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	public void setDeviceId(long nDeviceId) {
		this.nDeviceId = nDeviceId;
	}
	/********************/
	
	
	public CommonTree getTree() {
		return this.tree;
	}

	public Token getToken() {
		return this.token;
	}

	public String toStringTree() {
		return this.tree.toStringTree();
	}


//	public Device getDevice() {
//		return this.objDevice;
//	}

	public String getOperator() {
		return this.operator;
	}

	public String getRule() {
		if (null != this.tree) {
			return this.tree.toStringTree();
		}
		return (String) null;
	}

	@Override
	public String toString() {
		/*** EUGENE v1.9 ***/
		StringBuilder sb = new StringBuilder();
		sb.append("Rule ").append(this.sName).append(" (");

		if (null != this.device) {
			sb.append("ON ").append(this.device.getName()).append(": ");
		}
		sb.append(this.predicate.toString());
		
		/**
		if (null != this.token) {
			sb.append(this.tree.toStringTree());
		} else {

			if (null != this.tree) {
				sb.append(this.tree.toStringTree());
			}
		}
		**/
		sb.append(")");
		return sb.toString();
	}

	@Override
	public boolean equals(NamedElement objElement) {
		// TODO Auto-generated method stub
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

	@Override
	public void assign(NamedElement objElement) {
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		throw new EugeneException(
				"A rule does not support assignments!");
	}

	@Override
	public void set(String sRuleName, NamedElement objElement)
			throws EugeneException {
		throw new EugeneException(
				"A rule does not support assignments!");
	}
}
