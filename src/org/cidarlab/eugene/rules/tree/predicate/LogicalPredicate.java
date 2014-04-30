package org.cidarlab.eugene.rules.tree.predicate;

import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;

import JaCoP.constraints.Constraint;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public interface LogicalPredicate 
	extends Predicate {
	
	/*
	 * NEGATION OF RULES
	 * NOT
	 */
	public Constraint toJaCoPNot(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
					throws EugeneException;

	/*
	 * LOGICAL COMPOSITION OF RULES
	 * AND, OR, XOR
	 */
	public Constraint toJaCoPAnd(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
					throws EugeneException;

	public Constraint toJaCoPOr(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
					throws EugeneException;

	public Constraint toJaCoPXor(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
					throws EugeneException;

}
