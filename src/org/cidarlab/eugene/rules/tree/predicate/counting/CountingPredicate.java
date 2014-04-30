package org.cidarlab.eugene.rules.tree.predicate.counting;

import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.rules.tree.predicate.BinaryPredicate;

import JaCoP.constraints.Constraint;
import JaCoP.core.BooleanVar;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public abstract class CountingPredicate 
	extends BinaryPredicate {

	public CountingPredicate(long A, long B) 
			throws EugeneException {
		super(A, B);
	}

	public abstract Constraint toJaCoPNot(
			Store store, IntVar[] variables, 
			Device device, List<Component> components) 
				throws EugeneException;

}
