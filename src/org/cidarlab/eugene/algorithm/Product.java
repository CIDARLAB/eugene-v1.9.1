package org.cidarlab.eugene.algorithm;

import java.util.List;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.arrays.GeneratedDeviceArray;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.solver.jacop.JaCoPSolver;


public class Product {

	public static DeviceArray product(String sDeviceName, int N) 
			throws EugeneException {		
		/*
		 * first, we need to retrieve the device from the symbol tables
		 */
		NamedElement element = SymbolTables.get(sDeviceName);
		if(element == null) {
			throw new EugeneException("I don't know "+sDeviceName+"!");
		}
		if(!(element instanceof Device)) {
			throw new EugeneException(sDeviceName+" is not a Device!");
		}
		
		return product((Device)element, N);
	}
	
	public static DeviceArray product(Device device, int N) 
				throws EugeneException {
		/*
		 * second, we retrieve all rules from the symbol tables
		 * that were defined on the device
		 */
		// iterate over the devices components
		List<Rule> rules = null;
		try {
			rules = getRules(device);
		} catch(EugeneException ee) {
			throw new EugeneException(ee.getMessage());
		}
		
		/*
		 * finally, we invoke the constraint solver to 
		 * model and solve the problem
		 */
		return new JaCoPSolver().solveProduct(
					device, 
					rules, 
					N);
	}
	
	private static List<Rule> getRules(Device device) 
			throws EugeneException {
		List<Rule> lstRules = SymbolTables.getDeviceRules(device.getName());
		
		for(Component component : device.getComponents()) {
			if(component instanceof Device) {
				lstRules.addAll(getRules((Device)component));
			}
		}
		
		return lstRules;
	}
	
}
