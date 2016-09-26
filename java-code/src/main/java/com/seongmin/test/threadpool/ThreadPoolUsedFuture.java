package com.seongmin.test.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Policy implements RejectedExecutionHandler {

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

		// 2011.04.05
		try {
			executor.getQueue().put(r);
			
			System.out.println("+++++++++++++ " +executor.getQueue().size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// Ǯ�̳� ť�� ���� ó���� ���Ҷ� ó�� �ϴ� handler
		// ���� �޼���
	}

}

class WorkThread implements Callable<String>{

	public WorkThread() {

	}

	public String call() {
		System.out.println(WorkThread.class.getName());
//		try {
//			Thread.sleep(1000);
//			//System.out.println(WorkThread.class.getName());
//		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		
		for(long i=0; i<1000000000; i++) {
			
		}
		

		return Thread.currentThread().getName();
	}

}

class RunnableThread extends Thread{
	
	public RunnableThread() {
		
	}

	public void run() {
		System.out.println(WorkThread.class.getName());
		try {
			Thread.sleep(5000);
			//System.out.println(WorkThread.class.getName());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long startTime = System.currentTimeMillis();
		
	}
	
}

class TestThreadPoolExecutor extends ThreadPoolExecutor {

	public TestThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

	
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		System.out.println("before : " + Thread.currentThread().getName());
	}



	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		System.out.println("after : " + Thread.currentThread().getName());
//		System.out.println("t : " + t.getMessage());
	}
	
	
	
}


public class ThreadPoolUsedFuture {
	public static void main(String[] argv) {

		ThreadPoolExecutor				threadPool		= null;

		Policy policy = new Policy();
		List<Future<String>> futureList = null;
		/*
    	// 2011.04.05
    	ThreadQueue		= new ArrayBlockingQueue<Runnable>( queueSize );
		 */
//		threadPool = new TestThreadPoolExecutor(
//				10, 			// Pool MIN ũ��
//				10, 		// Pool MAX ũ��
//				1000, 		// ���� thread �� MIN ���� ������, ��� ������ ��� �ִ� thread ���.
//				TimeUnit.MILLISECONDS, 	// ����
//				new SynchronousQueue<Runnable>(),     // ������ Ǯ�� ���� ť
//				policy		 		// Ǯ�̳� ť�� ���� ó���� ���Ҷ� ó�� �ϴ� handler
//		);
//		futureList = new ArrayList<Future<String>>();
//		for(int i =0 ; i < 10; i++) {
//			WorkThread workThread = new WorkThread();
//			futureList.add(threadPool.submit(workThread));
//		}
//
//		System.out.println("################################## check point 1");
////
////		for(Future<String> future : futureList){
////			try {
////				System.out.println("^^");
////				future.get();
////
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////
////			} catch (ExecutionException e) {
////
////				e.printStackTrace();
////			}
////		}
//		
//		
//		System.out.println("getActiveCount : " + threadPool.getActiveCount());
//		System.out.println("getCompletedTaskCount : " + threadPool.getActiveCount());
//		System.out.println("getCorePoolSize : " + threadPool.getCorePoolSize());
//		System.out.println("getTaskCount : " + threadPool.getTaskCount());
//		System.out.println("getLargestPoolSize : " + threadPool.getLargestPoolSize());
//		System.out.println("getMaximumPoolSize : " + threadPool.getMaximumPoolSize());
//		System.out.println("getPoolSize : " + threadPool.getPoolSize());
//		
//
//		System.out.println("################################## check point 2");
//
//		if (threadPool != null) {
//			threadPool.shutdown();
//			try {
//				//threadPool.awaitTermination(10, TimeUnit.SECONDS);
//				threadPool.awaitTermination(5000L, TimeUnit.MILLISECONDS);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//			} finally {
//				if (threadPool.isShutdown())
//					threadPool.shutdownNow();
//			}
//		}
//
//		System.out.println("getActiveCount : " + threadPool.getActiveCount());
//		System.out.println("getCompletedTaskCount : " + threadPool.getActiveCount());
//		System.out.println("getCorePoolSize : " + threadPool.getCorePoolSize());
//		System.out.println("getTaskCount : " + threadPool.getTaskCount());
//		System.out.println("getLargestPoolSize : " + threadPool.getLargestPoolSize());
//		System.out.println("getMaximumPoolSize : " + threadPool.getMaximumPoolSize());
//		System.out.println("getPoolSize : " + threadPool.getPoolSize());

		
		threadPool = new TestThreadPoolExecutor(
				10, 			// Pool MIN ũ��
				10, 		// Pool MAX ũ��
				1000, 		// ���� thread �� MIN ���� ������, ��� ������ ��� �ִ� thread ���.
				TimeUnit.MILLISECONDS, 	// ����
				new SynchronousQueue<Runnable>(),     // ������ Ǯ�� ���� ť
				policy		 		// Ǯ�̳� ť�� ���� ó���� ���Ҷ� ó�� �ϴ� handler
		);
		
		
		futureList = new ArrayList<Future<String>>();
		for(int i =0 ; i < 10; i++) {
			WorkThread runnableThread = new WorkThread();
			futureList.add(threadPool.submit(runnableThread));
			System.out.println(i+" : "+threadPool.getQueue().size());
			
		}

		System.out.println("################################## check point 3");

		for(Future<String> future : futureList){
			System.out.println("^^");
			try {
				System.out.println(future.get());
//				System.out.println(future.cancel(true));
//				System.out.println(" $$$$$$$$$$$$$: "+threadPool.getQueue().size());
			} catch(Throwable e) {
				e.printStackTrace();
			}

		}
		
		System.out.println("getQueue().size() : "+threadPool.getQueue().size());
		System.out.println("getActiveCount : " + threadPool.getActiveCount());
		System.out.println("getCompletedTaskCount : " + threadPool.getActiveCount());
		System.out.println("getCorePoolSize : " + threadPool.getCorePoolSize());
		System.out.println("getTaskCount : " + threadPool.getTaskCount());
		System.out.println("getLargestPoolSize : " + threadPool.getLargestPoolSize());
		System.out.println("getMaximumPoolSize : " + threadPool.getMaximumPoolSize());
		System.out.println("getPoolSize : " + threadPool.getPoolSize());

		System.out.println("################################## check point 4");
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		
//		System.out.println("getActiveCount : " + threadPool.getActiveCount());
//		System.out.println("getCompletedTaskCount : " + threadPool.getActiveCount());
//		System.out.println("getCorePoolSize : " + threadPool.getCorePoolSize());
//		System.out.println("getTaskCount : " + threadPool.getTaskCount());
//		System.out.println("getLargestPoolSize : " + threadPool.getLargestPoolSize());
//		System.out.println("getMaximumPoolSize : " + threadPool.getMaximumPoolSize());
//		System.out.println("getPoolSize : " + threadPool.getPoolSize());
//
//		System.out.println("################################## check point 5");
//		
//		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("getActiveCount : " + threadPool.getActiveCount());
		
		return ;
	}
	
}


class TestPool extends ThreadPoolExecutor {

	public TestPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

	
	
	
}
