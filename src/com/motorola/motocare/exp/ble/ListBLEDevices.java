package com.motorola.motocare.exp.ble;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

public class ListBLEDevices {

	public static void main(String[] args) {
		LocalDevice myDevice = getLocalBluetoothDevice();
		if (myDevice == null) {
			System.out.println("ERROR: Local bluetooth device returned as NULL. Exiting program.");
			return;
		}
		printLocalBluetoothDeviceDetails(myDevice);

		scanAndPrintNearbyDevices();
	}

	private static void scanAndPrintNearbyDevices() {
		System.out.println(":: Inside scanAndPrintNearbyDevices ::");
		BluetoothInquirer inquirer = new BluetoothInquirer();

		// Step 1 : Start the inquiry process to look for other devices
		if (!inquirer.startInquiry()) {
			System.out.println("Could not start the inquiry process. Exiting now ...");
			return;
		}

		// Step2 : While the inquiry process is in progress, just wait for it to complete.
		while (inquirer.isInquiring()) {
			sleep(1000);
		}

		// Step 3: Print devices discovered
		System.out.println("Device count = " + inquirer.getDevices().size());

	}

	private static void sleep(long timeInMillis) {
		try {
			System.out.println("Sleeping for " + timeInMillis + " ms ...");
			Thread.sleep(timeInMillis);
		} catch (Exception e) {
			System.out.println("Something went wrong when waiting for Bluetooth inquiry process to complete: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static LocalDevice getLocalBluetoothDevice() {
		LocalDevice myDevice = null;

		try {
			System.out.println("isPowerOn = " + LocalDevice.isPowerOn());
			myDevice = LocalDevice.getLocalDevice();
			System.out.println("Local device obtained successfully!!");
		} catch (BluetoothStateException e) {
			System.out.println("Error when trying to get local device: " + e.getMessage());
			e.printStackTrace();
		}

		return myDevice;
	}

	private static void printLocalBluetoothDeviceDetails(LocalDevice d) {
		System.out.println("getBluetoothAddress = " + d.getBluetoothAddress());
		System.out.println("getDiscoverable = " + d.getDiscoverable());
		System.out.println("getFriendlyName = " + d.getFriendlyName());
	}

}
