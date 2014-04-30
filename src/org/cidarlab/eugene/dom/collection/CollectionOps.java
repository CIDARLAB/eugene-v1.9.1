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

package org.cidarlab.eugene.dom.collection;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

public class CollectionOps {
	public static EugeneCollection union(EugeneCollection col1, EugeneCollection col2) {
		if (null == col1 || null == col2) {
			return null;
		}

		EugeneCollection objCollection = new EugeneCollection(col1.getName() + "_"
				+ col2.getName());

		HashSet<CollectionElement> s = new HashSet<CollectionElement>();
		s.addAll(col1.getElements());
		s.addAll(col2.getElements());
		objCollection.setElements(s);
		return objCollection;
	}

	public static org.cidarlab.eugene.dom.collection.EugeneCollection minus(
			org.cidarlab.eugene.dom.collection.EugeneCollection col1,
			org.cidarlab.eugene.dom.collection.EugeneCollection col2) {
		if (null == col1 || null == col2) {
			return null;
		}

		EugeneCollection objCollection = new EugeneCollection(col1.getName() + "-"
				+ col2.getName());

		java.util.Collection objCol = CollectionUtils.subtract(
				col1.getElements(), col2.getElements());

		// TODO: add the objCol elements to a hash map
		objCollection.setElements((Set<CollectionElement>) objCol);

		// objCollection.setElements(objCol);
		return objCollection;
	}
}
