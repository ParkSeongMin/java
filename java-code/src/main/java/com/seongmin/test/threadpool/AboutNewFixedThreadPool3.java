package com.seongmin.test.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class  ThreadPoolTask3 implements Callable<String> {

	String number = null;
	int tmp = 0;

	public ThreadPoolTask3(int i) {
		StringBuffer buff = new StringBuffer();
		buff.append(i);
		number = buff.toString();

		tmp = i;
	}

	public String call() {
		//		try {
		//			Thread.sleep(100);
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		System.out.println("Thread name : " +  Thread.currentThread().getName() );

		//		if( (tmp%10) == 0) {
		//			try {
		//				Thread.sleep(10000);
		//			} catch (InterruptedException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//		}else {
		//			System.out.println("tmp : " + tmp);
		//			try {
		//				Thread.sleep(1000);
		//			} catch (InterruptedException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//		}

		return number;
		//return null;
	}
}

class AAA extends Thread {
	public void run() {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ExecutorService threadPool = Executors.newFixedThreadPool(8);
		

		for(int count = 0; count < 10000; count++) {
			
			List<Future<String>> futureList = new ArrayList<Future<String>>();
			
			for(int i = 0; i < 10; i++) {
				ThreadPoolTask3 threadPoolTask = new ThreadPoolTask3(i);
				futureList.add( threadPool.submit(threadPoolTask) );
				threadPool.submit(threadPoolTask) ;
			}

			for(Future<String> future : futureList){   
				try {
					String aaaa = future.get();
					System.out.println("aaaa : " + aaaa);
				} catch (InterruptedException e) {

				} catch (ExecutionException e) {

				}
			}
		}
		System.out.println("AAAAAAAAA");
		if( !(threadPool.isShutdown()) ) {
			threadPool.shutdown();
		}
	}
}

public class AboutNewFixedThreadPool3 {
	public static void main(String[] argv) {

		for(int i = 0; i < 50; i++) {
			AAA aaa = new AAA();
			aaa.start();
		}


	}
}