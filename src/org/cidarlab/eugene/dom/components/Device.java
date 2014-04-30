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

package org.cidarlab.eugene.dom.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.biojava.bio.seq.DNATools;
import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.Variable;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.relation.Interaction;
import org.cidarlab.eugene.exception.EugeneException;

import com.rits.cloning.Cloner;


public class Device 
		extends Component {

	private static final long serialVersionUID = 5413691795953256377L;

	private List<Component> lstComponents;
	private List<Interaction> lstInteractions;
	
	private char[] directions;
	
	private String sDeviceType;

	// the isValid flag marks the device if it passes all defined rules
	// one rule is violated => isValid=false
	// no rule is violated => isValid=true
	// this information is used for the SBOL serialization of the device
	private boolean isValid;

	long device_id;
	
	protected Device() {
		super();
	}

	public Device(String name) {
		super(name);
		lstComponents = new ArrayList<Component>();
		this.sDeviceType = (String) null;
		this.directions = null;
	}

	public Device(String sName, List<Component> lstComponents) {
		super(sName);
//		this.lstComponents = lstComponents;
	
		Cloner c = new Cloner();
		this.lstComponents = new ArrayList<Component>();
		for(Component component : lstComponents) {
			this.lstComponents.add(c.deepClone(component));
		}
		
		// directions
		this.directions = new char[lstComponents.size()];
		for(int i=0; i<lstComponents.size(); i++) {
			this.directions[i] = '+';
		}
	}
	
	public Device(String sName, List<Component> lstComponents, List<Property> lstProperties) {
		super(sName, lstProperties);
//		this.lstComponents = lstComponents;		
		
		Cloner c = new Cloner();
		this.lstComponents = new ArrayList<Component>();
		for(Component component : lstComponents) {
			this.lstComponents.add(c.deepClone(component));
		}
		

		// directions
		this.directions = new char[lstComponents.size()];
		for(int i=0; i<lstComponents.size(); i++) {
			this.directions[i] = '+';
		}
		
//		this.component_ids = null;
	}

	public Device(
			String sName, 
			List<Component> lstComponents, 
			List<Property> lstProperties,
			char[] directions) {
		super(sName, lstProperties);

//		this.lstComponents = lstComponents;
		Cloner c = new Cloner();
		this.lstComponents = new ArrayList<Component>();
		for(Component component : lstComponents) {
			this.lstComponents.add(c.deepClone(component));
		}
		
		this.directions = ArrayUtils.clone(directions);
//		this.component_ids = null;
	}
	
	
	public void setInteractions(List<Interaction> lstInteractions) {
		this.lstInteractions = new ArrayList<Interaction>(lstInteractions);		
	}
	
	public List<Interaction> getInteractions() {
		return this.lstInteractions;
	}	

	public void setComponents(List<Component> lstComponents) {
		this.lstComponents = new ArrayList<Component>(lstComponents);
	}
	
//	public long[] getComponentIds() {
//		return this.component_ids;
//	}

	public char[] getDirections() {
		return this.directions;
	}	

	public void setDirections(char[] directions) {
		this.directions = directions;
	}
	public void setDeviceType(String sDeviceType) {
		this.sDeviceType = sDeviceType;
	}

	public String getDeviceType() {
		return this.sDeviceType;
	}

	@Override
	public void add(NamedElement objElement) 
			throws EugeneException {
		
		if (null != objElement && objElement instanceof Component) {
//			System.out.println(this.lstComponents);
//			System.out.println("[Device.add] -> "+objElement);
			this.lstComponents.add((Component)objElement);
			this.directions = ArrayUtils.add(this.directions, '+');
//			System.out.println("[Device.add] -> "+this.toString());
//			System.out.println(this.lstComponents);
		}

	}

	public void remove(int idx) {
		if(idx>=0 && idx<this.lstComponents.size()) {
			this.lstComponents.remove(idx);
			this.directions = ArrayUtils.remove(this.directions, idx);
		}
	}
//	public void add(long id) 
//			throws EugeneException {
//		if(id>=0) {
//			this.component_ids = ArrayUtils.add(this.component_ids, id);
//		}
//	}
	
	public void add(List<Component> lstComponents) {
		if (null != lstComponents && !lstComponents.isEmpty()) {
			Iterator<Component> it = lstComponents.iterator();
			while (it.hasNext()) {
				Component objComponent = it.next();
				if (null == this.lstComponents) {
					this.lstComponents = new ArrayList<Component>(
							lstComponents.size());
				}
				this.lstComponents.add(objComponent);
			}
		}
	}

	public void addComponentsByName(List<String> lstComponentNames) {
		if (null != lstComponentNames && !lstComponentNames.isEmpty()) {
			Iterator<String> it = lstComponentNames.iterator();
			while (it.hasNext()) {
				this.lstComponents.add((Component) SymbolTables.get(it.next()));
			}
		}
	}

	public void clear() {
		if (null != this.lstComponents) {
			this.lstComponents.clear();
			this.lstComponents = null;
		}
	}

	// the get method returns the i-th component of the device
	@Override
	public Component get(int idx) {
		Component objComponent = null;
		if (idx >= 0 && idx < this.lstComponents.size()) {
			objComponent = this.lstComponents.get(idx);
		}
		return objComponent;
	}

	public ArrayList<Component> getAllComponents() {
		ArrayList<Component> lst = new ArrayList<Component>();
		if (null != this.lstComponents && !this.lstComponents.isEmpty()) {
			Iterator<Component> it = this.lstComponents.iterator();
			while (it.hasNext()) {
				Component objComponent = it.next();
				if (null != objComponent && objComponent instanceof Device) {
					Device containingDevice = (Device) objComponent;
					lst.addAll(containingDevice.getAllComponents());
				} else {
					lst.add(objComponent);
				}
			}
		}
		return lst;
	}

	public List<Component> getComponents() 
			throws EugeneException {
		return this.lstComponents;
	}

	@Override
	public String toString() {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("Device ").append(this.getName()).append("(");
	
			/** PROPERTIES **/
			if(null != this.getProperties() && !this.getProperties().isEmpty()) {
				for (Property prop : this.getProperties()) {
					sb.append(prop.getName()).append(", ");
				}
				if(!this.getProperties().isEmpty() && 
					this.getComponents().isEmpty()) {
					sb.deleteCharAt(sb.toString().length()-2);
					sb.deleteCharAt(sb.toString().length()-1);
				}
				
				sb.append(System.getProperty("line.separator"));
			}
			
			/** COMPONENTS **/
			int i=0;
			for (Component c : this.getComponents()) {
				if(null != directions) {
					if(directions[i++] == '-') {
						sb.append("-");
					}
				}
				
				if(c instanceof Device) {
					sb.append(c.toString());
				} else {
					sb.append(c.getName());
				}
				sb.append(", ");
			}
			if(!this.getComponents().isEmpty()) {
				sb.deleteCharAt(sb.toString().length()-2);
				sb.deleteCharAt(sb.toString().length()-1);
			}
			
			//sb.append(System.getProperty("line.separator"));
			sb.append(");");
			return sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setIsValid(boolean b) {
		this.isValid = b;
	}

	public boolean isValid() {
		return isValid;
	}

	public String toSequence() 
			throws EugeneException {

		StringBuilder sb = new StringBuilder();
		if (null != this.lstComponents && !this.lstComponents.isEmpty()) {
			for(Component component : this.lstComponents) {
				
				if(component.getDirection() == '+') {
					sb.append(component.toSequence());
				} else {
					// due to SBOL validation issues we can't 
					// reverse complement the sequence
//					sb.append(reverseComplement(component.toSequence()));
					sb.append(component.toSequence());
				}
			}
		}
		return sb.toString();
	}
	
	private String reverseComplement(String dna) 
			throws EugeneException {
		try {
			return DNATools.reverseComplement(
						DNATools.createDNA(dna)).seqString();
		} catch(Exception e) {
			throw new EugeneException(
					"The sequence of the device "+this.getName()+" contains invalid characters!");
		}
	}

	public boolean compareTo(Device objDevice) {
		if (this == objDevice) {
			return true;
		}
		return false;
	}

	@Override
	public void assign(NamedElement objElement)
			throws EugeneException {
		if (objElement instanceof Device) {
			this.lstComponents = new ArrayList<Component>(
					((Device) objElement).getComponents());
			this.directions = ((Device)objElement).getDirections();
		} else {
			throw new EugeneException("Cannot assign a "
					+ objElement.getClass() + " value to a Device!");
		}
	}

	@Override
	public int size() {
		return this.lstComponents.size();
		//return this.component_ids.length;
	}

	@Override
	public boolean contains(NamedElement objElement) {
		boolean b = false;
		if (objElement != null && objElement instanceof Component) {
			Component objComponent = (Component) objElement;
			for (int i = 0; i < lstComponents.size() && !b; i++) {
				Component objDeviceComponent = lstComponents.get(i);
				if (objDeviceComponent.equals(objComponent)) {
					return true;
				} else if (objDeviceComponent instanceof Device) {
					b = ((Device) objDeviceComponent).contains(objComponent);
				}
			}
		}
		return b;
	}

	@Override
	public NamedElement get(String sName) {

		if (null == sName || null == this.lstComponents
				|| this.lstComponents.isEmpty()) {
			return null;
		}

		if ("name".equals(sName.toLowerCase())) {
			return EugeneBuilder.buildVariable(this.getName());
			// return new Property("name", EugeneConstants.TXT);
		}

		for (Component objComponent : this.lstComponents) {
			if (sName.equals(objComponent.getName())) {
				return objComponent;
			}
		}

		ArrayList<Component> lstComponents = this.getAllComponents();
		for (int i = 0; i < lstComponents.size(); i++) {
			Component objComponent = lstComponents.get(i);

			if (sName.equals(objComponent.getName())) {
				return objComponent;
			} else if (objComponent instanceof Part) {
				Part objPart = (Part) objComponent;
				if (sName.equals(objPart.getPartType().getName())) {
					return objPart;
				}
			}
		}

		return (NamedElement) null;
	}

	public Part getParts(PartType objPartType) {
		// TODO: iterate over the device's components and check if the part
		// is an instance of the given part type
		if (null != objPartType) {
			for (Component objComponent : this.getAllComponents()) {
				if (objComponent instanceof Part) {
					Part objPart = (Part) objComponent;
					if (objPart.getPartType().equals(objPartType)) {
						return objPart;
					}
				}
			}
		}
		return (Part) null;
	}

	@Override
	public boolean equals(NamedElement objComponent) {

		if (!(objComponent instanceof Device)) {
			return false;
		}

		Device objDevice = (Device) objComponent;

		ArrayList<Component> lstThisComponents = this.getAllComponents();
		ArrayList<Component> lstComponents = objDevice.getAllComponents();

		if (lstComponents.size() != lstThisComponents.size()) {
			return false;
		}
		for (int i = 0; i < lstThisComponents.size(); i++) {
			if (!lstThisComponents.get(i).equals(lstComponents.get(i))) {
				return false;
			}
		}
		return true;
	}

	public int indexOf(NamedElement objElement) {
		if (objElement != null && objElement instanceof Component) {
			Iterator<Component> it = this.lstComponents.iterator();
			int i = 0;
			while (it.hasNext()) {
				if (((Component) objElement).equals(it.next())) {
					return i;
				}
				i++;
			}
		}

		return -1;
	}

	public int lastIndexOf(NamedElement objElement) {
		if (objElement != null && objElement instanceof Component) {
			Component objComponent = (Component) objElement;
			if (objComponent instanceof Device) {
				for (int i = this.lstComponents.size() - 1; i >= 0; i--) {
					if (this.lstComponents.get(i).equals(objComponent)) {
						return i;
					}
				}
			} else {
				ArrayList<Component> lst = this.getAllComponents();
				for (int i = lst.size() - 1; i >= 0; i--) {
					// System.out.println(lst.get(i)+" vs. "+objComponent+" -> "+lst.get(i).equals(objComponent));
					if (lst.get(i).equals(objComponent)) {
						// System.out.println("returning "+i);
						return i;
					}
				}
			}
		}
		return -1;
	}

	/*
	 * the count method counts the occurrence of the given component in the
	 * device
	 */
	public int count(Component objComponent) {
		int nCount = 0;
		if (null != this.lstComponents && !lstComponents.isEmpty()) {
			Iterator<Component> it = this.lstComponents.iterator();
			while (it.hasNext()) {
				Component objDeviceComponent = it.next();
				if (objDeviceComponent instanceof Device) {
					nCount += ((Device) objDeviceComponent).count(objComponent);
				} else if (objDeviceComponent.equals(objComponent)) {
					nCount++;
				}
			}
		}
		return nCount;
	}

	@Override
	public void set(int idx, NamedElement objElement)
			throws EugeneException {
		if (objElement instanceof Component && idx >= 0 && idx < this.lstComponents.size()) {
			Component objComponent = this.lstComponents.get(idx);
			objComponent.assign(objElement);
		} else {
			throw new EugeneException("Cannot assign the "
					+ objElement + " element to the " + (idx + 1)
					+ " component of the " + this.getName() + " Device");
		}
	}

	/*
	 * GO LEAFS GO
	 */

	public void setLeaf(int idx, Component newComponent) 
			throws EugeneException {
		
		try { 			
			replaceComponent(idx, newComponent, 0);
		} catch(Exception e) {
			e.printStackTrace();
			throw new EugeneException(e.toString());
		}
	}
	
	public void replace(int idx, Component component) {
		this.lstComponents.set(idx, component);
	}
	
	public void replaceComponent(int idx, Component newComponent, int nCurrentIdx) 
			throws EugeneException {
//		System.out.println(idx+", "+nCurrentIdx+", ");
		int i=0;
		for(Component component : this.getComponents()) {
			if(component instanceof Device) {
//				System.out.println("currentIdx: "+nCurrentIdx);
				if(idx <= nCurrentIdx + ((Device)component).getAllComponents().size() - 1) {
					((Device)component).replaceComponent(idx, newComponent, nCurrentIdx);
				}
				nCurrentIdx += ((Device)component).getAllComponents().size();
			} else {
				if(nCurrentIdx == idx) {
//					System.out.println("i -> "+i);
					this.lstComponents.set(i, newComponent);
					return;
				}
//				System.out.println(nCurrentIdx+": "+component.getName()+", ");
				nCurrentIdx++;				
			}
			i++;			
		}
//		System.out.println();
	}

	@Override
	public void set(String sElementName, NamedElement objAssignElement)
			throws EugeneException {
		if (null != sElementName && "name".equals(sElementName.toLowerCase())) {
			if (null == objAssignElement
					|| !(objAssignElement instanceof Variable)
					|| !EugeneConstants.TXT
							.equals(((Variable) objAssignElement).getType())) {
				throw new EugeneException(objAssignElement
						+ " is an invalid name for the " + this.getName()
						+ " device!");
			} else {
				this.sName = ((Variable) objAssignElement).getTxt();
			}
		} else {
			throw new EugeneException(sElementName
					+ " is an invalid property name for a device!");
		}
	}

	public boolean isAbstract() {
		Iterator<Component> it = this.getAllComponents().iterator();
		while (it.hasNext()) {
			if (it.next() instanceof Part) {
				return false;
			}
		}
		return true;
	}

	public boolean isConcrete() {
		Iterator<Component> it = this.getAllComponents().iterator();
		while (it.hasNext()) {
			if (!(it.next() instanceof Part)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isInstanceOf(String sDeviceType) {
		if (null != this.sDeviceType && null != sDeviceType) {
			return this.sDeviceType.equals(sDeviceType);
		}
		return false;
	}
	
	private static List<Component> lst; 
	private static int nCurrentDepth = 0;
    private static char[] dirs;
    
	public List<Component> getComponentsAtDepth(int nDepth) 
			throws EugeneException {
		// first, reset the list
		lst = new ArrayList<Component>();
        nCurrentDepth = 0;

        if(nDepth > 1) {
        	this.traverse(nDepth);
        } else {
        	return this.getComponents();
        }

		// finally, return the list
		return lst;
	}
	
	public char[] getDirectionsAtDepth(int nDepth) 
			throws EugeneException {

		lst = new ArrayList<Component>();
        nCurrentDepth = 0;
		dirs = new char[1];
		
		if(nDepth > 1) {
			this.traverse(nDepth);
		} else {
			return this.directions;
		}
		
		dirs = ArrayUtils.remove(dirs, 0);
		//System.out.println("[Device.getDirectionsAtDept] "+nDepth+" -> "+Arrays.toString(dirs));
		
		return dirs;
	}
	
	private void traverse(int nDepth) 
			throws EugeneException {
		if(nCurrentDepth > nDepth) {
			return;
		}
		
		if(nDepth == (nCurrentDepth+1)) {
			lst.addAll(this.getComponents());
			dirs = ArrayUtils.addAll(dirs, this.getDirections());
			return;
		}

		if(this.lstComponents != null && !this.lstComponents.isEmpty()) {
			for(Component comp : this.lstComponents) {
				if(comp instanceof Device) {
					++ nCurrentDepth;
					((Device)comp).traverse(nDepth);
					-- nCurrentDepth;
				} else {
					lst.add(comp);
				}
			}
		}
	}
	
	private static int nMaxDepth = 0;	
	public int getMaxDepth() {
		nMaxDepth = 0;
		return traverseMaxDepth();
	}
	
	private int traverseMaxDepth() {
		for(Component comp : this.lstComponents) {
			if(comp instanceof Device) {
				if(++ nCurrentDepth > nMaxDepth) {
					nMaxDepth = nCurrentDepth;
				}
				
				((Device)comp).traverseMaxDepth();	
				
				-- nCurrentDepth;
			}
		}
		return nMaxDepth+1;
	}
	
	public void reverseComponents() {
		if(null != this.lstComponents && !this.lstComponents.isEmpty()) {

			for(int i=0; i<lstComponents.size()/2; i++) {
				if(lstComponents.get(i) instanceof Device) {
					((Device)lstComponents.get(i)).reverseComponents();
				}
				
				if(lstComponents.get(lstComponents.size()-1-i) instanceof Device) {
					((Device)lstComponents.get(i)).reverseComponents();
				}

				/* set the directions */
				this.directions[i] = '-';
				this.directions[lstComponents.size()-1-i] = '-';
				
				Component tmpComponent = lstComponents.get(i);
				lstComponents.set(i, this.lstComponents.get(this.lstComponents.size()-i-1));
				this.lstComponents.set(this.lstComponents.size()-i-1, tmpComponent);
			}
			
			/*
			 * finally, we need to change the direction of the middle component
			 * (in case of an odd number of components)
			 */
			if(lstComponents.size() % 2 != 0) {
				System.out.println(((Device)this.lstComponents.get(this.lstComponents.size()/2)));
				if(this.lstComponents.get(this.lstComponents.size()/2) instanceof Device) {
					((Device)this.lstComponents.get(this.lstComponents.size()/2)).reverseComponents();
				}
				System.out.println(((Device)this.lstComponents.get(this.lstComponents.size()/2)));
				this.directions[this.lstComponents.size()/2] = '-';
			}

		}
	}

}
