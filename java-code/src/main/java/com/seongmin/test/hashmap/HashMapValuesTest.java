package com.seongmin.test.hashmap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HashMapValuesTest {
	
	public static void main(String[] args) {
		
		// 1.5 랑 1.6 이랑 순서가 다르게 나오넹.. values 할 때 
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("test1", "test1");
		map.put("test2", "test2");
		
		// 1.5 의 경우는 뒤에서 부터 데이터를 찾는다. 1.6의 경우 앞에서 부터 데이터를 찾는다.
		Collection<String> values = map.values();
		for(String value : values) {
			System.out.println(value);
		}
		
		
		int i = 1 << 30;
		
		System.out.println("default hashmap threshold="+i);
		
	}

}
