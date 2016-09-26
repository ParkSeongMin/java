package com.seongmin.test.utils.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// thread 별로 객체를 저장하기 위한 클래스.
// 예를 들어 SystemApi를 thread별로 저장한다.
// TODO : 더이상 존재하지 않는  thread에 해당하는 값들은 삭제할 수 없다. 메모리 릭이다.
public class ThreadContext {

	private static ThreadLocal<ThreadContext>	threadLocal	= new ThreadLocal<ThreadContext>();

	private ThreadContext() {
	}

	private Map<String, Object>	objectMap	= new HashMap<String, Object>();

	private void store(String objectId, Object object) {
		if (objectId == null) {
			return;
		}
		objectMap.put(objectId, object);
	}

	private Object retrieve(String objectId) {
		if (objectId == null) {
			return null;
		}
		return objectMap.get(objectId);
	}

	private void deleteByObjectId(String objectId) {
		if (objectId == null) {
			return;
		}
		objectMap.remove(objectId);
	}

	private void deleteByObject(Object object) {
		if (object == null) {
			return;
		}
		if (!objectMap.containsValue(object)) {
			return;
		}
		Set<Entry<String, Object>> entrySet = objectMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			if (object == entry.getValue()) {
				objectMap.remove(entry.getKey());
			}
		}
	}

	public static ThreadContext getCurrent() {
		ThreadContext threadContext = threadLocal.get();
		if (threadContext == null) {
			threadContext = new ThreadContext();
			threadLocal.set(threadContext);
		}
		return threadContext;
	}

	public static Object get(String objectId) {
		ThreadContext threadContext = getCurrent();
		return threadContext.retrieve(objectId);
	}

	public static void put(String objectId, Object value) {
		ThreadContext threadContext = getCurrent();
		threadContext.store(objectId, value);
	}

	public static void removeByKey(String objectId) {
		ThreadContext threadContext = getCurrent();
		threadContext.deleteByObjectId(objectId);
	}

	public static void removeByValue(Object object) {
		ThreadContext threadContext = getCurrent();
		threadContext.deleteByObject(object);
	}

}
