package com.seongmin.test.innerclass;

public class InnerClassTest {

	public static void main(String[] args) {
		System.out.println("InnerClassTest.main()");
	}

	public void anonymousClass() {
		InnerClassInterface anonymous = new InnerClassInterface() {
			public void interfaceMethod() {
				
			}
		};
	}
	
	class InnerClassSample implements InnerClassInterface {

		public void interfaceMethod() {
			
		}
		
	}
	
}
