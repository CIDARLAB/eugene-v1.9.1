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

package org.cidarlab.eugene.data.sbol.mapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.Property;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLRootObject;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.StrandType;
import org.sbolstandard.core.util.SequenceOntology;

/*
 *   Mapping:
 *   
 *   basic DNACompomonent (ie a DNAComponent without SequenceAnnotations)   <-->  Part
 *   composite DNAComponent (ie a DNAComponent with SequenceAnnotations)    <-->  Device
 *   Collection                                                             <-->  Device[], Collection
 *
 */

public class SBOL2Eugene {

	private static List<Property> lstProperties = null;

	/*
	 * the convert() method gets as input the SBOLRootObject (from the SBOLDocument)
	 * and returns a corresponding Eugene object ... 
	 * 
	 * all classes in Eugene inherit (directly or indirectly) from the NamedElement class
	 * 
	 */
	public static NamedElement convert(SBOLRootObject sbolComponent)
			throws EugeneException {

		if (null == lstProperties) {
			createSBOLProperties();
		}

		if (null != sbolComponent) {
			/*
			 * SBOL Collection --> Eugene Collection
			 */
			if (sbolComponent instanceof org.sbolstandard.core.impl.CollectionImpl) {
				org.sbolstandard.core.impl.CollectionImpl sbolCollection = 
						(org.sbolstandard.core.impl.CollectionImpl) sbolComponent;

				Set<CollectionElement> setCollectionElements = new HashSet<CollectionElement>();
				
				/*
				 * here, we iterate over the SBOL Collection's elements
				 */
				for (DnaComponent dc : sbolCollection.getComponents()) {

					/*
					 * every SBOL Collection element gets converted into a
					 * Eugene NamedElement 
					 */
					NamedElement objElement = SBOL2Eugene.convert(dc);
					
					if (null != objElement && objElement instanceof CollectionElement) {
						
						/*
						 * every SBOL 
						 */
						setCollectionElements.add((CollectionElement) objElement);
					}
				}

				/*
				 * finally we create the Eugene Collection object
				 * following the Builder design pattern
				 * 
				 * Builder pattern:
				 * Separate the construction of a complex object from its representation
				 * so that the same construction process can create different representations
				 */
				return EugeneBuilder.buildCollection(
						sbolCollection.getDisplayId(), setCollectionElements);

			/*
			 * SBOL DnaComponent
			 */
			} else if (sbolComponent instanceof org.sbolstandard.core.impl.DnaComponentImpl) {
				org.sbolstandard.core.impl.DnaComponentImpl sbolDC = (org.sbolstandard.core.impl.DnaComponentImpl) sbolComponent;

				
				if (null != sbolDC.getAnnotations()
						&& !sbolDC.getAnnotations().isEmpty()) {
					
					/*
					 * SBOL composite DNAComponent --> Eugene Device
					 */
					return buildDevice(sbolDC);
					
				} else {
					/*
					 *  if the SBOL component does not have any annotations, then we map it onto a 
					 *  Eugene Part
					 */

					if(null != sbolDC.getDnaSequence()) {
						/*
						 * SBOL DNAComponent -> Eugene Part
						 */
						return buildPart(sbolDC);
					} else {
						/*
						 * SBOL DNAComponent -> Eugene Part Type
						 */
						return buildPartType(sbolDC);
					}
					
				}
			}
		}

		return (NamedElement) null;
	}

	
	private static Device buildDevice(
			org.sbolstandard.core.impl.DnaComponentImpl sbolDC)
		throws EugeneException {
		
		// if the SBOL component does have annotations, then we transform it into a 
		// Eugene Device

		List<org.cidarlab.eugene.dom.components.Component> lstDeviceElements = 
				new ArrayList<org.cidarlab.eugene.dom.components.Component>(sbolDC.getAnnotations().size());

		// for a device, we have to iterate over all sequence
		// annotations and
		// convert each annotated DNA component
		char[] directions = new char[sbolDC.getAnnotations().size()];
		int i=0;
		for (SequenceAnnotation sa : sbolDC.getAnnotations()) {
			NamedElement objElement = SBOL2Eugene.convert(sa.getSubComponent());
			if (null != objElement && 
					objElement instanceof org.cidarlab.eugene.dom.components.Component) {
				SymbolTables.put(objElement);
				lstDeviceElements.add((org.cidarlab.eugene.dom.components.Component) objElement);
			}
			if(sa.getStrand() == StrandType.NEGATIVE) {
				directions[i++] = '-';
			} else {
				directions[i++] = '+';
			}
			
		}
		
		/*
		 * get the directions
		 */
		return EugeneBuilder.buildDevice(sbolDC.getDisplayId(),
				lstDeviceElements, directions);
	}
	
	private static Part buildPart(
			org.sbolstandard.core.impl.DnaComponentImpl sbolDC)
		throws EugeneException {

		
		/*
		 * let's get the part type
		 */
		PartType pt = getPartType(sbolDC.getTypes().get(0).toString());

		/*
		 * finally, we set the part's property values
		 */
		List<PropertyValue> lstValues = getPartPropertyValues(sbolDC);
		
		/*
		 * next, we create the Part object
		 */		
		Part objPart = EugeneBuilder.buildPart(
				sbolDC.getDisplayId(), 
				lstValues, 
				pt);

		/*
		 * and store it in the symbol tables
		 */
		SymbolTables.put(objPart);

		return objPart;
	}
	
	private static List<PropertyValue> getPartPropertyValues(
			org.sbolstandard.core.impl.DnaComponentImpl sbolDC)
					throws EugeneException {
		
		List<PropertyValue> lstValues = new ArrayList<PropertyValue>();
		
		/*
		 * displayId
		 */
		PropertyValue objDisplayId =
				EugeneBuilder.buildPropertyValue(
						EugeneConstants.DISPLAY_ID_PROPERTY,
						EugeneBuilder.buildVariable(
								(null!=sbolDC.getDisplayId())?sbolDC.getDisplayId():""));
		lstValues.add(objDisplayId);
		
		/*
		 * name
		 */
		PropertyValue objName =
				EugeneBuilder.buildPropertyValue(
						EugeneConstants.NAME_PROPERTY,
						EugeneBuilder.buildVariable(
								(null!=sbolDC.getName())?sbolDC.getName():""));
		lstValues.add(objName);
		
		/*
		 * URI
		 */
		PropertyValue objURI = EugeneBuilder
				.buildPropertyValue(
						EugeneConstants.URI_PROPERTY,
						EugeneBuilder.buildVariable(
								(null!=sbolDC.getURI())?sbolDC.getURI().toString():""));
		lstValues.add(objURI);
		
		/*
		 * description
		 */
		PropertyValue objDescription =
				EugeneBuilder.buildPropertyValue(
						EugeneConstants.DESCRIPTION_PROPERTY,
						EugeneBuilder.buildVariable(
								(null!=sbolDC.getDescription())?sbolDC.getDescription():""));
		lstValues.add(objDescription);
		
		/*
		 * SEQUENCE
		 */
		PropertyValue objSequenceValue = 
				EugeneBuilder.buildPropertyValue(
						EugeneConstants.SEQUENCE_PROPERTY,
						EugeneBuilder.buildVariable(
								(sbolDC.getDnaSequence()!=null)?sbolDC.getDnaSequence().getNucleotides():""));
		lstValues.add(objSequenceValue);
		
		return lstValues;
	}
	
	private static PartType buildPartType(
			org.sbolstandard.core.impl.DnaComponentImpl sbolDC)
					throws EugeneException {
		PartType pt = getPartType(sbolDC.getTypes());
		if(null != pt) {
			SymbolTables.put(pt);
		}
		return pt;
	}
	
	private static PartType getPartType(List<URI> types) 
		throws EugeneException {
		
		PartType pt = getPartType(types.get(0).toString());
		if(null != pt) {
			return pt;
		}
		
		/*
		 * we need to create the part type
		 */
		return getPartType(types.get(0).toString());
	}
	
	private static PartType getPartType(String type) throws EugeneException {
		String sPartTypeName = soMapping(type);

		PartType objPartType = (PartType) null;
		NamedElement objTmp = SymbolTables.get(sPartTypeName);
		if (null != objTmp && objTmp instanceof PartType) {
			return (PartType) objTmp;
		} else {
			objPartType = EugeneBuilder.buildPartType(
					sPartTypeName,
					lstProperties);
			SymbolTables.put(objPartType);
		}
		return objPartType;
	}

	private static String soMapping(String s) {

		if (SequenceOntology.FIVE_PRIME_UTR.toString().equals(s)) {
			return "Five_Prime_UTR";
		} else if (SequenceOntology.CDS.toString().equals(s)) {
			return "CDS";
		} else if("http://purl.obolibrary.org/obo/SO_0000139".equals(s) ||
				"http://purl.obolibrary.org/obo/SO_0000552".equals(s)) {
			return "RBS";
		} else if (SequenceOntology.INSULATOR.toString().equals(s)) {
			return "Insulator";
		} else if (SequenceOntology.OPERATOR.toString().equals(s)) {
			return "Operator";
		} else if (SequenceOntology.ORIGIN_OF_REPLICATION.toString().equals(s)) {
			return "Origin_of_Replication";
		} else if (SequenceOntology.PRIMER_BINDING_SITE.toString().equals(s)) {
			return "Primiter_Binding_Site";
		} else if (SequenceOntology.PROMOTER.toString().equals(s)) {
			return "Promoter";
		} else if (SequenceOntology.RESTRICTION_ENZYME_RECOGNITION_SITE
				.toString().equals(s)) {
			return "Restriction_Enzyme_Recognition_Site";
		} else if (SequenceOntology.TERMINATOR.toString().equals(s) ||
				"http://purl.obolibrary.org/obo/SO_0000313".equals(s)) {
			return "Terminator";
		} else if("Promoter".equals(s)) {
			return "Promoter";
		} else if("CDS".equals(s)) {
			return "CDS";
		}

		return EugeneConstants.SBOL_PART_TYPE;
	}

	public static void readURI(URI uri) throws Exception {
		URL url = uri.toURL();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}

	
	private static void createSBOLProperties() 
			throws EugeneException {
		if (null == lstProperties) {
			
			lstProperties = new ArrayList<Property>(6);
			
			/*
			 * URI
			 */
			lstProperties.add(
					EugeneBuilder.buildProperty(
							EugeneConstants.URI_PROPERTY, 
							EugeneConstants.TXT));
			
			/*
			 * displayId
			 */
			lstProperties.add(
					EugeneBuilder.buildProperty(
							EugeneConstants.DISPLAY_ID_PROPERTY, 
							EugeneConstants.TXT));
			
			/*
			 * name
			 */
			lstProperties.add(
					EugeneBuilder.buildProperty(
							EugeneConstants.NAME_PROPERTY, 
							EugeneConstants.TXT));
			
			/*
			 * description
			 */
			lstProperties.add(
					EugeneBuilder.buildProperty(
							EugeneConstants.DESCRIPTION_PROPERTY, 
							EugeneConstants.TXT));
			
			/*
			 * type(s) 
			 */
			lstProperties.add(
					EugeneBuilder.buildProperty(
							EugeneConstants.TYPE_PROPERTY, 
							EugeneConstants.TXTLIST));
			
			/*
			 * Sequence
			 */
			lstProperties.add(
					EugeneBuilder.buildProperty(
							EugeneConstants.SEQUENCE_PROPERTY, 
							EugeneConstants.TXT));
			
			/*
			 * put the properties into the symbol tables
			 */
			for (Property objProperty : lstProperties) {
				if (!SymbolTables.contains(objProperty.getName())) {
					SymbolTables.put(objProperty);
				}
			}
			
		}
	}

	private static Property getSequenceProperty() {
		// every part type should have a sequence property
		Property objSequenceProperty = (Property) SymbolTables
				.get(EugeneConstants.SEQUENCE_PROPERTY);
		if (null == objSequenceProperty) {
			objSequenceProperty = EugeneBuilder.buildProperty(
					EugeneConstants.SEQUENCE_PROPERTY, EugeneConstants.TXT);
		}
		return objSequenceProperty;
	}

}
