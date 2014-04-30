/*
Copyright (c) 2012 Boston University.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL BOSTON UNIVERSITY BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
BOSTON UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

BOSTON UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND BOSTON UNIVERSITY HAS
NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.
 */

package org.cidarlab.eugene.dom.arrays;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;

import JaCoP.core.Domain;
import JaCoP.core.ValueEnumeration;

import com.rits.cloning.Cloner;


public class DeviceArray 
	extends ComponentArray {

	private static final long serialVersionUID = 2775241154187528389L;

	// v1.8
	private ArrayList<String> lstDeviceNames;
	
	// v1.9
//	private long[] devices;
//	private long[][] device_components;
	
	public DeviceArray() {
		super(null);
		lstDeviceNames = new ArrayList<String>();
	}
	
	public DeviceArray(String sName) {
		super(sName);
		lstDeviceNames = new ArrayList<String>();
	}
	
	// for product()
	private Device device;
	private Domain[][] solutions;
	public DeviceArray(Device device, Domain[][] solutions) {
		super(null);

		this.device = device;
		
		// cure the solutions
		if(null != solutions && solutions.length>0) {
			this.solutions = new Domain[1][solutions[0].length];
			this.solutions[0] = solutions[0];
			for(int i=1; i<solutions.length; i++) {
				if( null != solutions[i]) {
					this.solutions = ArrayUtils.add(this.solutions, solutions[i]);
				}
			}
		} else { 
			this.solutions = null;
		}
	}
	
	public Domain[][] getSolutions() {
		return this.solutions;
	}
	
	public Device getDevice() {
		return this.device;
	}
	
	// v1.9 -- Constructor
//	public DeviceArray(String sName, long[] devices) {
//		super(sName);
//		this.devices = devices;
//	}
//	public DeviceArray(String sName, long[][] device_components) {
//		super(sName);
//		this.device_components = device_components;
//	}
//
//	public long[] getDevices() {
//		return this.devices;
//	}
	
	/** ITERATOR FUNCTIONS **/	
	public boolean hasNext() {
		return false;
	}
	
	public Device next() {
		return null;
	}
	

	public DeviceArray(String sName, int nSize) {
		super(sName);
		lstDeviceNames = new ArrayList<String>(nSize);
	}

	public DeviceArray(String sName, List<String> lstDeviceNames) {
		super(sName);
		this.lstDeviceNames = new ArrayList<String>(lstDeviceNames);
	}

	public void setDeviceNames(ArrayList<String> lstDeviceNames) {
		this.lstDeviceNames = lstDeviceNames;
	}

	public ArrayList<String> getDeviceNames() {
		return this.lstDeviceNames;
	}
	
//	public void add(long deviceId) {
//		this.devices = ArrayUtils.add(this.devices, deviceId);
//	}
//	
//	public void addAll(long[] devices) {
//		this.devices = ArrayUtils.addAll(this.devices, devices);
//	}
//	

	public void remove(int idx) {
		throw new UnsupportedOperationException("DeviceArray.remove(int) is not supported yet!");
//		if(idx>=0 && idx<this.devices.length) {
//			this.devices = ArrayUtils.remove(this.devices, idx);
//		}
	}
	
	public void add(String sDeviceName) {
//		System.out.println("[DeviceArray.add] -> "+sDeviceName);
		
		this.lstDeviceNames.add(sDeviceName);
	}

	public void addAll(DeviceArray objArray) {
		
		if(null != objArray) {
			if (null != objArray.lstDeviceNames && !objArray.lstDeviceNames.isEmpty()) {
	
				if (null == this.lstDeviceNames) {
					this.lstDeviceNames = new ArrayList<String>();
				}
	
				// v1.9
//				this.devices = ArrayUtils.clone(objArray.getDevices());
				
				// v1.8
				this.lstDeviceNames.addAll(objArray.getDeviceNames());
				
			} else if(null != objArray.getSolutions()) {

				Domain[][] arraySolutions = objArray.getSolutions();
				if(null == this.solutions) {
					this.solutions = arraySolutions.clone();
					int idx = 0;
					for(Domain[] d : this.solutions) {
						if(null == d) {
							this.solutions = ArrayUtils.remove(this.solutions, idx);
						} else {
							idx++;
						}
					}
				} else {
					for(int i=0; i<arraySolutions.length; i++) {
						if(null != arraySolutions[i]) {
							this.solutions = ArrayUtils.add(this.solutions, arraySolutions[i]);
						}
					}
				}
			}
		}

	}
	
//	public NamedElement createDevice(int idx) 
//			throws EugeneException {
//		if(null != this.solutions) {
//			
//			Cloner cloner = new Cloner();
//			
//			/** here, we create the device on-the-fly **/
//
//			Device d = null;
//			if(null != this.device) {
//				d = new Device(this.device.getName()+"_"+(idx+1));
//			} else {
//				d = new Device(this.getName()+"_"+(idx+1));
//			}
//
//			/*
//			 * here, we need to rebuild the hierarchy 
//			 * as in the device array's device
//			 */
////			for(Component component : this.device.getComponents()) {
////				if(component instanceof Device) {
////					d.add(component);
////				} else {
////					d.set(idx, component);
////				}
////			}
//			
//			Domain[] domains = this.solutions[idx];
//			int i = 0;
//			for(Domain domain : domains) {
//				ValueEnumeration ve = domain.valueEnumeration();
//				/* here, we need to create the device */
//				while(ve.hasMoreElements()) {
//					Component c = SymbolTables.getComponent((long)ve.nextElement());
//					
//					/*
//					 * here, we need to ``insert'' the component 
//					 */
////					System.out.println(i+" -> "+c);
//					d.add(c);
//				}				
//			}
//			
//			//System.out.println("[DeviceArray.get] -> "+d);
//			
//			return d;
//		}
//		return null;
//	}
			
	public NamedElement get(int idx) 
			throws EugeneException {

		// v1.9
		if(null != this.solutions) {
			
			Cloner cloner = new Cloner();
			
			/** here, we create the device on-the-fly **/

			Device d = null;
			if(null != this.device) {
				d = cloner.deepClone(this.device);
				d.setName(this.device.getName()+"_"+(idx+1));
			} else {
				d = new Device(this.getName()+"_"+(idx+1));
			}

			/*
			 * here, we need to rebuild the hierarchy 
			 * as in the device array's device
			 */
//			for(Component component : this.device.getComponents()) {
//				if(component instanceof Device) {
//					d.add(component);
//				} else {
//					d.set(idx, component);
//				}
//			}
			
			Domain[] domains = this.solutions[idx];
			int i = 0;
			for(Domain domain : domains) {
				ValueEnumeration ve = domain.valueEnumeration();
				/* here, we need to create the device */
				while(ve.hasMoreElements()) {
					Component c = SymbolTables.getComponent((long)ve.nextElement());
					
					/*
					 * here, we need to ``insert'' the component 
					 */
					//System.out.println(i+" -> "+c);
					if(c instanceof Part || c instanceof PartType) {
						d.setLeaf(i++, c);
					} else if(c instanceof Device) {
						d.set(i++, c);
					}
				}				
			}
			
			//System.out.println("[DeviceArray.get] -> "+d);
			
			return d;
			
			
//			ValueEnumeration ve = this.solutions[idx].valueEnumeration();
//			
//			while(ve.hasMoreElements()) {
//				Node node = this.graphDb.getNodeById((long)ve.nextElement());
//				Relationship rel = solution_node.createRelationshipTo(node, EugeneRelation.CONSISTS_OF);
//				rel.setProperty("position", j);
//
//				System.out.print(node.getProperty("name"));
//			}
			
//		} else if(null != this.devices) {
//			if(idx >= 0 && idx < this.devices.length) {		
//				return SymbolTables.getDevice(this.devices[idx]);
//			}

		// v1.8
		} else if(null != this.lstDeviceNames) {		
			if (idx >= 0 && idx < lstDeviceNames.size()) {
				return SymbolTables.get(this.lstDeviceNames.get(idx));
			}
		}
		
		return null;
	}

	public NamedElement get(String sDeviceName) 
			throws EugeneException {
		if (sDeviceName != null) {
			if (this.lstDeviceNames.contains(sName)) {
				return (Device) SymbolTables.get(sDeviceName);
			}
		}
		return null;
	}

	@Override
	public boolean equals(NamedElement objElement) {
		if (objElement != null && objElement instanceof DeviceArray) {
			DeviceArray objDeviceArray = (DeviceArray) objElement;
			if (objDeviceArray.getDeviceNames().equals(this.lstDeviceNames)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {

		// v1.9
		if(null != this.solutions) {
			
			return solutions.length;
			
//		} else if(null != this.devices && 0 != this.devices.length) {
//			return this.devices.length;
		}
			
		// v1.8
		else if (this.lstDeviceNames != null && !this.lstDeviceNames.isEmpty()) {
			return this.lstDeviceNames.size();
		}
		return 0;
	}

	@Override
	public String toString() {
		String NEWLINE = System.getProperty("line.separator");
		
		StringBuilder sb = new StringBuilder();
		sb.append("Device[] ").append(this.getName()).append(" [");
		if(null != this.solutions) {
				
			char[] directions = {};
			if(null != this.device) {
				directions = this.device.getDirections();
			}

			int k=0;
			for(int i=0; i<this.size(); i++) {
//				System.out.println(i+": "+this.solutions[i]);
				Domain[] dArray = this.solutions[i];
				if(null != dArray) {
					k=0;
					for(Domain d : dArray) {
						ValueEnumeration ve = d.valueEnumeration();
						while(ve.hasMoreElements()) {
							try {
								Component c = SymbolTables.getComponent((long)ve.nextElement());

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
		} else if (null != this.lstDeviceNames && !lstDeviceNames.isEmpty()) {
			for (String s : lstDeviceNames) {
				sb.append(s).append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean contains(Device objDevice) {
		if (null != objDevice) {
			return lstDeviceNames.contains(objDevice.getName());
		}
		return false;
	}

	public boolean contains(String sDeviceName) {
		if (null != sDeviceName && !sDeviceName.isEmpty()) {
			return this.lstDeviceNames.contains(sDeviceName);
		}
		return false;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {

		if (objElement == null) {
			throw new EugeneException(
					"I cannot assign an undefined value to an array of devices!");
		} else if (!(objElement instanceof DeviceArray)) {
			throw new EugeneException(""
					+ "Incompatible types! I cannot assign the " + objElement
					+ " element to an array of devices!");
		}
		
//		this.devices = ArrayUtils.clone(((DeviceArray)objElement).getDevices());
		this.solutions = ArrayUtils.clone(((DeviceArray)objElement).getSolutions());
        if( null != ((DeviceArray) objElement).getDeviceNames()) {
            this.lstDeviceNames = new ArrayList<String>(((DeviceArray) objElement).getDeviceNames());
        }
        this.device = ((DeviceArray)objElement).getDevice();
	}
	

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		if (objElement instanceof Device) {
			if (idx >= 0 && idx < this.lstDeviceNames.size()) {
				this.lstDeviceNames.set(idx, ((Device) objElement).getName());
			} else {
				throw new EugeneException("Array index ("
						+ idx + ") is out of bounds!");
			}
		} else {
			throw new EugeneException(objElement.getName()
					+ " is not a Device!");
		}

	}

	@Override
	public void set(String sDeviceName, NamedElement objElement)
			throws EugeneException {
		if (null != this.lstDeviceNames && !this.contains(sDeviceName)) {
			if (objElement instanceof Device) {
				this.lstDeviceNames.set(
						this.lstDeviceNames.indexOf(sDeviceName),
						objElement.getName());
			} else {
				throw new EugeneException("I cannot place a "
						+ objElement.getClass() + " element into the "
						+ this.getName() + " Device list!");
			}
		} else {
			throw new EugeneException("The " + this.getName()
					+ " Device list does not contain a Device named "
					+ sDeviceName + "!");
		}
	}

	public void add(NamedElement objElement)
			throws EugeneException {

            if(null != objElement) {
                if(objElement instanceof DeviceArray) {
                    Domain[][] solutions = ((DeviceArray)objElement).getSolutions();                    

                    if(solutions != null) {
                        if(null == this.solutions) {
                            this.solutions = new Domain[1][solutions[0].length];
                            for(int i=0; i<solutions.length; i++) {
                                if(null !=  solutions[i]) {
                                    this.solutions = ArrayUtils.add(this.solutions, solutions[i]);
                                }
                            }
                            this.solutions = ArrayUtils.remove(this.solutions, 0);
                        } else {
                            for(int i=0; i<solutions.length; i++) {
                                if(null !=  solutions[i]) {
                                    this.solutions = ArrayUtils.add(this.solutions, solutions[i]);
                                }
                            }
                        }
                    }
                    //this.solutions = ArrayUtils.add(
                    //        this.solutions, ((DeviceArray)objElement).getSolutions());
                    this.lstDeviceNames.addAll(
                            ((DeviceArray)objElement).getDeviceNames());
                } else if (objElement instanceof Device) {
                    lstDeviceNames.add(objElement.getName());
		} else {
                    throw new EugeneException("I cannot add the "
				+ objElement.getName() + " to the " + this.getName()
				+ " device array!");
                }
            }

	}

	public boolean isEmpty() {
		return this.lstDeviceNames.isEmpty();
	}

	@Override
	public ArrayList<Device> getComponents() {
		if (null != this.lstDeviceNames && !this.lstDeviceNames.isEmpty()) {
			ArrayList<Device> lst = new ArrayList<Device>(
					this.lstDeviceNames.size());
			for (String s : this.lstDeviceNames) {
				lst.add((Device) SymbolTables.get(s));
			}
			return lst;
		}
		return new ArrayList<Device>();
	}

	/***
	@Override
	public void remove(int idx) 
			throws EugeneException {
		if(null != this.instances) {
			if(idx>=0 && idx<this.instances.length) {
				this.instances = ArrayUtils.remove(this.instances, idx);
			} else {
				throw new EugeneException(idx+" is an invalid index!");
			}
		}
		if(null != this.lstDeviceNames) {
			if(idx>=0 && idx<this.lstDeviceNames.size()) {
				this.lstDeviceNames.remove(idx);
			} else {
				throw new EugeneException(idx+" is an invalid index!");
			}
		}
	}
	***/

}
