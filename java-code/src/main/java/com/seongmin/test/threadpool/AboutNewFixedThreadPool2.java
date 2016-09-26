package com.seongmin.test.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class  ThreadPoolTask2 implements Callable<String> {

	String number = null;
	int tmp = 0;
	
	public ThreadPoolTask2(int i) {
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
		
		if( (tmp%10) == 0) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("tmp : " + tmp);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return number;
		//return null;
	}
}

public class AboutNewFixedThreadPool2 {
	public static void main(String[] argv) {
		ExecutorService threadPool = Executors.newFixedThreadPool(4);
		CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);
		
		for(int i = 0; i < 10; i++) {
			ThreadPoolTask2 threadPoolTask = new ThreadPoolTask2(i);
			pool.submit(threadPoolTask);
		}
		
		for(int i = 0; i < 10; i++) {
			try {
				String result = pool.take().get();
				System.out.println("result : " + result);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("AAAAAAAAA");
		if( !(threadPool.isShutdown()) ) {
			threadPool.shutdown();
		}
	}
}
