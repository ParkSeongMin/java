package com.seongmin.test.type.bigdecimal;

import java.math.BigDecimal;

public class BigDecimalTest {

	public static void main(String[] args) {

		BigDecimal bd1 = new BigDecimal("1.11111111111111111111111111111111111");
		BigDecimal bd2 = new BigDecimal("1.111111111111111111111111111111111121111111111111111111111111111111111");
		System.out.println(bd1.add(bd2));
		System.out.println(1.11111111111111111111111111111111111+2.2222222222222222222222222222222222222222222);

		BigDecimal payment = new BigDecimal(2.00); // double 연산은 정확하지 않다.
		BigDecimal cost = new BigDecimal(1.10);
		System.out.println(payment.subtract(cost));
		
		BigDecimal payment1 = new BigDecimal("2.00"); // 정확한 연산
		BigDecimal cost1 = new BigDecimal("1.10");
		System.out.println(payment1.subtract(cost1));
		
		BigDecimalTest test = new BigDecimalTest();
		test.test(null, "test", "Test", null);
		
	}

	public <T> void test(T... test) {
		
		int i=0; 
		for(T element: test) {
			System.out.println(++i);
			if(element != null) {
				System.out.println(element.toString());
			}
		}
		
	}
	
}
