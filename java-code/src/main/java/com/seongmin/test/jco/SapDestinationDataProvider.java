package com.seongmin.test.jco;


import java.util.HashMap;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SapDestinationDataProvider implements DestinationDataProvider{

	private DestinationDataEventListener eL;
	private HashMap<String ,Properties> destinationPropertyMap = new HashMap<String, Properties>();
	
	public SapDestinationDataProvider()
	{
		System.out.println();
	}
	
	public void changeProperties(String destinationName , Properties properties) {
		
		Properties desProperty = destinationPropertyMap.get(destinationName);
		
		if(desProperty==null) {
			eL.deleted(destinationName);
			destinationPropertyMap.put(destinationName, properties);
		}
		else {
			if (desProperty!=null &&
					!desProperty.equals(properties)) {
				destinationPropertyMap.put(destinationName, properties);
				eL.updated(destinationName); 
			}
		}
	}
	
	
	
	public Properties getDestinationProperties(String destinationName) {
		return destinationPropertyMap.get(destinationName);
	}
	public void setDestinationDataEventListener(
			DestinationDataEventListener eventListener) {
		this.eL = eventListener;
	}
	public boolean supportsEvents() {
		return true;
	}
}
