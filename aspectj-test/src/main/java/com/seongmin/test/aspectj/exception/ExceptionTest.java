package com.seongmin.test.aspectj.exception;

public class ExceptionTest {

	public static void main(String[] args) {
		ExceptionTest instance = new ExceptionTest();
        instance.test();
    }
 
    public void test() {
 
    	ExceptionThrower dummyException = new ExceptionThrower();
 
        try {
        	dummyException.throwException();
        } catch (Exception e) {
            System.err.println("Caught by handler: " + e.toString());
            e.printStackTrace();
        }
    }
	
}
