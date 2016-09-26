package com.seongmin.test.aspectj.anonymous;

public aspect PrintAspect {
//	before(): call(* println(..))&& within(Runnable+)  {
//		System.out.println("before ... ");
//		throw new RuntimeException("ddd");
//	}
//	
//	after(): call(* println(..)) && within(Test) && within(Runnable+)  {
//		System.out.println("after... ");
//		throw new RuntimeException("ddd");
//	}
	
//	before() : call(* t(..)) && within(Test) && within(Runnable+)  {
//		System.out.println("before ... ");
//		throw new Exception("ddd");
//	}
}

class Test {
	public static void main(String[] args) {
		System.out.println("not this one");
		
		Runnable runner = new Runnable() {
			public void run() {
				System.out.println("but this one");
				throw new RuntimeException("ddd");
			}
			
			public Object test() {
				return null;
			}
		};
		
		runner.run();
	}
}