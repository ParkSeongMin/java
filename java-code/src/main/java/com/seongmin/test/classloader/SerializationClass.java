package com.seongmin.test.classloader;

import java.io.Serializable;


public class SerializationClass implements Serializable {
	
    private static final long serialVersionUID = 5024741671582526226L;

    public SerializationClass() {
    }
    
    public static void test() {
    	System.out.println("ddd");
    }
    
    @Override
    public String toString() {
    	System.out.println("toString");
    	return "test";
    }
}