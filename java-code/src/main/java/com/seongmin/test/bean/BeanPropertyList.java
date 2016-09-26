package com.seongmin.test.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BeanPropertyList {
	public static Map getProperties(Object bean) throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		
		// JavaBean 객체의 클래스에 대한 BeanInfo 객 체를 얻어온다.
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());

		// 모든 프로퍼티의 PropertyDescriptor객체를 얻어온다.
		PropertyDescriptor descriptor[] = beanInfo.getPropertyDescriptors();

		// PropertyDescriptor 배열의 정보를 Map 객체에 설정하여 리턴한다.
		Map map = new HashMap(descriptor.length);
		Object value;

		for (int i = 0, n = descriptor.length; i < n; i++) {
			
			// static 은 별도 처리해야 하며, boolean의 값도 마찬가지로 별도 처리 해야 한다.
			
			// 프로퍼티의 설정값을 조회하기위하여 Read Method를 얻어온다.
			// 디폴트로 get<PropertyName>()메소드이며, 자바 빈즈 스팩에서는
			// BeanInfo 클래스에서 정의된 다른 이름을 사용할 수 있도록 허용한다.
			Method read = descriptor[i].getReadMethod();
			if (read != null) {
				// Reflection API를 통하여 Read Method를 호출한다.
				// Read Method는 인자를 가질 수 없으므로 인자로 빈 배열 객체를
				// 넘겨준다.
				value = read.invoke(bean, new Object[0]);
				map.put(descriptor[i].getName(), value);
			}
		}
		return map;
	}

	/**
	 * JButton 빈 객체의 모든 프로퍼티 정보를 디스프레이한다.
	 */
	public static void main(String args[]) throws Exception {
//		JButton button = new JButton("Help");
		BeanObject obj = new BeanObject();

		// 모든 프로퍼티를 출력
		Map map = BeanPropertyList.getProperties(obj);
		Set entrySet = map.entrySet();

		for (Iterator iter = entrySet.iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			System.out.println(entry.getKey() + ":\t" + entry.getValue());
		}
	}
}
