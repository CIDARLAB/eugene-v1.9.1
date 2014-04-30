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

package org.cidarlab.eugene.output;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.SavableElement;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;


public class ResultSet {
	private LinkedHashMap<String, SavableElement> hmComponents;

	public ResultSet() {
		this.hmComponents = new LinkedHashMap<String, SavableElement>();
	}

	public boolean contains(SavableElement objComponent) {
		return hmComponents.containsValue(objComponent);
	}

	public boolean contains(String sComponent) {
		return hmComponents.containsKey(sComponent);
	}

	public void save(String sName, SavableElement objElement) 
			throws EugeneException {
		if (null != sName && null != objElement) {
			if (contains(sName)) {
				hmComponents.remove(sName);
			}

			this.hmComponents.put(sName, objElement);

//			if (objElement instanceof DeviceArray) {
//				DeviceArray objArray = (DeviceArray) objElement;
//				
//				for(int i=0; i<objArray.size(); i++) {
//					Device d = (Device)objArray.get(i);
//					this.hmComponents.put(d.getName(), d);
//				}
//
////				for (String s : objArray.getDeviceNames()) {
////					this.hmComponents.put(s,
////							(SavableElement) SymbolTables.get(s));
////				}
//			
//			} else {
//				this.hmComponents.put(sName, objElement);
//			}
		}
	}

	public SavableElement get(String sComponent) {
		return this.hmComponents.get(sComponent);
	}

	public HashMap<String, SavableElement> getResultSet() {
		return this.hmComponents;
	}

	public void clear() {
		if (null != this.hmComponents) {
			hmComponents.clear();
			hmComponents = null;
		}
	}

	public HashMap<String, SavableElement> getResults() {
		return this.hmComponents;
	}
}
