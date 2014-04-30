package org.cidarlab.eugene.dom.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;

import com.rits.cloning.Cloner;

import JaCoP.core.Domain;
import JaCoP.core.ValueEnumeration;

public class GeneratedDeviceArray 
	extends DeviceArray {

	private static final long serialVersionUID = -7895923175916527348L;

	private Map<Device, Domain[][]> deviceSolutions;

	public GeneratedDeviceArray(Device device, Domain[][] solutions) {
		super(null);
		
		this.deviceSolutions = new HashMap<Device, Domain[][]>();
		
		/*
		 * first, we cure the solutions... 
		 * by removing all rows in the solutions array that are NULL
		 */
		this.deviceSolutions.put(
				device, 
				cureSolutions(solutions));

	}
	
	public GeneratedDeviceArray(GeneratedDeviceArray deviceArray) {
		super(null);
		this.deviceSolutions = new Cloner().deepClone(deviceArray.getDeviceSolutions());
	}
	
	public void add(GeneratedDeviceArray deviceArray) {
		if(null != deviceArray && null != deviceArray.getDeviceSolutions()) {
			for(Device device : deviceArray.getDeviceSolutions().keySet()) {
				if(!this.deviceSolutions.containsKey(device)) {
					this.deviceSolutions.put(device, deviceArray.getDeviceSolutions().get(device));
				} else {

					/*
					 * we only add the new solutions into the device solutions hash map
					 */
					Domain[][] solutions = this.deviceSolutions.get(device);
					solutions = (Domain[][])ArrayUtils.add(solutions, deviceArray.getDeviceSolutions().get(device));
					
					/*
					 * replace the current solutions 
					 * with the new solutions
					 */
					this.deviceSolutions.remove(device);
					this.deviceSolutions.put(device, solutions);
				}
			}
		}
	}
	
	public Map<Device, Domain[][]> getDeviceSolutions() {
		return this.deviceSolutions;
	}
	
	private Domain[][] cureSolutions(Domain[][] solutions) {
		Domain[][] sols = null;
		// cure the solutions
		if(null != solutions && solutions.length>0) {
			sols = new Domain[1][solutions[0].length];
			sols[0] = solutions[0];
			for(int i=1; i<solutions.length; i++) {
				if( null != solutions[i]) {
					sols = ArrayUtils.add(sols, solutions[i]);
				}
			}
		} 

		return sols;
	}
	
	@Override
	public int size() {
		int size = 0;
		for(Device d : this.deviceSolutions.keySet()) {
			if(this.deviceSolutions.get(d) != null) {
			//System.out.println(d+" -> "+this.deviceSolutions.get(d));
				size += this.deviceSolutions.get(d).length;
			}
		}
		return size;
	}
	
	@Override
	public Device get(int idx) 
			throws EugeneException {

		if(idx<0 || idx>=this.size()) {
			throw new EugeneException(idx+" is an invalid index!");
		}
		
		int nCurrentIdx = 0;
		Device d = null;
		for(Device device : this.deviceSolutions.keySet()) {
			Domain[][] dom = this.deviceSolutions.get(device);
			if(dom != null) {
				if(idx > nCurrentIdx && idx >= nCurrentIdx + dom.length) {
					nCurrentIdx += this.deviceSolutions.get(device).length;
				} else {
					d = device;
					break;
				}
			}
		}
		
		if(null != d) {
			Domain[][] solutions = this.deviceSolutions.get(d);
			if(null != solutions) {
				Domain[] solution = solutions[idx-nCurrentIdx];
				return toDevice(d, solution, idx-nCurrentIdx);
			}
		}
		
		return new Device(null);
	}
	
	private Device toDevice(Device device, Domain[] solution, int idx) 
			throws EugeneException {
		
		/** here, we create the device on-the-fly **/

		Device d = null;
		if(null != device) {
			d = new Cloner().deepClone(device);
			d.setName(device.getName()+"_"+(idx+1));
		} else {
			d = new Device(this.getName()+"_"+(idx+1));
		}
		

		int i = 0;
		for(Domain domain : solution) {
			ValueEnumeration ve = domain.valueEnumeration();
			/* here, we need to create the device */
			while(ve.hasMoreElements()) {
				Component c = SymbolTables.getComponent((long)ve.nextElement());
				
				/*
				 * here, we need to ``insert'' the component 
				 */
				if(c instanceof Part || c instanceof PartType) {
					d.setLeaf(i++, c);
				} else if(c instanceof Device) {
					d.set(i++, c);
				}
				
//				System.out.println(d);
			}				
		}
		
		/*
		 * ORIENTATIONS
		 */
		for(i=0; i<device.getDirections().length; i++) {
			d.getComponents().get(i).setDirection(device.getDirections()[i]);
		}
		
//		System.out.println("[GeneratedDeviceArray.toDevice] -> "+d);
		
		return d;
	}
	
	@Override
	public String toString() {
		String NEWLINE = System.getProperty("line.separator");
		
		StringBuilder sb = new StringBuilder();
		sb.append("Device[] ").append(this.getName()).append(" [");
		for(Device device : deviceSolutions.keySet()) {
				
			char[] directions = {};
			if(null != device) {
				directions = device.getDirections();
			}

			Domain[][] solutions = this.deviceSolutions.get(device);
			
			int N = device.getAllComponents().size();

			if(null != solutions) {
				int k=0;
				for(int i=0; i<solutions.length; i++) {

					Domain[] dArray = solutions[i];
					if(null != dArray) {
						for(int p=dArray.length-N; p<dArray.length; p++) {
							
							ValueEnumeration ve = dArray[p].valueEnumeration();
							while(ve.hasMoreElements()) {
								try {
									Component c = SymbolTables.getComponent(ve.nextElement());
	
									if(k<directions.length && directions[k++] == '-') {
										sb.append("-");
									}
									
									sb.append(c.getName()).append(", ");
								} catch(Exception e) {
									e.printStackTrace();
								}
							}
						}
						sb.append(NEWLINE);
					} else {
						break;
					}
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
