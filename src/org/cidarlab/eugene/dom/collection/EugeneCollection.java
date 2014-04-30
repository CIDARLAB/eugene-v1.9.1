package org.cidarlab.eugene.dom.collection;

import java.util.HashSet;
import java.util.Set;

import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.StackElement;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;


public class EugeneCollection 
	extends NamedElement 
	implements StackElement {

	private static final long serialVersionUID = 1026382099769213789L;
	private Set<CollectionElement> elements;
	
	public EugeneCollection(String name) {
		super(name);
		this.elements = new HashSet<CollectionElement>();		
	}
	
	public void setElements(Set<CollectionElement> elements) {
		this.elements = elements;
	}
	
	public Set<CollectionElement> getElements() {
		return this.elements;
	}
	
	public void add(CollectionElement element) {
		this.elements.add(element);
	}
	
	public Set<Device> getDevices() {
		Set<Device> devices = new HashSet<Device>();
		for(CollectionElement element : this.elements) {
			if(element instanceof Device) {
				devices.add((Device)element);
			}
		}
		return devices;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String NEWLINE = System.getProperty("line.separator");
		
		sb.append("Collection ").append(this.getName()).append("(");
		if(null != elements && !elements.isEmpty()) {
			sb.append(NEWLINE);
			for(CollectionElement element : elements) {
				sb.append("\t").append(element.toString()).append(NEWLINE);
			}
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	@Override
	public void assign(NamedElement objElement) throws EugeneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String sElementName, NamedElement objElement)
			throws EugeneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(int idx, NamedElement objElement) throws EugeneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(NamedElement objElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NamedElement get(int index) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedElement get(String sName) throws EugeneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
