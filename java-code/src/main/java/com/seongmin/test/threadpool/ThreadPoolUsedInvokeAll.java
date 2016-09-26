package com.seongmin.test.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Policy2 implements RejectedExecutionHandler {

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

		// 2011.04.05
		try {
			executor.getQueue().put(r);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

}

public class ThreadPoolUsedInvokeAll {
	public static void main(String[] argv) {

		ThreadPoolExecutor				threadPool		= null;

		Policy2 policy = new Policy2();

		/*
    	// 2011.04.05
    	ThreadQueue		= new ArrayBlockingQueue<Runnable>( queueSize );
		 */
		threadPool = new ThreadPoolExecutor(
				16, 			// Pool MIn
				32, 		// Pool MAX
				1000, 		// 
				TimeUnit.MILLISECONDS, 	
				new SynchronousQueue<Runnable>(),     
				policy		 		
		);
		List<Future<String>> futureList = new ArrayList<Future<String>>();
		for(int i =0 ; i < 10; i++) {
			WorkThread workThread = new WorkThread();
			futureList.add(threadPool.submit(workThread));
		}

		System.out.println("check point 1");

		for(Future<String> future : futureList){
			try {
				System.out.println("^^");
				future.get();

			} catch (InterruptedException e) {
				e.printStackTrace();

			} catch (ExecutionException e) {

				e.printStackTrace();
			}
		}

		System.out.println("check point 2");

		if (threadPool != null) {
			threadPool.shutdown();
			try {
				//threadPool.awaitTermination(10, TimeUnit.SECONDS);
				threadPool.awaitTermination(5000L, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} finally {
				if (threadPool.isShutdown())
					threadPool.shutdownNow();
			}
		}

		System.out.println("check point 3");

		return;
	}
}
