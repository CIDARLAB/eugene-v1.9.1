package org.cidarlab.eugene.solver;

import java.util.List;

import org.cidarlab.eugene.dom.arrays.GeneratedDeviceArray;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;

public interface Solver {
	
	/*
	 * Eugene's product() function
	 * 
	 * every solver needs to implement the solveProduct() method
	 * which returns an array of valid rule-compliant devices
	 * 
	 * @param device ... an ``abstract'' device the represents a ``template'' 
	 *                   for all solutions
	 * @param rules  ... a list of rules that constraint the device generation
	 * @param N      ... the number of requested rule-compliant devices
	 *                   if N == -1 ... return all rule-compliant devices
	 *                   otherwise  ... return N rule-compliant devices
	 *                   
	 *                   if N is greater than the max. possible rule-compliant devices,
	 *                   then only the rule-compliant devices get returned
	 *                   
	 * @return       ... it returns a GeneratedDeviceArray object that contains all 
	 *                   the rule-compliant devices
	 */
	public GeneratedDeviceArray solveProduct(
			Device device,
			List<Rule> rules,
			int N)
				throws EugeneException;

	/*
	 * Eugene's permute() function
	 * 
	 * every solver needs to implement the solvePermute() method
	 * which returns an array of valid rule-compliant devices
	 * 
	 * @param device ... an ``abstract'' device the represents a ``template'' 
	 *                   for all solutions
	 * @param rules  ... a list of rules that constraint the device generation
	 * @param N      ... the number of requested rule-compliant devices
	 *                   if N == -1 ... return all rule-compliant devices
	 *                   otherwise  ... return N rule-compliant devices
	 *                   
	 *                   if N is greater than the max. possible rule-compliant devices,
	 *                   then only the rule-compliant devices get returned
	 *                   
	 * @return       ... it returns a GeneratedDeviceArray object that contains all 
	 *                   the rule-compliant devices
	 */
	public GeneratedDeviceArray solvePermute(
			Device device,
			List<Rule> rules,
			int N);
}
