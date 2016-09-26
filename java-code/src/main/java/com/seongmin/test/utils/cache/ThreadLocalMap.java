package com.seongmin.test.utils.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * HashMap과 똑같지만 thread safe하다.
 * static으로 선언되어도 thread별로 저장소를 따로 갖고 있는 맵으로
 * thread safe를 위해 사용한다.
 * 주로 static으로 선언된 유틸성 메소드들간에 값을 저장하기 위해서 사용된다.
 * 
 * 주의할 것은 요 클래스의 객체를 생성하는 순간, Map 객체 하나가 ThreadContext에 저장된다.
 * 계속 객체를 생성하면 계속 ThreadContext에 쌓일 것이고, 결국 메모리 릭이 된다.
 * 따라서 반드시 다음과 같은 방법으로 사용하여야 한다.
 * 		ThreadLocalMap.setEnabledForCurrentThread(true); // 사용을 선언
 * 		ThreadLocalMap map1 = new ThreadLocalMap();
 * 		ThreadLocalMap map2 = new ThreadLocalMap();
 * 		ThreadLocalMap.setEnabledForCurrentThread(false); // 비사용을 선언. ContextThread에 쌓인것을 제거한다.
 *  
 * setEnabledForCurrentThread(false)를 호출하면 ContextThread에 담긴 모든 ThreadLocalMap의 것을 삭제한다. 물론 같은 thread의 것만.
 *  
 * @author dhrim
 *
 * @param <K>
 * @param <V>
 */
public class ThreadLocalMap<K, V> implements Map<K, V> {

	private static final String IS_ENABLED_QUERY_KEY = ThreadLocalMap.class.getName()+".isEnabled";
	private static final String INSTANCE_SET_QUERY_KEY = ThreadLocalMap.class.getName()+".instanceSet";
	public ThreadLocalMap() {
	}
	
	private boolean isEnabled() {
		Object isEnabledObject = ThreadContext.get(IS_ENABLED_QUERY_KEY);
		if(isEnabledObject==null) {
			return false;
		}
		return (Boolean)isEnabledObject;
	}
	
	/**
	 * ThreadLocalMap을 사용하겠다고 선언한다.
	 * 선언하지 않으면 ThreadLocalMap은 값을 저장하지 않는다.
	 * !!! 주의 : 선언을 했으면 반!드!시! 해제하여야 한다.
	 * 사용법을 잘 모르면 사용하지 말라.
	 * @param isEnabled
	 */
	public static void setEnabledForCurrentThread(boolean isEnabled) {
		ThreadContext.put(IS_ENABLED_QUERY_KEY, new Boolean(isEnabled));
		// disabled 됐다. ThreadContext에 담긴것을 삭제하자.
		if(!isEnabled) {
			Set<String> instanceSet = (Set<String>)ThreadContext.get(INSTANCE_SET_QUERY_KEY);
			if(instanceSet==null) { return; }
			for(String instanceId : instanceSet) {
				ThreadContext.removeByKey(instanceId);
			}
		}
	}
	
	private Map<K, V> getMap() {
		Map<K, V> map = (Map<K, V>) ThreadContext.get(this.toString());
		if(map==null) {
			map = new HashMap<K, V>();
			addToInstanceMap(map);
		}
		ThreadContext.put(this.toString(), map);
		return map;
	}
	
	// ThreadContext에 무한정 쌓이지 않기 위해 생성한 인스턴스의 키 값을 가지고 있는다.
	private void addToInstanceMap(Map<K, V> map) {
		Set<String> instanceSet = (Set<String>)ThreadContext.get(INSTANCE_SET_QUERY_KEY);
		if(instanceSet==null) {
			instanceSet = new HashSet<String>(); 
			ThreadContext.put(INSTANCE_SET_QUERY_KEY, instanceSet);
		}
		instanceSet.add(this.toString());
	}
	
	
// 요 아래는  java.lang.Map의 delegation 메소드들
	
	public void clear() {
		Map<K, V> map = getMap();
		map.clear();
	}

	public boolean containsKey(Object key) {
		Map<K, V> map = getMap();
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		Map<K, V> map = getMap();
		return map.containsValue(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Map<K, V> map = getMap();
		return map.entrySet();
	}

	public V get(Object key) {
		Map<K, V> map = getMap();
		Object object = map.get(key);
		return map.get(key);
	}

	public boolean isEmpty() {
		Map<K, V> map = getMap();
		return map.isEmpty();
	}

	public Set<K> keySet() {
		Map<K, V> map = getMap();
		return map.keySet();
	}

	public V put(K key, V value) {
		if(!isEnabled()) { return value; }
		Map<K, V> map = getMap();
		(new Object()).toString();
		map.put(key, value);
		return map.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		if(!isEnabled()) { return;}
		Map<K, V> map = getMap();
		map.putAll(t);
	}

	public V remove(Object key) {
		Map<K, V> map = getMap();
		return map.remove(key);
	}

	public int size() {
		Map<K, V> map = getMap();
		return map.size();
	}

	public Collection<V> values() {
		Map<K, V> map = getMap();
		return map.values();
	}
	
}
