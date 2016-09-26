package com.seongmin.test.hashcode;

import java.util.HashMap;
import java.util.Map;

public class HashCodeTest {

	private String	a;
	private int		b;

	public HashCodeTest(String a, int b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(String a) {
		this.a = a;
	}

	/**
	 * @return the b
	 */
	public int getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(int b) {
		this.b = b;
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((a == null) ? 0 : a.hashCode());
//		result = prime * result + b;
//		return result;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashCodeTest other = (HashCodeTest) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b != other.b)
			return false;
		return true;
	}

	public static void main(String[] args) {

		HashCodeTest test1 = new HashCodeTest("aaa", 1);
		HashCodeTest test2 = new HashCodeTest("aaa", 1);
		
		System.out.println(test1.hashCode());
		
//		test1.setA("aa");
//		test1.setB(1);

		System.out.println("aa".hashCode());
		System.out.println(new Integer(1).hashCode());
		System.out.println(test1.hashCode());
		
		
		Map<HashCodeTest, String> map = new HashMap<HashCodeTest, String>();
		map.put(test1, "test1");
		
		 
		System.out.println(test1.equals(test2)); // true
		System.out.println(map.containsKey(test1));; // true
		System.out.println(map.containsKey(test2));; // false
		 
		map.put(test2, "test2");
		 
		System.out.println(map.size()); // 2
		System.out.println(map.get(test1)); // "test1"
		System.out.println(map.get(test2)); // "test2"
		System.out.println(map.containsKey(test2));; // true
		
		
	}

}
