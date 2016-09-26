package com.seongmin.test.clazz;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;

public class ClassTest {

	@Test
	public void testClassMatch() {
		
		Class clazz1 = TestClass.class;
		Class clazz2 = TestClass.class;

		System.out.println(clazz1.equals(clazz2));
		
		Assert.assertEquals(clazz1, clazz2);

		
		try {
			Method method = clazz1.getMethod("testMethod", null);
			Method method2 = clazz1.getMethod("testMethod", null);
		
			System.out.println(method.equals(method2));
			
			Assert.assertEquals(method, method2);
			
		} catch (SecurityException e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
	public class TestClass {
		
		public void testMethod() {
			
		}
		
	}
}
