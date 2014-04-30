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

import java.io.File;
import java.io.FileOutputStream;

import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.data.sbol.mapping.Eugene2SBOL;
import org.cidarlab.eugene.dom.arrays.ComponentArray;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.dom.collection.EugeneCollection;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;


/**
 * 
 * @author Ernst Oberortner
 */
public class SBOLExporter {
	public static void serialize(ComponentArray objArray, String sFileName)
			throws EugeneException {
		if (sFileName.startsWith("\"") && sFileName.endsWith("\"")) {
			sFileName = sFileName.substring(1, sFileName.length() - 2);
		}

		try {
			org.sbolstandard.core.Collection sbolCollection = 
					Eugene2SBOL.convert(objArray, EugeneConstants.EUGENE_URL);

			// create an empty document populated with some SBOL objects
			SBOLDocument document = SBOLFactory.createDocument();

			// add the DnaComponent to this document
			document.addContent(sbolCollection);

			FileOutputStream fos;
			File f = new File(sFileName);
//			System.out.println(sFileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			fos = new FileOutputStream(f);

			//System.out.println(document);
			
			SBOLFactory.write(document, fos);

			fos.flush();
			fos.close();
			fos = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EugeneException(e.getMessage());
		}
	}

	public static boolean serialize(Device objDevice, String sFileName) {
		try {
			FileOutputStream fos;
			DnaComponent dnaComponent = 
					Eugene2SBOL.convert(objDevice, null, 0);
			// create an empty document populated with some SBOL objects
			SBOLDocument document = SBOLFactory.createDocument();
			// add the DnaComponent to this document
			document.addContent(dnaComponent);

			//System.out.println(document);
			
			fos = new FileOutputStream(new File(sFileName));

			SBOLFactory.write(document, fos);
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		return true;
	}

	public static boolean serialize(EugeneCollection objCollection, String sFileName) 
			throws EugeneException {
		try {
			FileOutputStream fos;
			
			// create an empty document populated with the SBOL objects
			// from the given collection
			SBOLDocument document = SBOLFactory.createDocument();

			// add the DnaComponent to this document
			document.addContent(Eugene2SBOL.convert(objCollection, EugeneConstants.EUGENE_URL));

			fos = new FileOutputStream(new File(sFileName));
			SBOLFactory.write(document, fos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EugeneException(e.getMessage());
		}

		return true;
	}

}
