package org.cidarlab.eugene.dom.arrays;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.exception.EugeneException;


public class PermutedDeviceArray 
		extends DeviceArray {

	private static final long serialVersionUID = -7717820454893218128L;
	
	private Device  device;
	private long[][] devices;
	
	public PermutedDeviceArray(
			Device device, long[][] devices) {
		super(device.getName()+"_PERMUTE");
		this.device = device;
		this.devices = devices;
	}
	
	public String getDeviceName() {
		return this.device.getName();
	}

	public long[] getDevices() {
		return null;
	}
	
	@Override
	public int size() {
		return this.devices.length;
	}
	
	@Override
	public NamedElement get(int idx) 
			throws EugeneException {

		if(null != this.devices) {
			if(idx >= 0 && idx < this.devices.length) {
//				System.out.println("[PermutedDeviceArray] -> "+Arrays.toString(this.devices[idx]));
				
				return EugeneBuilder.buildDevice(
						this.getDeviceName()+"_"+(idx+1), 
						this.devices[idx], 
						this.device.getDirections());
			}
		}
		return null;
	}
	
	public void add(long deviceId) {

	}
	
	public void addAll(long[] devices) {

	}
	
	public void remove(int idx) {
		if(idx>=0 && idx<this.devices.length) {
			this.devices = ArrayUtils.remove(this.devices, idx);
		}
	}
	
	public void add(String sDeviceName) {

	}

	public void addAll(DeviceArray objArray) {

	}
	
}
