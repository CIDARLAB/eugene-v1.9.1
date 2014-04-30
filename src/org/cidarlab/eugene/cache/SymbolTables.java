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

package org.cidarlab.eugene.cache;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.StackElement;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.functions.Function;
import org.cidarlab.eugene.dom.relation.Interaction;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.fact.Fact;
import org.cidarlab.eugene.fact.relation.Relation;


/**
 * 
 * @author Ernst Oberortner
 */
public class SymbolTables {
	/*** Symbol Tables ***/

	// hmDefinitions contains the names of all defined
	// Properties, Parts, Devices, Variables, and their instances

	// the objdesignSpace collection contains all components (i.e. Part Types,
	// Parts, and Devices)
	// that have been defined in a Eugene script
	private static Stack<StackElement> objStack;
	private static Stack<String> stCurrentFunction;
	private static DesignSpace designSpace;
	
	public static void init() {
		objStack = new Stack<StackElement>();
		stCurrentFunction = new Stack<String>();
		designSpace = new DesignSpace();
	}

	/***
	public static void executeCypherQuery(String s) 
		throws EugeneException {
		designSpace.executeCypherQuery(s);
	}
	***/
	
	public static java.util.Collection<Rule> getRules() {
		Collection<Rule> colRules = designSpace.getRules();

		if (!objStack.isEmpty()) {
			StackElement objStackElement = SymbolTables.pop();
			colRules.addAll(SymbolTables.getRules());
			SymbolTables.push(objStackElement);
		}

		return colRules;
	}

	public static long[] getDeviceIds() {
		return designSpace.getDeviceIds();
	}
	
	public static java.util.Collection<Device> getDevices() {
		Collection<Device> colDevices = designSpace.getDevices();

		if (!objStack.isEmpty()) {
			StackElement objStackElement = SymbolTables.pop();
			colDevices.addAll(SymbolTables.getDevices());
			SymbolTables.push(objStackElement);
		}

		return colDevices;
	}

	public static java.util.Collection<DeviceArray> getDeviceArrays() {
		Collection<DeviceArray> colDeviceArrays = designSpace.getDeviceArrays();

		if (!objStack.isEmpty()) {
			StackElement objStackElement = SymbolTables.pop();
			colDeviceArrays.addAll(SymbolTables.getDeviceArrays());
			SymbolTables.push(objStackElement);
		}

		return colDeviceArrays;
	}

	public static java.util.Collection<PartType> getPartTypes() {
		Collection<PartType> colPartTypes = designSpace.getPartTypes();

		if (!objStack.isEmpty()) {
			StackElement objStackElement = SymbolTables.pop();
			colPartTypes.addAll(SymbolTables.getPartTypes());
			SymbolTables.push(objStackElement);
		}

		return colPartTypes;
	}

	public static java.util.Collection<Part> getParts() {
		Collection<Part> colParts = designSpace.getParts();

		if (!objStack.isEmpty()) {
			StackElement objStackElement = SymbolTables.pop();
			colParts.addAll(SymbolTables.getParts());
			SymbolTables.push(objStackElement);
		}

		return colParts;
	}

	public static java.util.Collection<Part> getParts(PartType objPartType) {
		Collection<Part> colParts = designSpace.getParts(objPartType);

		if (!objStack.isEmpty()) {
			StackElement objStackElement = SymbolTables.pop();
			colParts.addAll(SymbolTables.getParts(objPartType));
			SymbolTables.push(objStackElement);
		}

		return colParts;
	}

	public static void cleanUp() {
		if (null != objStack) {
			objStack = null;
		}

		if(null != designSpace) {
			designSpace.clear();
			designSpace = null;
		}
		
		if(null != stCurrentFunction) {
			stCurrentFunction = null;
		}
		
		System.gc();
	}

	public static void clear(String sGroup) {
//		System.out.println("[SymbolTables.clear] -> "+sGroup);
		designSpace.clear(sGroup);
	}

//	public static void clearLoop() {
//		
//		// we need to delete:
//		// - variables
//		// - rules
//		// - ???
//		
////		System.out.println("[SymbolTables.clearLoop] -> " + SymbolTables.peek());
//	}
	
	public static boolean contains(String sName) {
		return designSpace.contains(sName);
	}

	public static void put(Fact fact) 
			throws EugeneException {		
		designSpace.put(fact);
	}
	
	public static void put(Interaction interaction) 
			throws EugeneException {
		designSpace.put(interaction);
	}
	
	
	/*** GET() METHODS ***/
	public static NamedElement get(String sName) {

//		System.out.println("[SymbolTables.get] -> "+sName);
		
		NamedElement objElement = designSpace.get(sName);
		if (objElement != null) {
//			System.out.println("SymbolTables.get -> "+objElement.getName());
			return objElement;
		}

		if (null != objStack && !objStack.isEmpty()) {
			StackElement objStackElement = SymbolTables.pop();
			objElement = SymbolTables.get(sName);
			SymbolTables.push(objStackElement);

			if (null != objElement) {
				return objElement;
			}
		}

		return (NamedElement) null;
	}

	public static PartType getPartType(String name) {
		return designSpace.getPartType(name);
	}
	public static Component getComponent(long id) 
		throws EugeneException {
		if(id>0) {
			return designSpace.getComponent(id);
		} else {
			throw new EugeneException(id+" is an invalid ID!");
		}
	}
	
	public static long[][] queryPairs(String relation, Component c1, Component c2) 
			throws EugeneException {
		return designSpace.queryPairs(relation, c1, c2);
	}
	
	public static long getId(String sName) 
			throws EugeneException {
		long id = designSpace.getId(sName);

		if(id > 0) {
			return id;
		}
		throw new EugeneException("I cannot find "+sName);

	}
	
	public static String getNameById(long nId) 
			throws EugeneException {
		if(designSpace.contains(nId)) {
			return designSpace.getNameById(nId);
		} else {
			throw new EugeneException("I cannot find a component with the id "+nId);
		}
	}
	
	public static long[] getDeviceComponentIds(String sName) 
			throws EugeneException {
		//if(designSpace.contains(sName)) {
		long[] lst = designSpace.getDeviceComponentIds(sName);
		if(null == lst) {
		//} else {
			throw new EugeneException("I cannot find "+sName);
		}
		return lst;
	}	
	
	public static List<Interaction> getInteractions(Component component) {
		if(null != component) {
			return designSpace.getInteractions(component);
		}
		return null;
	}

	
	public static Device getDevice(long deviceId) {
//		System.out.println("[SymbolTables.getDevice] -> "+deviceId);
		return designSpace.getDevice(deviceId);
	}

	
//	public static DeviceArray sample(String sDeviceName, int N) 
//			throws EugeneException {
//		return designSpace.sample(sDeviceName, N);
//	}
	
//	public static DeviceArray getInstancesOf(String sDeviceName) {
//		long[] instance_ids = designSpace.getInstancesOf(sDeviceName);
//		
//		return new DeviceArray(sDeviceName+"_INSTANCES", instance_ids);
//	}

	
	public static long[] getDeviceComponentIds(long nDeviceId) 
			throws EugeneException {
		//System.out.println("[SymbolTables.getDeviceComponentIds] -> "+nDeviceId);
		if(designSpace.contains(nDeviceId)) {
			return designSpace.getDeviceComponentIds(nDeviceId);
		} else {
			throw new EugeneException("I cannot find "+SymbolTables.getNameById(nDeviceId));
		}
	}	
	
	public static long[] getAllDeviceComponentIds(long nDeviceId) 
			throws EugeneException {
		//System.out.println("[SymbolTables.getDeviceComponentIds] -> "+nDeviceId);
		if(designSpace.contains(nDeviceId)) {
			return designSpace.getAllDeviceComponentIds(nDeviceId);
		} else {
			throw new EugeneException("I cannot find "+SymbolTables.getNameById(nDeviceId));
		}
	}	

	public static List<Rule> getDeviceRules(long nDeviceId) 
			throws EugeneException {
		if(designSpace.contains(nDeviceId)) {
			return designSpace.getDeviceRules(nDeviceId);
		} else {
			throw new EugeneException("I cannot find "+nDeviceId);
		}
	}
	
//	public static Component getComponentById(long id) {
//		if(designSpace.contains(id)) {
//			return designSpace.getComponent(id);
//		}
//	}
	
	public static List<Rule> getDeviceRules(String sDeviceName) 
			throws EugeneException {
		// first, we need to get the device's id...
		if(designSpace.contains(sDeviceName)) {
			return designSpace.getDeviceRules(sDeviceName);
		} else {
			throw new EugeneException("I cannot find a device named "+sDeviceName);
		}
	}
	
	
	/*** REMOVE() METHODS ***/
	public static void remove(String sName) {
		if (null != sName) {
			designSpace.remove(sName);
		}
	}

	public static void remove(NamedElement objElement) {
		if (null != objElement && null != objElement.getName()
				&& !objElement.getName().isEmpty()) {
			designSpace.remove(objElement.getName());
		}
	}

	public static void put(NamedElement objElement) 
			throws EugeneException {

		if (null != objElement) {
//			System.out.println("[SymbolTables.put] -> "+objElement.getName());
			SymbolTables.put(objElement.getName(), objElement);
		}
	}

	public static void put(String sName, NamedElement objElement)
			throws EugeneException {

		if (null != sName && null != objElement) {
			try {				
				designSpace.put(sName, objElement);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EugeneException(e.getMessage());
			}
		} else {
			throw new EugeneException("I cannot put the " + objElement
					+ " element into the symbol tables!");
		}

	}

	/** STACK OPERATIONS ***/
	public static StackElement peek() {
		if (null != objStack && !objStack.isEmpty()) {
			return objStack.peek();
		}
		return null;
	}

	public static long stackSize() {
		if(null != stCurrentFunction) {
			return stCurrentFunction.size();
		}
		return 0;		
	}
	
	public static boolean push(StackElement objStackElement) {
		if (objStackElement != null) {
			if (null == objStack) {
				init();
			}
			objStack.push(objStackElement);
			if (objStackElement instanceof Function) {
				stCurrentFunction.push(((Function) objStackElement).getName());
			}
		}
		return true;
	}

	public static StackElement pop() {
		StackElement objElement = null;
		if (null != objStack && !objStack.isEmpty()) {
			objElement = objStack.pop();
			if (objElement instanceof Function) {
				stCurrentFunction.pop();
			}
		}
		return objElement;
	}

	public static String getCurrentFunctionName() {
		if (stCurrentFunction.size() > 0) {
			return stCurrentFunction.peek();
		}
		return null;
	}

}
