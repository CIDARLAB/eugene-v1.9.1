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

import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.components.Part;

import scala.util.parsing.json.JSONObject;

/**
 * 
 * @author Ernst Oberortner
 */
public class PropertyValue 
	extends Variable {

	private static final long serialVersionUID = 2928874575715925000L;
//	private Part objPart;

	public PropertyValue() {
		super(null, null);
	}

	public void setValue(String value) {
		//System.out.println("[PropertyValue.setValue] -> "+value+" -> "+this.getType());
	}
	
//	public PropertyValue(PropertyValue value) {
//		super(value.getName(), value.getType());
//
//		this.setNum(value.getNum());
//		this.setNumList(value.getNumList());
//		this.setTxt(value.getTxt());
//		this.setTxtList(value.getTxtList());
//		this.setBoolean(value.getBoolean());
//		
//	}
	
	public PropertyValue(String s) {
		super(null, null);
		System.out.println(s);
	}
	
	public PropertyValue(String name, String type) {
		super(name, type);
//		this.objPart = null;
	}

//	public PropertyValue(String name, String type, Part objPart) {
//		super(name, type);
//		this.objPart = objPart;
//	}

//	public void setPart(Part objPart) {
//		this.objPart = objPart;
//	}

//	public Part getPartInstance() {
//		return this.objPart;
//	}

	public void setName(String sName) {
		this.sName = sName;
	}

	public boolean compareTo(PropertyValue objPropertyValue) {
		if (this == objPropertyValue) {
			return true;
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getValue());

		/**
		 * sb.append(".").append(this.sName).append("(");
		 * if(EugeneConstants.TXT.equals(this.getType())) {
		 * sb.append("\"").append(this.getValue()).append("\""); } else {
		 * sb.append(this.getValue()); } sb.append(")");
		 **/

		return sb.toString();
	}
}
