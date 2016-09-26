package jni;

public class HelloWorld {

	static {
		// os 별 파일 확장자가 붙는다. dependancy 가 걸린게 함께 로딩 된다.
		System.loadLibrary("lib/jni/HelloWorld");
		
		// absolute path 가 와야 한다. 하나씩 로딩 된다. 로딩의 순서가 존재 한다.
		// System.load("");
		
	}
	
	
	private native void print();
	
	private native String hello(String name);
	
	private native void raiseException();
	
	public static void main(String[] args) {
		
		HelloWorld greet = new HelloWorld();
		
		greet.print();
		
		greet.hello("test");
		
		try {
			greet.raiseException();
		} catch(JniException e) {
			System.out.println("exception occured.." + e.getMessage());
		}
		
		
		
		
	}
	
}
