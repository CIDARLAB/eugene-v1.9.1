package org.cidarlab.eugene.solver.jacop;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.exception.EugeneException;

import JaCoP.constraints.IfThen;
import JaCoP.constraints.Reified;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XeqY;
import JaCoP.core.BooleanVar;
import JaCoP.core.IntVar;
import JaCoP.core.Store;

public class Eugene2JaCoP {

	public static IntVar modelPart(Part part, Store store/**, IntVar[] variables, int position**/) 
			throws EugeneException {

		int partId = (int)SymbolTables.getId(part.getName());

		IntVar partVar = (IntVar)store.findVariable(part.getName()); 
		if(null == partVar) {			
			partVar = new IntVar(store, part.getName(), partId, partId);
		}
		
		/* 
		 * the partVar's domain contains all part ids
		 */
		IntVar partIdsVar = (IntVar)store.findVariable("part-ids");
		if(null == partIdsVar) {
			partIdsVar = new IntVar(store, "part-ids", partId, partId);
		} else {
			partIdsVar.addDom(partId, partId);
		}
		
		/** PROPERTY VALUES ***/
		/*
		 * here, we need to iterate over the part's property values ...
		 */
		if(null != part.getPropertyValues() && !part.getPropertyValues().isEmpty()) {
			for(PropertyValue pv : part.getPropertyValues()) {
				/*
				 * for every property value we create a new IntVar
				 */
				IntVar propertyVar = (IntVar)store.findVariable(pv.getName());
				if(null == propertyVar) {
					propertyVar = new IntVar(store, pv.getName());
				}
				
				int value = toASCII(pv.getValue());
				propertyVar.addDom(value, value);
				
				/*
				 * now, we need to build a relationship between the part variable
				 * and the property variable
				 */
				BooleanVar bVar = new BooleanVar(store);
				store.impose(new Reified(new XeqC(propertyVar, value), bVar));
				store.impose(new Reified(new XeqC(partVar, partId), bVar));
			}
		}
		
//		/*
//		 * positioning of the parts
//		 */
//		IntVar partPositionVar = (IntVar)store.findVariable("positions");
//		if(null == partPositionVar) {
//			partPositionVar = new IntVar(store, "positions", position, position);
//		} else {
//			partPositionVar.addDom(position, position);
//		}
		
		/*
		 * now we use an Element constraint to specify the parts valid position
		 */

//		System.out.println("[modelPart] -> "+part.getName());

		return partVar;
	}
	
	public static int toASCII(String s) {
		int ascii = 0;
		char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
        	ascii += (int)chars[i];
        }
        return ascii;
	}
}
