package com.seongmin.test.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class Policys implements RejectedExecutionHandler {

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println( "threadpool full." );
		
		try {
			executor.getQueue().put(r);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Work implements Callable<String>{

	long[] delays;

	ReentrantLock[] rlock;

	public String call() {

		long startTime = System.currentTimeMillis();
		//rlock[count].lock();
		//try {
		System.out.println("WorkThread : " + Thread.currentThread().getName() );
		//}finally {
		//	rlock[count].unlock();
		//}
		long endTime = System.currentTimeMillis();

		long resultTime = endTime - startTime;
		System.out.println("endTime - startTime : " + resultTime + " : " + Thread.currentThread().getName() );

		return null;
	}

}

public class ThreadPoolUsedFuture2 {
	
	static ThreadPoolExecutor		threadPool		= null;
	static long						keepAliveTime	= 10000; 
    static int						poolSize  		= 2;
    static int						maxPoolSize		= 4;
    static ReentrantLock 			rlock[]			= null;
	
	public static void main(String[] argv) {
		
		Policys policy = new Policys();
		
		threadPool = new ThreadPoolExecutor(
    			poolSize, 			// Pool MIN ũ��
    			maxPoolSize, 		// Pool MAX ũ��
    			keepAliveTime, 		// 
    			TimeUnit.MILLISECONDS, 	// 
    			new SynchronousQueue<Runnable>(),     // 
    			policy		 		// 
    	);
		
		List<Future<String>> futureList = new ArrayList<Future<String>>();
		
		rlock = new ReentrantLock[10];
		for(int i = 0; i < 10; i++) {
			rlock[i] = new ReentrantLock();
		}
		
		for(int count = 0; count < 10; count++) {
			Work workThread = new Work();
			
			/*
			Future<String> future =  threadPool.submit(workThread);
			try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			futureList.add(threadPool.submit(workThread));
		}
		
		for(Future<String> future : futureList){
			try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		
		return;
	}
}
