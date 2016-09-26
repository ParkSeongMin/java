package com.seongmin.test.bridgemethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class Sink<T> {
	abstract void add(T... elements);

	void addUnlessNulll(T... elements) { // line 10
		for (T element : elements) {
			System.out.println("count");
			if (element != null) {
				add(element); // line 13
			}
		}
	}
}

public class StringSink extends Sink<String> {
	
	private final List<String>	list	= new ArrayList<String>();

	public void add(String... elements) { // line 21
		list.addAll(Arrays.asList(elements));
	}

	public String toString() {
		return list.toString();
	}

	public static void main(String[] args) {
		
		Method[] methods = StringSink.class.getMethods();

		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			
//			method.setAccessible(true);
			
			System.out.println(method.getName());
			System.out.println("\t isBridge()="+ method.isBridge());
			System.out.println("\t isVarArgs()="+ method.isVarArgs());
		}
		
		Sink<String> ss = new StringSink();
		ss.addUnlessNulll("null", null); // line 29
		System.out.println(ss);
	}
}

/*


에러 결과를 보면 아래와 같습니다.

Exception in thread "main" java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
	at googleio2011.StringSink.add(StringSink.java:1)
	at googleio2011.Sink.addUnlessNulll(StringSink.java:13)
	at googleio2011.StringSink.main(StringSink.java:29)

여기서 재미있는 부분은 error stack에서 1번째 줄에서 뭔가 나왔다는 겁니다. 1번째 줄은 패키지 선언입니다. stack trace에 들어갈 이유가 없는 곳이죠!

3가지 포인트가 있는데, 첫째는 Varargs ( T... 으로 표현된 부분) , 둘째는 erasure (generic에서 생기는 거. 자세한 건 요기), 세째는 bridge method 입니다.

line 29에서  Varargs 는 내부적으로 갯수가 변하는 변수로 처리하는 것이 아니라 Array로 처리한답니다. 따라서 line 29에서 String[] 이 생깁니다. 즉 new String[]{"null", null} 이 생성된다고 보시면 됩니다. 
line 10에서 T... 으로 받는 것은 사실은 그냥 Object[] 로 받는 것입니다. erasure 로 처리되는 거죠. 그래서 line 13에서 element는 String이 아니라 사실 Object입니다. 
그런데, Sink<T>에서 정의된 add method를 보면
abstract void add(T... elements) 라고 되어있습니다. 이것을 상속받아 구현한 method는 21번째 줄에서 void add(String ...)으로 되어있습니다. 
line 13에서는 line 29에서 String[]가 생성된 것과 마찬가지로 Object[]가 생성됩니다. 이게 결국 line 21의 method로 전달되어야 하는데 Object[]를 String[]으로 전달할 수는 없습니다. 이런 이유로 코드 상에서는 존재하지 않지만 java가 내부적으로 메쏘드를 생성하는데, 이게 bridge method입니다.

void add(Object[] args){   -- 여기가 실제로 abstract add(T... elements)를 override하는 부분이며,
	add( (String[]) args); -- 여기서 실제로 존재하는 메쏘드인 void add(String ... element) 를 호출하도록 연결 시켜줍니다.
}

이 bridge method 안 쪽에서 Object[]를 String[]으로 바꾸려는 시도를 하게 된 것입니다. 실제로 존재하지 않는 코드이기 때문에 error stack에서는 line1 이라는 이상한 숫자가 찍히거구요. 

실제로 이클립스를 통해서 코드를 만들면 13번째 줄에서 "Type safety : A generic array of T is created for varargs parameter" 라는 warning이 뜹니다. 

이걸 해결하는 좋은 방법은 varargs와 array를 전부 collection으로 뜯어 고치는 것이랩니다.

수정된 코드는 아래와 같습니다.

abstract class Sink2<T>{
	abstract void add(Collection<T> elements);
	void addUnlessNulll(Collection<T>  elements){
		for (T element : elements) {
			if (element != null) {
				add(Collections.singleton(element));
			}
		}
	}
}

public class StringSink2 extends Sink2<String>{
	private final List<String> list = new ArrayList<String>();
	void add(Collection<String>  elements){
		list.addAll(elements);
	}
	public String toString(){
		return list.toString();
	}
	public static void main(String[] args) {
		Sink2<String> ss = new StringSink2();
		ss.addUnlessNulll(Arrays.asList( "null", null));
		System.out.println(ss);
	}
}


교훈 

1. varargs 는 쫌 조심해서 써라.
2. Generics랑 array는 별로 사이가 안 좋다. 따라서 generic이랑 varargs도 사이가 안 좋다. 
3. 긍까 array 같은 거 쓰지말고, collection 으로 가자. 특히 API 만들때는!!!
4. 컴파일러 말씀을 잘 듣자. 컴팔러가 괜히 지랄하는 게 아니다.!! 죽었다 깨어나도 확실하다 싶은 부분 에다가 @SuppressWarnings annotation을 쓰자. 

*/

