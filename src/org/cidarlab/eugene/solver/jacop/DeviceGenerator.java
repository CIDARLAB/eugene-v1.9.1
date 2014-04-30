package org.cidarlab.eugene.solver.jacop;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.exception.EugeneException;

import JaCoP.core.Domain;
import JaCoP.core.ValueEnumeration;

public class DeviceGenerator 
		implements Runnable {

	private Domain[] solution;
	public DeviceGenerator(Domain[] solution) {
		this.solution = solution;
	}
	
	@Override
	public void run() {
		if(solution != null) {
			try {
				for(int i=0; i<solution.length; i++) {
					System.out.print(i+", ");
					ValueEnumeration ve = solution[i].valueEnumeration();
					StringBuilder sb = new StringBuilder();
					sb.append("Device (");
					while(ve.hasMoreElements()) {
						Part p = (Part)SymbolTables.get(SymbolTables.getNameById(ve.nextElement()));
						sb.append(p.getName());
						if(ve.hasMoreElements()) {
							sb.append(", ");
						}
					}
					sb.append(")");
					//System.out.println(sb.toString());
				}
				System.out.println();
			} catch(EugeneException ee) {}
		}
	}
}
