package com.seongmin.test.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/*
 * http://kin.naver.com/qna/detail.nhn?d1id=1&dirId=1040201&docId=72066377&qb=RXhlY3V0b3JTZXJ2aWNlIHN1Ym1pdA==&enc=utf8&section=kin&rank=1&search_sort=0&spq=0&pid=g2r%2Bvc5Y7vlssZtNKYossc--387819&sid=T2r@DDTrak8AAEBcDz8
 */
class  ThreadPoolTask implements Callable<String> {

	String number = null;
	int tmp = 0;
	
	public ThreadPoolTask(int i) {
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
		//System.out.println("Thread name : " +  Thread.currentThread().getName() );

		
		/*
		 * ������ sleep�� �ֱ�����
		 */
		if("pool-1-thread-3".equals(Thread.currentThread().getName()) ) {
			try {
				System.out.println("sleep" + number);
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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

public class AboutNewFixedThreadPool {
	public static void main(String[] argv) {
		
		int threadCount = 0;

		ExecutorService pool = Executors.newFixedThreadPool(8);

		List<Future<String>> futureList = new ArrayList<Future<String>>();
		
		/*
		 * ������ for ���� ����.
		 * ���� �� ��� out of Memory �� �߻��Ѵ�.
		 */
		for(int i = 0; i < 99999; i++) {
			ThreadPoolTask threadPoolTask = new ThreadPoolTask(i);
			//pool.submit(threadPoolTask);
			futureList.add( pool.submit(threadPoolTask) );
			//System.out.println("i : " + i);

		}
		
		for(Future<String> future : futureList){   
			try {
				/*
				 * get()
				 */
				//String aaaa = future.get();
				
				
				/*
				 * get(long timeout, TimeUnit unit)
				 */
				String aaaa;
				try {
					aaaa = future.get(1L, TimeUnit.SECONDS);
					System.out.println("aaaa : " + aaaa);
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("--- TimeoutException ---");
					
					/*
					 * ������ �������� ����
					 */
					System.out.println(future.isDone());
					/*
					 * �������� ������ ��ҵǾ���� ����
					 */
					System.out.println(future.isCancelled());
					
					/*
					 * InterruptedException ǥ��
					 */
					//future.cancel(true);
					/*
					 * InterruptedException ǥ�� ���� ����
					 */
					future.cancel(false);


					System.out.println(future.isDone());
					System.out.println(future.isCancelled());
					
					
				}
			} catch (InterruptedException e) {

			} catch (ExecutionException e) {

			}
		}

		if( !(pool.isShutdown()) ) {
			pool.shutdown();
		}
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}

		return;
	}
}
