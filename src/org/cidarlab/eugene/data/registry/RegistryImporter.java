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

package org.cidarlab.eugene.data.registry;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.cidarlab.eugene.dom.components.RegistryPart;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 
 * @author Ernst Oberortner
 */
public class RegistryImporter {

	private String url = new String("http://partsregistry.org/xml/part.");
	private XPath xpath;
	private Document doc;
	private DocumentBuilder db;

	public RegistryImporter() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true); // never forget this!
		try {
			db = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public RegistryPart importPart(String sPartName) throws SAXException,
			IOException, XPathExpressionException {
		return this.parseXMLPart(sPartName);
	}

	private RegistryPart parseXMLPart(String sPartName) throws SAXException,
			IOException, XPathExpressionException {
		this.doc = db.parse(this.url + sPartName);
		XPathFactory factory = XPathFactory.newInstance();
		this.xpath = factory.newXPath();

		XPathExpression expr = xpath.compile("//part_list/part/*");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		RegistryPart objPart = null;
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			// System.out.println(node.getLocalName()+" -> "+node.getTextContent());
			
			String sContent = node.getNodeValue().trim();
			String sID = null;
			if (node.getLocalName().equals("part_name")) {
				// System.out.println("partname -> "+sContent);
				objPart = new RegistryPart(sContent);
				objPart.setID(sID);
			} else if (node.getLocalName().equals("part_id")) {
				if (objPart != null) {
					objPart.setID(sContent);
				} else {
					sID = sContent;
				}
			} else if (node.getLocalName().equals("part_short_name")) {
				objPart.setShortName(sContent);
			} else if (node.getLocalName().equals("part_type")) {
				objPart.setPartType(sContent);
			} else if (node.getLocalName().equals("part_url")) {
				objPart.setURL(sContent);
			} else if (node.getLocalName().equals("sequences")) {
				objPart.setDNASequence(sContent.replaceAll("\n", "")
						.toUpperCase());
			} else if (node.getLocalName().equals("ERROR")) {
				return null;
			}
		}

		return objPart;
	}
}
