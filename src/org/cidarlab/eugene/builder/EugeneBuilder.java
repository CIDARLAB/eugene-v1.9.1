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

package org.cidarlab.eugene.builder;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.Variable;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.arrays.GeneratedDeviceArray;
import org.cidarlab.eugene.dom.arrays.PermutedDeviceArray;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.dom.collection.EugeneCollection;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.Property;
import org.cidarlab.eugene.dom.components.types.DeviceType;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.tree.RuleTreeParser;
import org.cidarlab.eugene.rules.tree.predicate.Predicate;

import JaCoP.core.Domain;

import com.rits.cloning.Cloner;


public class EugeneBuilder {

	private static Cloner cloner = new Cloner();

	/**
	 * public EugeneBuilder() { this.cloner = new Cloner(); }
	 **/

	public static Property buildProperty(String sName, String sType) {
		return new Property(sName, sType);
	}

	public static Variable buildVariable(String sName, String sType) {
		return new Variable(sName, sType);
	}

	public static PropertyValue buildPropertyValue(
			String sName, Variable objVariable) {
		PropertyValue objPropertyValue = new PropertyValue(sName, objVariable.getType());
		objPropertyValue.setValue((Variable) objVariable);
		return objPropertyValue;
	}

	public static PropertyValue buildPropertyValue(String sName,
			PropertyValue objValue) throws EugeneException {
		PropertyValue objPropertyValue = new PropertyValue(sName,
				objValue.getType());
		objPropertyValue.assign(objValue);
		return objPropertyValue;
	}

	public static Device buildDevice(
			String sName, List<Component> lstComponents, char[] directions)
			throws EugeneException {
		
//		System.out.println("[EugeneBuilder.buildDevice] -> "+sName+", "+Arrays.toString(directions));
		
//		if(null != lstComponents) {
//			/* here, we iterate over the devices components and start building the device graph */
//			long[] components = new long[lstComponents.size()];
//			int i=0;
//			for(Component component : lstComponents) {
//				//System.out.println("[EugeneBuilder.buildDevice] -> "+
//				//		component.getName()+", "+directions[i]);
//				components[i++] = SymbolTables.getId(component.getName());
//			}
//		}
//		Device d = new Device(sName, lstComponents);
		
		Device device = new Device(sName, lstComponents, null, directions);
//		System.out.println("[EugeneBuilder.buildDevice] -> "+device);
		return device;
	}

	public static PermutedDeviceArray buildPermutedDeviceArray(
			Device device, long[][] valid_combinations) {
		return new PermutedDeviceArray(device, valid_combinations);
	}
	
	public static Device buildDevice(
			String sName, 
			long[] lstComponents, 
			char[] directions) 
					throws EugeneException {
		
//		System.out.println("[EugeneBuilder.buildDevice] -> "+sName+", "+Arrays.toString(lstComponents));
		
		Device device = new Device(sName);
		if(null != lstComponents && lstComponents.length > 0) {
			for(long component : lstComponents) {
				NamedElement element = SymbolTables.getComponent(component);
				device.add(element);
			}
		}
		device.setDirections(directions);
		
		return device;
	}

	public static Device buildDevice(
			String sName, 
			List<Component> lstComponents, 
			List<Property> lstProperties, 
			char[] directions) 
					throws EugeneException {
		
		if(null != directions) {
			for(int i=0; i<directions.length; i++) {
				if('-' == directions[i]) {
					Component component = lstComponents.get(i);
					
					/*
					 * if the current component is a device, 
					 * then we reverse the device's elements
					 */
					if(component instanceof Device) {
						((Device)component).reverseComponents();
					}
				}
			}
		}
		return new Device(sName, lstComponents, lstProperties, directions);		
	}

	public static DeviceType buildDeviceType(String sName,
			List<NamedElement> lstElements) throws EugeneException {
		return new DeviceType(sName, lstElements);
	}

	public static Rule buildRule(
			String sName, Device objDevice, Token token, CommonTree tree) 
					throws EugeneException {
		
		/** iterate over the tree and build the appropriate predicates **/
		Predicate predicate = null;
		if (null != tree) {
			try {
				predicate = RuleTreeParser.buildTree(tree);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EugeneException(e.getMessage());
			}
		} else {
			throw new EugeneException("No rule tree provided!");
		}

		/** and finally assign the rules to the device **/
		/** => making it later easier to retrieve one device's rules **/
		
		
		if(null != predicate) { 
			return new Rule(sName, objDevice, predicate);
		}
		return null;
	}

	public static Rule buildRule(
			String sName, long nDeviceId, Token token, CommonTree tree) 
					throws EugeneException {
		
		/** iterate over the tree and build the appropriate predicates **/
		Predicate predicate = null;
		if (null != tree) {
			try {
				predicate = RuleTreeParser.buildTree(tree);
			} catch (Exception e) {
				throw new EugeneException(e.getMessage());
			}
		} else {
			throw new EugeneException("No rule tree provided!");
		}

		/** and finally assign the rules to the device **/
		/** => making it later easier to retrieve one device's rules **/
		//System.out.println("[EugeneBuilder.buildRule] -> ON "+nDeviceId+": "+predicate);
		if(null != predicate) {
			return new Rule(sName, nDeviceId, predicate);
		}
		return null;
	}
	
	public static PartType buildPartType(
			String sName, List<Property> lstProperties) {
		PartType objPartType = new PartType(sName);
		if (null != lstProperties && !lstProperties.isEmpty()) {
			for (Property p : lstProperties) {
				objPartType.addProperty(cloner.deepClone(p));
			}
		}
		return objPartType;
	}

	public static Part buildPart(String sName, List<PropertyValue> lstValues,
			PartType objPartType) throws EugeneException {

		Part objPart =new Part(objPartType, sName);

		if (null != lstValues && !lstValues.isEmpty()) {
			// assign the property values to the part
			for (PropertyValue objValue : lstValues) {
				objPart.setPropertyValue(objValue.getName(), objValue);
			}
		}

		return objPart;
	}

	public static DeviceArray buildDeviceArray(String sName, List<Device> lstDevices) {
		if(null != lstDevices && !lstDevices.isEmpty()) {
			List<String> lstComponentNames = new ArrayList<String>(lstDevices.size());
			for(Component comp : lstDevices) {
				lstComponentNames.add(comp.getName());
			}
			return new DeviceArray(sName, (ArrayList<String>)lstComponentNames);
		}
		return new DeviceArray(sName);
	}
	
	/*
	 * buildDeviceArray ... constructs a device array for Eugene's product/permute functions
	 * 
	 * @param device    ... the abstract device that got produced/permuted... 
	 *                      therefore, we keep the ``hierachy'' information of the devices 
	 * @param solutions ... solutions that map to the part ids in the database 
	 *                      according to the devices ``hierarchy'' 
	 */
	public static DeviceArray buildDeviceArray(Device device, Domain[][] solutions) {
		if(null != device) {
			return new GeneratedDeviceArray(device, solutions);
		}
		return null;
	}
	
	public static GeneratedDeviceArray unionDeviceArrays(
			GeneratedDeviceArray leftElement, GeneratedDeviceArray rightElement) 
			throws EugeneException {
		GeneratedDeviceArray gda = new GeneratedDeviceArray(leftElement);
		gda.add(rightElement);
//		System.err.println("[EugeneBuilder.unionDeviceArrays] -> "+leftElement.size()+" + "+rightElement.size()+" -> "+gda.size());
		return gda;
	}
			
	public static EugeneCollection buildCollection(
			String sName,
			java.util.Collection<CollectionElement> setElements) {
		
		EugeneCollection objCollection = new EugeneCollection(sName);
		if (null != setElements && !setElements.isEmpty()) {
			for (CollectionElement objElement : setElements) {
				objCollection.add(objElement);
			}
		}
		
		return objCollection;
	}

	public static Variable buildVariable(String sValue) {
		Variable objVar = null;
		if (null == sValue) {
			return (Variable) null;
		}

		// txt
		if (sValue.startsWith("\"") && sValue.endsWith("\"")) {
			objVar = new Variable(null, EugeneConstants.TXT);
			objVar.setTxt(sValue.substring(1, sValue.length() - 1));
			return objVar;
		}

		// bool
		if ("true".equals(sValue.toLowerCase())) {
			objVar = new Variable(null, EugeneConstants.BOOLEAN);
			objVar.setBoolean(true);
			return objVar;
		} else if ("false".equals(sValue.toLowerCase())) {
			objVar = new Variable(null, EugeneConstants.BOOLEAN);
			objVar.setBoolean(false);
			return objVar;
		}

		// num
		try {
			double num = Double.parseDouble(sValue);
			objVar = new Variable(null, EugeneConstants.NUM);
			objVar.setNum(num);
			return objVar;
		} catch (Exception e) {
		}

		// TODO: num[], txt[]

		objVar = EugeneBuilder.buildVariable(null, EugeneConstants.TXT);
		objVar.setTxt(sValue);

		return objVar;
	}
}
