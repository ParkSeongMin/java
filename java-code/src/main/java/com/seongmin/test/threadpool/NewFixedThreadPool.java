package com.seongmin.test.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFixedThreadPool implements Runnable {
	private String tab = "";

	public static void main(String argv[]) {
		// thread pool ��
		ExecutorService es = Executors.newFixedThreadPool(2);
		/*
		System.out.println("--- thread pool create ---");
		System.out.println("Thread count : [" + Thread.activeCount() + "]");
		Thread.currentThread().getThreadGroup().list();		
		 */
		for(int i = 0; i < 3; i++) {
			NewFixedThreadPool t = new NewFixedThreadPool(i*3);
			// thread pool �� ���
			es.execute(t);
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	

		//es.shutdown();
	}

	// ����
	public NewFixedThreadPool(int tab) {
		for(int i = 0; i<=tab;i++) {
			this.tab += " ";
		}
	}

	// thread ����
	public void run() {
		for(int i = 0; i < 3; i++) {
			System.out.println(tab + i);
		}
	}
}
