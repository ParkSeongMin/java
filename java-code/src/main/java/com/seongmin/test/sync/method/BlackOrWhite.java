package com.seongmin.test.sync.method;

public class BlackOrWhite {
	private String			str;
	private final String	BLACK	= "black";
	private final String	WHITE	= "white";

	public synchronized void black() {
//		str = BLACK;
		System.out.println("#### black : " + str);
		try {
			long sleep = (long) (Math.random() * 100L);
			Thread.sleep(sleep);
			if (!str.equals(BLACK)) {
				System.out.println("+++++++++++++++++broken!!++++++++++++++++++++");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void white() {
//		str = WHITE;
		System.out.println("---- white : " + str);
		try {
			long sleep = (long) (Math.random() * 100L);
			Thread.sleep(sleep);
			if (!str.equals(WHITE)) {
				System.out.println("+++++++++++++++++broken!!++++++++++++++++++++");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void test() {
		System.out.println("dd");
	}
	public synchronized void test1() {
		System.out.println("ddd");
		sholudHoldingRequest = true;
		synchronized (count) {
			System.out.println(count);
			count++;
			System.out.println(count);
		}
	}
	private static volatile boolean	sholudHoldingRequest	= false;	
	private static volatile Integer	count	= new Integer(0);
	
	public void test3() {
		System.out.println("dddd");
		System.out.println(sholudHoldingRequest);
		synchronized (count) {
			System.out.println(count);
			count--;
			System.out.println(count);
		}
	}
	
}
