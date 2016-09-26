package com.seongmin.test.inet;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {
	
	public static void main(String[] args) {
		
		printIpAddress();
		printIpAddressManually();
		printHostName();
		
	}
	
	public static void printIpAddress() {
		
		String hostAddress = null;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		System.out.println("InetAddress.getLocalHost().getHostAddress():" + hostAddress);
		
	}
	
	public static void printIpAddressManually() {
		
		try {

			InetAddress inetAddr = InetAddress.getLocalHost();

			byte[] addr = inetAddr.getAddress();

			// Convert to dot representation
			String ipAddr = "";
			for (int i = 0; i < addr.length; i++) {
				if (i > 0) {
					ipAddr += ".";
				}
				ipAddr += addr[i] & 0xFF;
			}

			String hostname = inetAddr.getHostName();

			System.out.println("IP Address: " + ipAddr);
			System.out.println("host name: " + hostname);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void printHostName() {
		
		String machineName = "Unknown";

		try {
			machineName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		System.out.println("host name=" + machineName);
	}
	
	

}
