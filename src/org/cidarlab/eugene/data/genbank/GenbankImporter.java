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

package org.cidarlab.eugene.data.genbank;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.biojava.bio.seq.SequenceIterator;
import org.biojava.bio.seq.io.SeqIOTools;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;


public class GenbankImporter {

	public static Part importPart(PartType objPartType, String sPartName)
			throws Exception {

		// load the genbank file
		String sGenBank = getGenBank(sPartName);

		// convert the string to
		InputStream is = new ByteArrayInputStream(sGenBank.getBytes());

		// now, use BioJava's feature to read the GenBank data
		String sPartSeq = readGenBank(new BufferedReader(new InputStreamReader(
				is)));

		Part objPart = new Part(objPartType, sPartName);

		PropertyValue pv = new PropertyValue("Sequence", EugeneConstants.TXT);
		pv.setTxt(sPartSeq);
		objPart.setPropertyValue("Sequence", pv);

		objPart.setSequence(sPartSeq);

		return objPart;
	}

	private static String getGenBank(String sPartName) throws Exception {
		URL url = new URL("http://cambridgeigem.org/gbdownload/BBa_"
				+ sPartName + ".gb");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String inputLine;
		StringBuilder sb = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine).append(System.getProperty("line.separator"));
		}
		in.close();

		return sb.toString();
	}

	private static String readGenBank(BufferedReader br) throws Exception {

		SequenceIterator it = (SequenceIterator) SeqIOTools.fileToBiojava(
				"GENBANK", "DNA", br);
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			sb.append(it.nextSequence().seqString());
		}
		return sb.toString();
	}
}
