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

package org.cidarlab.eugene.data.sbol;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.data.sbol.mapping.SBOL2Eugene;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.exception.EugeneException;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLRootObject;


/**
 * 
 * @author Ernst Oberortner
 */
public class SBOLImporter {

	public static NamedElement importSBOL(String sFileName)
			throws EugeneException {
		/*
		 * an SBOLDocument object keeps the SBOL File's information in memory
		 */
		SBOLDocument newDocument;
		
		try {
			/*
			 * read the SBOL File into memory
			 */
			newDocument = SBOLFactory.read(
					new FileInputStream(sFileName));

			if (null != newDocument && 
					null != newDocument.getContents() && 
					!newDocument.getContents().isEmpty()) {

				if(newDocument.getContents().size() > 1) {
					/*
					 * Collection
					 */
					Collection<CollectionElement> collection = new HashSet<CollectionElement>();

					// first, create the SBOL properties
					for (SBOLRootObject sbolObj : newDocument.getContents()) {
						collection.add((CollectionElement)SBOL2Eugene.convert(sbolObj));
					}
					
					return EugeneBuilder.buildCollection("sbol", collection);					
					
				} else {
					
					/*
					 * NamedElement
					 */
					NamedElement objElement = SBOL2Eugene.convert(newDocument.getContents().get(0));

					if (null != objElement) {
						return objElement;
					}
				}
			}

		} catch (Exception e) {
			throw new EugeneException(e.getMessage());
		}
		
		return null;
	}
}
