package com.seongmin.test.reflection;

import java.lang.reflect.Method;

public class ReflectionPerformanceTest {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Blah x = new Blah();
		ComplexClass cc = new ComplexClass();
		test((Object) x, cc);
	}

	public static void test(Object x, ComplexClass cc) {
		
		// 여러종류의 테스트를 한번에 하는 것은 좋지 않다. 
		
		long start, end;
		long time1, time2, time3 = 0;
		int numToDo = 10000000;
		MyInterface interfaceClass = (MyInterface) x;

		// warming up the cache
		for (int i = 0; i < numToDo; i++) {
			cc.doSomething(i); // calls a method directly
		}

		start = System.currentTimeMillis();
		for (int i = 0; i < numToDo; i++) {
			cc.doSomething(i); // calls a method directly
		}
		end = System.currentTimeMillis();
		time1 = end - start;

		start = System.currentTimeMillis();
		for (int i = 0; i < numToDo; i++) {
			interfaceClass.doSomething(i); // casts an object to an interface
											// then calls the method
		}
		end = System.currentTimeMillis();
		time2 = end - start;

		try {
			Class xClass = x.getClass();
			Class[] argTypes = { int.class };
			Method m = xClass.getMethod("doSomething", argTypes);
			Object[] paramList = new Object[1];
			start = System.currentTimeMillis();
			for (int i = 0; i < numToDo; i++) {
				paramList[0] = i;
				m.invoke(x, paramList); // calls via reflection
			}
			end = System.currentTimeMillis();
			time3 = end - start;

		} catch (Exception ex) {
		}

		System.out.println("calling a method directly: " + time1);
		System.out.println("calling a method directly from an object cast to an interface: " + time2);
		System.out.println("calling a method through reflection: " + time3);
	}

	private static interface MyInterface {
		void doSomething(int i);
	}
	
	private static class Blah implements MyInterface {

		public void doSomething(int i) {

		}

	}

	private static class ComplexClass {
		public void doSomething(int i) {

		}
	}

}
