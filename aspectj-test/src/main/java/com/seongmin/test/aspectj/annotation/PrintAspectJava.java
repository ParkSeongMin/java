package com.seongmin.test.aspectj.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class PrintAspectJava {

	private JoinPoint	joinPoint;

	@Before(value = "(call(* println(..)) && (within(Test) && within(Runnable+)))", argNames = "")
	public void beforeAspect() {
		System.out.println("before ... ");
//		throw new RuntimeException("ddd");
	}
	
	@Before(value = "(execution(* println()) && (within(Test) && within(Runnable+)))", argNames = "")
	public void beforeAspect2() {
		System.out.println("before2 ... ");
//		throw new RuntimeException("ddd");
	}
	
	@Around(value = "(call(* println(String)) && (within(Test) && within(Runnable+)))", argNames = "")
	public void aroundAspect(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs(); 
		System.out.println("around ... " + args[0]);
	}
	

	@After(value = "(call(* println(..)) && (within(Test) && within(Runnable+)))", argNames = "")
	public void afterAspect() {
		System.out.println("after... ");
//		throw new RuntimeException("ddd");
	}

	// before() : call(* t(..)) && within(Test) && within(Runnable+) {
	// System.out.println("before ... ");
	// throw new Exception("ddd");
	// }
}

class Test {
	public static void main(String[] args) {
		System.out.println("not this one");
		

		Runnable runner = new Runnable() {
			private int count;
			public void run() {
				boolean runnable = true;
				while(runnable) {
					if(count++ > 10) {
						break;
					}
					System.out.println("but this one");
	//				throw new RuntimeException("ddd");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			public Object test() {
				return null;
			}
		};

		runner.run();
	}
}
