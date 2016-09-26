package com.seongmin.test.bean;

public class BeanObject {

	private String			string;
	private int				intVal;

	private static String	test;

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getIntVal() {
		return intVal;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public static String getTest() {
		return test;
	}

	public static void setTest(String test) {
		BeanObject.test = test;
	}

}
