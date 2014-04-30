package org.cidarlab.eugene.rules.tree.predicate;

import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;

import JaCoP.constraints.Constraint;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public interface Predicate {
	public String getOperator();

	public Constraint toJaCoP(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
		throws EugeneException;


	/* 
	 * the long[] array contains all IDs of 
	 * one Device's components
	 */
	public boolean evaluate(long[] l) 
			throws EugeneException;
	
	/* 
	 * the long value refers to the ID of 
	 * the Device in the database
	 */
	public boolean evaluate(long deviceId) 
			throws EugeneException;

	/* 
	 * the String value refers to the name of 
	 * the Device 
	 */
	public boolean evaluate(String sDeviceName) 
			throws EugeneException;
	
	
	public boolean evaluate(Device device)
			throws EugeneException;

}
