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

package org.cidarlab.eugene.dom.branches;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.StackElement;
import org.cidarlab.eugene.dom.components.Device;


public class ConditionalBranch 
		implements StackElement {

	private Token condition;
	private Token statements;

	public ConditionalBranch(Token condition, Token statements) {
		this.condition = condition;
		this.statements = statements;
	}

	public Token getCondition() {
		return this.condition;
	}

	public Token getStatements() {
		return this.statements;
	}

	public NamedElement get(String sName) {
		return (NamedElement) null;
	}

	public void clear() {
		//System.out.println("[branch.clear] -> "+this + " -> " + this.hashCode());
		SymbolTables.clear(String.valueOf(this.hashCode()+SymbolTables.stackSize()));
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}
}
