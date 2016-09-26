package com.seongmin.test.clazz;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

public class ClassFieldTest {

	private TestMakeClass	makeClass;
	private String			FIELD_NAME		= "fieldName";
	private String			FIELD_ATTRIBUTE	= "fieldAttribute";
	private String			MAKE_VALUE		= "testMakeValue";
	private String			MAKE_ATTRIBUTE	= "string";

	@Before
	public void setUp() {
		makeClass = new TestMakeClass();
		makeClass.setFieldName(MAKE_VALUE);
		makeClass.setFieldAttribute(MAKE_ATTRIBUTE);
	}

	/**
	 * private 접근제어 선언이 되어 있는 경우, field.setAccessible(true) 선언을 해주면 접근이 가능하다.
	 */
	@Test
	public void testClassGetPrivateField() {

		Field field = null;
		try {
			field = makeClass.getClass().getDeclaredField(FIELD_NAME);
			field.setAccessible(true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		String fieldNameValue = null;
		try {
			fieldNameValue = (String) field.get(makeClass);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		assertThat(fieldNameValue, is(makeClass.getFieldName()));
	}

	/**
	 * private 선언된 필드 정보를 가져오려고 하면 IllegalAccessExceptino이 발생함.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test(expected = IllegalAccessException.class)
	public void testClassGetPrivateFieldThrowsEception() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {

		Field field = makeClass.getClass().getDeclaredField(FIELD_NAME);

		String fieldNameValue = (String) field.get(makeClass);
		assertThat(fieldNameValue, is(makeClass.getFieldName()));
	}

	@Test
	public void testClassGetPublicField() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {

		Field field = makeClass.getClass().getDeclaredField(FIELD_ATTRIBUTE);

		String fieldAttribute = (String) field.get(makeClass);
		assertThat(fieldAttribute, is(makeClass.getFieldAttribute()));
	}

	@Test
	public void testClassGetFileds() throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = makeClass.getClass().getFields();
		System.out.println("fields size is[" + fields.length + "]");

		for (Field field : fields) {
			// public 선언되어 있는 필드 정보만 나타난다.
			String fieldValue = (String) field.get(makeClass);
			System.out.println(fieldValue);

		}
	}

	@Test
	public void testClassFiledInfo() {
		try {
			Class cls = TestMakeClass.class;

			Field fieldlist[] = cls.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				Field fld = fieldlist[i];
				System.out.println("name = " + fld.getName());
				System.out.println("decl class = " + fld.getDeclaringClass());
				System.out.println("type = " + fld.getType());
				int mod = fld.getModifiers();
				System.out.println("modifiers = " + Modifier.toString(mod));
				System.out.println("-----");
			}
		}
		catch (Throwable e) {
			System.err.println(e);
		}
	}
	
	public class TestMakeClass {
		private String	fieldName;
		public String	fieldAttribute;

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldAttribute() {
			return fieldAttribute;
		}

		public void setFieldAttribute(String fieldAttribute) {
			this.fieldAttribute = fieldAttribute;
		}

	}
}