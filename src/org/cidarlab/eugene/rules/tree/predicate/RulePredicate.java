package org.cidarlab.eugene.rules.tree.predicate;

import org.cidarlab.eugene.exception.EugeneException;

public interface RulePredicate 
	extends Predicate {

	/* 
	 * the long value is the ID of the device
	 * in the database
	 */
	public boolean evaluate(long n) 
			throws EugeneException;
	
	/* 
	 * The sDeviceName string parameter represents 
	 * the device's name 
	 */
	public boolean evaluate(String sDeviceName) 
			throws EugeneException;

	/*
	 * The evaluate() method tries to evaluate 
	 * the rule based on its containing information...
	 */
	public boolean evaluate() 
			throws EugeneException;
	
	/* The correct() method is important in the pre-processing phase 
	 * of permutations and the cartesian product
	 * 
	 * It tries to to correct a given array so that it complies with the
	 * binary predicate...
	 *
	 * Example:
	 * Imagine you're having 
	 * - this possible combination [1, 2, 0, 0, 0] and
	 * - a rule saying 2 NEXTTO 5
	 * 
	 * now, we can place 5 next to to
	 * i.e. the resulting array will look like
	 * [1, 2, 5, 0, 0]
	 * 
	 * the correct() method returns a boolean indicating 
	 * if the array has been corrected
	 * 
	 * the second parameter (long[] elements) contains all elements 
	 * what can be used for correction
	 * usually, long[] elements contains a device's elements
	 */ 
	
	//public boolean correct(long[] l, long[] elements);

	
	/* the allCombinations() method returns all possible combinations 
	 * of the binary predicate that comply with the predicate
	 * 
	 * Example: A BEFORE B  and N=5
	 * 
	 * [A, B, x, x, x]
	 * [A, x, B, x, x]
	 * [A, x, x, B, x]
	 * [A, x, x, x, B]
	 * [x, A, B, x, x]
	 * [x, A, x, B, x]
	 * [x, A, x, x, B]
	 * [x, x, A, B, x]
	 * [x, x, A, x, B]
	 * [x, x, x, A, B]
	 */
	//public long[][] allCombinations(int N);
	

}
