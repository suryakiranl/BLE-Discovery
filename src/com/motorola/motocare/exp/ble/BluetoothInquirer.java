package com.motorola.motocare.exp.ble;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

/**
 * This class is a Bluetooth Inquiry - DiscoveryListener that can be used to 
 * look for all accessible Bluetooth devices in the vicinity.
 * 
 * @author suryak
 *
 */
public class BluetoothInquirer implements DiscoveryListener {
	private boolean inquiring;
	private List<RemoteDevice> devices;
	
	/**
	 * This method is called every time a new device is identified
	 */
	@Override
	public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
		System.out.println(":: Inside deviceDiscovered :: ");
		devices.add(remoteDevice);
		printRemoteDeviceDetails(remoteDevice);
		printDeviceClassDetails(deviceClass);
	}

	/**
	 * This method is called once the inquiry mode is completed.
	 */
	@Override
	public void inquiryCompleted(int statusCode) {
		System.out.println("Inquiry complete : " + statusCode);
		inquiring = false;
	}

	/**
	 * This method is called when a Service search is completed.
	 */
	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		System.out.println(":: Inside serviceSearchCompleted - Nothing implemented ::");
	}

	/**
	 * This method is called when a Service is discovered.
	 */
	@Override
	public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
		System.out.println(":: Inside servicesDiscovered - Nothing implemented ::");
	}
	
	/**
	 * This method should be used to start the discovery / inquiry process
	 * to look for nearby Bluetooth devices.
	 * 
	 * @return - Flag depicting whether the inquiry process started successfully
	 * or not.
	 */
	public boolean startInquiry() {
		System.out.println("Starting inquiry ...");
		inquiring = false;
		devices = new ArrayList<RemoteDevice>();
		
		try {
			inquiring = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, this);
		} catch (BluetoothStateException e) {
			System.out.println("Exception when trying to start enquiry: " + e.getMessage());
			e.printStackTrace();
		}
		
		return inquiring;
	}
	
	/**
	 * This utility method can be used to print the newly discovered remote device details.
	 * 
	 * @param rd - New RemoteDevice
	 */
	private void printRemoteDeviceDetails(RemoteDevice rd) {
		try {
			System.out.println("-- getFriendlyName = " + rd.getFriendlyName(false));
		} catch (IOException e) {
			System.out.println("Error when fetching remote device's friendly name" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("-- getBluetoothAddress = " + rd.getBluetoothAddress());
		System.out.println("-- isAuthenticated = " + rd.isAuthenticated());
		System.out.println("-- isEncrypted = " + rd.isEncrypted());
		System.out.println("-- isTrustedDevice = " + rd.isTrustedDevice());
	}
	
	/**
	 * This utility method can be used to print the newly discovered RemoteDevice's - DeviceClass
	 * 
	 * @param dc - DeviceClass
	 */
	private void printDeviceClassDetails(DeviceClass dc) {
		System.out.println("-- getMajorDeviceClass = " + dc.getMajorDeviceClass());
		System.out.println("-- getMinorDeviceClass = " + dc.getMinorDeviceClass());
		System.out.println("-- getServiceClasses = " + dc.getServiceClasses());
	}

	public boolean isInquiring() {
		return inquiring;
	}

	public List<RemoteDevice> getDevices() {
		return devices;
	}
}
