package com.seongmin.test.systemproperty;

import java.util.Iterator;

public class SystemProperty {

	public static void main(String[] args) {
		
		Iterator iter = System.getProperties().keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			System.out.println("key : " + key + ", value : " + System.getProperty(key));
		}
		
	}
	
}
