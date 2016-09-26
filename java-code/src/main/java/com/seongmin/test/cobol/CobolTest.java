package com.seongmin.test.cobol;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.Assert;

import org.junit.Test;

public class CobolTest {
	
	
	
	@Test
	public void testCobol() {

		
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		
		URLClassLoader urlLoader = null;
		try {
//			urlLoader = new URLClassLoader(new URL[]{new File("./lib/cobol/CobolDemo.jar").toURL()}, contextClassLoader, new Factory());
			urlLoader = new URLClassLoader(new URL[]{new File(".").toURL()}, contextClassLoader);
		} catch (MalformedURLException e) {
			Assert.fail(e.getMessage());
		}
		
		
//		Class clazz = null;
//		try {
//			clazz = contextClassLoader.loadClass("com.microfocus.book.BookLegacy");
//		} catch (ClassNotFoundException e) {
//			fail(e.getMessage());
//		}
//		
		Class<?> forName = null;
		try {
//			forName = urlLoader.loadClass("com.microfocus.book.BookLegacy");
			forName = Class.forName("com.microfocus.book.BookLegacy", true, urlLoader);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		
		
		Field[] declaredFields = forName.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			System.out.println(declaredFields[i].getName());
		}
		
		Method[] declaredMethods = forName.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			System.out.println(declaredMethods[i].getName());
		}
		
		Annotation[] declaredAnnotations = forName.getDeclaredAnnotations();
		for (int i = 0; i < declaredAnnotations.length; i++) {
			System.out.println(declaredAnnotations[i]);
		}
		
		try {
			forName.newInstance();
		} catch (InstantiationException e) {
			Assert.fail(e.getMessage());
		} catch (IllegalAccessException e) {
			Assert.fail(e.getMessage());
		}
		
//		com.microfocus.book.BookLegacy legacy = new com.microfocus.book.BookLegacy();
//		ObjectControl get__MF_CONTROL = legacy.get__MF_CONTROL();
//		legacy.BookLegacy("0", new com.microfocus.book.Details(), new com.microfocus.book.FileStatus());
		
		System.out.println("dd");
		
		
	}

	@Test
	public void testDashField() {
		
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		
		Class<?> forName = null;
		try {
			forName = Class.forName("cobol.DashFieldTest", true, contextClassLoader);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		
		System.out.println("testDash");
		
		Field[] declaredFields = forName.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			System.out.println(declaredFields[i].getName());
		}
		
		Method[] declaredMethods = forName.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			System.out.println(declaredMethods[i].getName());
		}
		
		try {
			forName.newInstance();
		} catch (InstantiationException e) {
			Assert.fail(e.getMessage());
		} catch (IllegalAccessException e) {
			Assert.fail(e.getMessage());
		}
		
//		new DashFieldTest().test();
		
	}
	
}
