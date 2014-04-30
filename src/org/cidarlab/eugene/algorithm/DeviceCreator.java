package org.cidarlab.eugene.algorithm;

import org.cidarlab.eugene.cache.DesignSpace;

public class DeviceCreator
	implements Runnable {

	private long nDeviceId;
	private String newDeviceName;
	private long[] l;
	
	public DeviceCreator(long nDeviceId, String newDeviceName, long[] l) {
		this.nDeviceId= nDeviceId;
		this.newDeviceName = newDeviceName;
		this.l = l;
	}
	
	@Override
	public void run() {
		//DesignSpace.createPermutation(this.nDeviceId, this.newDeviceName, this.l);
	}

}
