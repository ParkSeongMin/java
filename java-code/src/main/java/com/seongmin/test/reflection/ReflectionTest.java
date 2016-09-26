package com.seongmin.test.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReflectionTest {

	public static void main(String[] args) {
		
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		
		Class clazz = null;
		try {
			clazz = contextClassLoader.loadClass("com.seongmin.test.reflection.ReflectionTarget");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Method method = null;
		
		try {
			method = clazz.getMethod("test", new Class[]{});
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			method.invoke(newInstance, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
}
