package com.seongmin.test.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryUsed {
	public static void main(String[] argv) {

		TestThreadPoolExecutor1 threadPool = null;

		Policy1 policy1 = new Policy1();
		List<Future> futureList = null;

		threadPool = new TestThreadPoolExecutor1(
				8, // Pool MIN ũ��
				16, // Pool MAX ũ��
				10000, // ���� thread �� MIN ���� ������, ��� ������ ��� �ִ�
				TimeUnit.MILLISECONDS, // ����
				new SynchronousQueue<Runnable>(), // ������ Ǯ�� ���� ť
				new ThreadFactoryTest(), 
				policy1 // Ǯ�̳� ť�� ���� ó���� ���Ҷ� ó�� �ϴ� handler
		);

		futureList = new ArrayList<Future>();
		for (int i = 0; i < 100; i++) {
			RunnableThread1 runnableThread = new RunnableThread1();
			TestInstance ins = new TestInstance();
			ins.setTest1(i+"");
			ins.setTest1(i+1+"");
			runnableThread.setIns(ins);
			
//			if(i>9) {
//				System.out.println("");
//			}
			
//			futureList.add(threadPool.submit(runnableThread));
			threadPool.execute(runnableThread);
//			System.out.println(i + " : " + threadPool.getQueue().size());

		}
//		System.out.println("main : " +Thread.currentThread().getName());
//		System.out.println("################################## check point 1");
//		System.out.println("getQueue().size() : " + threadPool.getQueue().size());
//		System.out.println("getActiveCount : " + threadPool.getActiveCount());

//		for (Future<String> future : futureList) {
//			System.out.println("^^");
//			try {
//				System.out.println(future.cancel(true));
//				// System.out.println(" $$$$$$$$$$$$$: "+threadPool.getQueue().size());
//			} catch (Throwable e) {
//				e.printStackTrace();
//			}
//
//		}
		
		
		
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("################################## check point 2");
		System.out.println("getQueue().size() : " + threadPool.getQueue().size());
		System.out.println("getActiveCount : " + threadPool.getActiveCount());

//
//		System.out.println("getQueue().size() : " + threadPool.getQueue().size());
//		System.out.println("getActiveCount : " + threadPool.getActiveCount());
//		System.out.println("getCompletedTaskCount : " + threadPool.getActiveCount());
//		System.out.println("getCorePoolSize : " + threadPool.getCorePoolSize());
//		System.out.println("getTaskCount : " + threadPool.getTaskCount());
//		System.out.println("getLargestPoolSize : " + threadPool.getLargestPoolSize());
//		System.out.println("getMaximumPoolSize : " + threadPool.getMaximumPoolSize());
//		System.out.println("getPoolSize : " + threadPool.getPoolSize());
//
//		System.out.println("################################## check point 4");

		return;
	}

}


class Policy1 implements RejectedExecutionHandler {

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

		// 2011.04.05
		try {
			executor.getQueue().put(r);
			System.out.println(executor.getActiveCount());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

}

class TestThreadPoolExecutor1 extends ThreadPoolExecutor {

	public TestThreadPoolExecutor1(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		
//		System.out.println("before : " + Thread.currentThread().getName());
//		System.out.println("before : " + t.getName());
		
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
//		System.out.println("after : " + Thread.currentThread().getName());
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

}

class ThreadFactoryTest implements ThreadFactory {
	 
	static final AtomicInteger poolNumber = new AtomicInteger(1);
     final ThreadGroup group;
     final AtomicInteger threadNumber = new AtomicInteger(1);
     final String namePrefix;

     ThreadFactoryTest() {
         SecurityManager s = System.getSecurityManager();
         group = (s != null)? s.getThreadGroup() :
                              Thread.currentThread().getThreadGroup();
         namePrefix = "pool-" + 
                       poolNumber.getAndIncrement() + 
                      "-thread-";
     }

     public Thread newThread(Runnable r) {
         Thread t = new Thread(group, r, 
                               namePrefix + threadNumber.getAndIncrement(),
                               0);
         if (t.isDaemon())
             t.setDaemon(false);
         if (t.getPriority() != Thread.NORM_PRIORITY)
             t.setPriority(Thread.NORM_PRIORITY);
         
//         System.out.println("threadfactory : " + Thread.currentThread().getName());
         
         return t;
     }
	
}


class RunnableThread1 implements Runnable {

	private TestInstance	ins;

	public RunnableThread1() {
	}

	public void run() {
//		System.out.println("runner : " +Thread.currentThread().getName());
//		System.out.println("runner : "+ins.getTest1());
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		throw new RuntimeException("runner");
		
	}

	public TestInstance getIns() {
		return ins;
	}

	public void setIns(TestInstance ins) {
		this.ins = ins;
	}

}

class TestInstance {
	String	test1;
	String	test2;

	public String getTest1() {
		return test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	public String getTest2() {
		return test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}

}

// class WorkThread implements Callable<String>{
//
// public WorkThread() {
//
// }
//
// public String call() {
// System.out.println(WorkThread.class.getName());
// // try {
// // Thread.sleep(1000);
// // //System.out.println(WorkThread.class.getName());
// // } catch (InterruptedException e) {
// //// // TODO Auto-generated catch block
// //// e.printStackTrace();
// // }
//
// // for(long i=0; i<1000000000; i++) {
// //
// // }
// //
// // for(long i=0; i<1000000000; i++) {
// //
// // }
// //
// // for(long i=0; i<1000000000; i++) {
// //
// // }
// //
// // for(long i=0; i<1000000000; i++) {
// //
// // }
// //
// // for(long i=0; i<1000000000; i++) {
// //
// // }
// //
// // for(long i=0; i<1000000000; i++) {
// //
// // }
// //
// // for(long i=0; i<1000000000; i++) {
// //
// // }
// //
// // for(long i=0; i<1000000000; i++) {
// //
// // }
//
//
// return null;
// }
//
// }

