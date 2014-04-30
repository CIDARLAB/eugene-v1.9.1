package org.cidarlab.eugene.solver.jacop;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.exception.EugeneException;

import JaCoP.core.*;
import JaCoP.search.*;

public class EugeneSolutionListener<T extends Var> 
	extends SimpleSolutionListener<T> { 
		 
	private long counter;
	private long i;
	private Domain[][] domain;

	public EugeneSolutionListener() {
		counter = 0;
		i=0;
		
	}
	
	public boolean executeAfterSolution(
			Search<T> search, SelectChoicePoint<T> select) {
		boolean b = super.executeAfterSolution(search, select);
//		System.out.println("solution found...");
//		if(null != search) {
//			counter++;
//			Domain[] solution = search.getSolution();
//			try {
//	//			new Thread(new DeviceGenerator(solution)).start();
//				StringBuilder sb = new StringBuilder();
//				sb.append("Device (");
//				for(int i=solution.length-4; i<solution.length; i++) {
//					ValueEnumeration ve = solution[i].valueEnumeration();
//					while(ve.hasMoreElements()) {
//						Part p = (Part)SymbolTables.get(SymbolTables.getNameById(ve.nextElement()));
//						sb.append(p.getName());
////						sb.append(ve.nextElement());
////						if(ve.hasMoreElements()) {
//							sb.append(", ");
////						}
//					}
//				}
//				sb.append(")");
//				System.out.println(sb.toString());
////				System.out.println();
//			} catch(EugeneException ee) {
//				ee.printStackTrace();
//			}
//			
//			if(null != solution) {
//				if(null == domain) {
//					this.domain = new Domain[1][solution.length];
//					this.domain[0] = solution; 
//				} else { 
//					this.domain = ArrayUtils.add(this.domain, solution);
//				}
//			}
//		}
//		return true;
		return b;
	} 
	
	public Domain[][] getSolutions() {
		return this.solutions;
	}
	
	public long getCounter() {
		return this.counter;
	}
}
