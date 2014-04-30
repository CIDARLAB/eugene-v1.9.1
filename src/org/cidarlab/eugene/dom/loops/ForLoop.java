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

package org.cidarlab.eugene.dom.loops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.antlr.runtime.Token;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.StackElement;


/**
 * 
 * @author Ernst Oberortner
 */
public class ForLoop implements StackElement {

	private Token lstStatements;
	private int nOldPosition;
	private LinkedHashMap<String, NamedElement> hmInitVariables;

	public ForLoop() {
		this.lstStatements = null;
		nOldPosition = 0;
		this.hmInitVariables = null;
	}

	public void setInitVariables(List<NamedElement> lstInitVariables) {
		this.hmInitVariables = new LinkedHashMap<String, NamedElement>(
				lstInitVariables.size());
		for (int i = 0; i < lstInitVariables.size(); i++) {
			NamedElement objElement = lstInitVariables.get(i);
			this.hmInitVariables.put(objElement.getName(), objElement);
		}
	}

	public ArrayList<NamedElement> getInitVariables() {
		if (this.hmInitVariables == null) {
			return null;
		}

		ArrayList<NamedElement> lst = new ArrayList<NamedElement>(
				this.hmInitVariables.size());
		Iterator<NamedElement> it = this.hmInitVariables.values().iterator();
		while (it.hasNext()) {
			lst.add(it.next());
		}

		return lst;
	}

	public void setOldPosition(int nOldPosition) {
		this.nOldPosition = nOldPosition;
	}

	public int getOldPosition() {
		return nOldPosition;
	}

	public boolean contains(String sName) {
		if (sName == null || sName.isEmpty()) {
			return false;
		}
		if (hmInitVariables != null && hmInitVariables.containsKey(sName)) {
			return true;
		}

		return SymbolTables.contains(sName);
	}

	public NamedElement get(String sName) {
		if (sName == null || sName.isEmpty()) {
			return null;
		}

		if (hmInitVariables.containsKey(sName)) {
			return hmInitVariables.get(sName);
		}

		return (NamedElement) null;
	}

	public void setStatements(Token lstStatements) {
		this.lstStatements = lstStatements;
	}

	public Token getStatements() {
		return this.lstStatements;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("for(");
		if (this.hmInitVariables != null) {
			Iterator<String> it = this.hmInitVariables.keySet().iterator();
			while (it.hasNext()) {
				sb.append(it.next());
				if (it.hasNext()) {
					sb.append(", ");
				}
			}
		}
		sb.append(")");
		return sb.toString();
	}

	// the clear function removes all declared elements within one loop
	// iteration
	// i.e the clear function gets invoked after every loop iteration
	public void clear() {
//		System.out.println("[ForLoop] clear");
		SymbolTables.clear(String.valueOf(this.hashCode()+SymbolTables.stackSize()));
	}

	// the finalCleanUp() function removes all loop variables from the symbol
	// tables
	// i.e. the finalCleanUp() function gets invoked after the loop has been
	// executed
	public void finalCleanUp() {
		if (null != this.hmInitVariables) {
			// remove the init variables from the symbol tables
			for (String s : this.hmInitVariables.keySet()) {
				SymbolTables.remove(this.hmInitVariables.get(s).getName());
			}

			// clear the hashmap
			this.hmInitVariables.clear();

			// set the hashmap to null
			this.hmInitVariables = null;
		}
	}
}
