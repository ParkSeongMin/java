package com.seongmin.test.clazz;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class ClassMethodTest {

	@Test
	public void getMethodTest() {

	}
	
	public class Generics<T> {
	    public void method1(T t) {
	    }
	}
	
	public class NonGenerics {
		public void method1(Object t) {
		}
	}
	
	public class ListGenerics {
		public void method1(List<String> t) {
		}
	}
	
	public class ListNonGenerics {
		public void method1(List t) {
		}
	}
	
	public class Arrays {
		public void method1(String[] t) {
		}
	}
	
	@Test	
	public void testGenericParameterTypeVSParameterType() {

		Method method = null;
		try {
			method = Generics.class.getMethod("method1", new Class[]{Object.class});
		} catch (SecurityException e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			Assert.fail(e.getMessage());
		}
		for (Type t : method.getGenericParameterTypes()) {
			System.out.println("with GPT: " + t);
		}
		for (Type t : method.getParameterTypes()) {
			System.out.println("with PT: " + t);
		}
		
		System.out.println();
		
		try {
			method = NonGenerics.class.getMethod("method1", new Class[]{Object.class});
		} catch (SecurityException e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			Assert.fail(e.getMessage());
		}
		for (Type t : method.getGenericParameterTypes()) {
			System.out.println("with GPT: " + t);
		}
		for (Type t : method.getParameterTypes()) {
			System.out.println("with PT: " + t);
		}

		System.out.println();
		
		try {
			method = ListGenerics.class.getMethod("method1", new Class[]{List.class});
		} catch (SecurityException e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			Assert.fail(e.getMessage());
		}
		for (Type t : method.getGenericParameterTypes()) {
			System.out.println("with GPT: " + t);
		}
		for (Type t : method.getParameterTypes()) {
			System.out.println("with PT: " + t);
		}

		System.out.println();
		
		try {
			method = ListNonGenerics.class.getMethod("method1", new Class[]{List.class});
		} catch (SecurityException e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			Assert.fail(e.getMessage());
		}
		for (Type t : method.getGenericParameterTypes()) {
			System.out.println("with GPT: " + t);
		}
		for (Type t : method.getParameterTypes()) {
			System.out.println("with PT: " + t);
		}
		
		System.out.println();
		
		try {
			method = Arrays.class.getMethod("method1", new Class[]{String[].class});
		} catch (SecurityException e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			Assert.fail(e.getMessage());
		}
		for (Type t : method.getGenericParameterTypes()) {
			System.out.println("with GPT: " + t);
		}
		for (Class t : method.getParameterTypes()) {
			System.out.println("with PT: " + t);
			System.out.println("with PT: " + t.getComponentType());
		}
		

	}

	@Test
	public void testGetMethodInfo() {
		try {
//			Class cls = Class.forName("ClassMethodTest");
			Class cls = TestClassImpl.class;

			Method methlist[] = cls.getDeclaredMethods();
			for (int i = 0; i < methlist.length; i++) {
				Method m = methlist[i];
				System.out.println("name =" + m.getName());
				System.out.println("decl class = " + m.getDeclaringClass());

				Class pvec[] = m.getParameterTypes();
				for (int j = 0; j < pvec.length; j++)
					System.out.println("param #" + j + " " + pvec[j]);

				Class evec[] = m.getExceptionTypes();
				for (int j = 0; j < evec.length; j++)
					System.out.println("exc #" + j + " " + evec[j]);

				System.out.println("return type = " + m.getReturnType());
				System.out.println("-----");
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	@Test
	public void testGetDeclaredMethods() {

		Class clazz = TestClassImpl.class;

		// 동일 한 instance 인지 체크
		System.out.println(clazz.isInstance(new TestClassImpl() {
			public void test() {
			}

			@Override
			public void testAbstract() {
				// TODO Auto-generated method stub

			}
		}));

		// getMethods 는 자신의 것 외에 추가적으로 superclass 와 interface 모두 다 반환한다.
		System.out.println("############  methods");
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i].toString());
		}

		// getDeclaredMethods 는 자신이 정의 한 것 만.
		System.out.println("############  declaredMethods");
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			System.out.println(declaredMethods[i].toString());
		}

	}

	public interface TestClass {

		void test();
	}

	public abstract class AbstractTestClass implements TestClass {

		public void test() {

		}

		public abstract void testAbstract();

		public void testMethod(String a) {

		}
	}

	public class TestClassImpl extends AbstractTestClass {

		public void test(String test) {

		}

		@Override
		public void testAbstract() {
		}

		public void testMethod() {

		}

		public void testImpl() {

		}
	}

}
