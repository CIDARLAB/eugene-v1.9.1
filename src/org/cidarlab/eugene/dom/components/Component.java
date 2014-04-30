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
import java.util.List;

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.dom.relation.Participant;
import org.cidarlab.eugene.exception.EugeneException;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * 
 * @author Ernst Oberortner
 */
@JsonDeserialize
public abstract class Component 
	extends CollectionElement 
	implements Participant {

	private static final long serialVersionUID = 6045834162841472128L;
	private String sSequence;
	protected List<Property> lstProperties; 
	private char direction;
	
	protected Component() {
		super();
		this.sSequence = new String();
		this.lstProperties = new ArrayList<Property>();
		this.direction = '+';
	}

	public Component(String sName) {
		super(sName);
		this.sSequence = new String();
		this.lstProperties = new ArrayList<Property>();
		this.direction = '+';
	}
	
	public Component(String sName, List<Property> lstProperties) {
		super(sName);
		this.lstProperties = lstProperties;
		this.sSequence = new String();
		this.direction = '+';
	}

	public void setSequence(String sSequence) {
		this.sSequence = sSequence;
	}

	public String getSequence() {
		return this.sSequence;
	}
	
	
	/** PROPERTY METHODS **/
	public void addProperty(Property objProperty) {
		if (null != objProperty) {
			this.lstProperties.add(objProperty);
		}
	}

	public void addProperties(List<Property> lst) {
		this.lstProperties.addAll(lst);
	}
	
	public void setProperties(List<Property> lst) {
		this.lstProperties = new ArrayList<Property>(lst);
	}
	
	public List<Property> getProperties() {
		return this.lstProperties;
	}

	public void setDirection(char direction) {
		if(direction == '+' || '-' == direction) {
			this.direction = direction;
		}
	}
	
	public char getDirection() {
		return this.direction;
	}
	
	public Property getProperty(String sPropertyName) {
		for (Property p : lstProperties) {
			if (p.getName().equals(sPropertyName)) {
				return p;
			}
		}
		return (Property) null;
	}

	// returns the i-th Property
	public Property getProperty(int idx) {
		if (idx >= 0 && idx < this.lstProperties.size()) {
			return this.lstProperties.get(idx);
		}
		return null;
	}

	
	/*** ABSTRACT METHODS ***/
	public abstract String toSequence() throws EugeneException;
	public abstract int size();
	public abstract boolean contains(NamedElement objElement);
	public abstract void add(NamedElement objElement)
			throws EugeneException;
	public abstract boolean isInstanceOf(String sType);
}
