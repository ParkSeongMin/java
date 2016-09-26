package com.seongmin.test.connectionpool;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;

public class PoolableObjectFactoryWapper implements PoolableObjectFactory{
	private PoolableObjectFactory factory;
	private ObjectPool pool;
	public PoolableObjectFactoryWapper(ObjectPool pool,PoolableObjectFactory factory){
		this.factory = factory;
		this.pool = pool;
	}
	
	public void activateObject(Object obj) throws Exception {
		PoolableObject object = (PoolableObject)obj;
		object.setActive(true);
		factory.activateObject(obj);
	}
	
	public void destroyObject(Object obj) throws Exception {
		PoolableObject object = (PoolableObject)obj;
		if(object.getObjectPool() != null){
			object.setObjectPool(null);
		}
		factory.destroyObject(obj);
	}
	
	/**
	 * ���ø�factory Wrapper����object ����ʵ��PoolableObject
	 */
	public Object makeObject() throws Exception {
		PoolableObject object = (PoolableObject)factory.makeObject();
		object.setObjectPool(pool);
		return object;
	}
	
	public void passivateObject(Object obj) throws Exception {
		PoolableObject object = (PoolableObject)obj;
		object.setActive(false);
		factory.passivateObject(obj);
	}
	public boolean validateObject(Object obj) {
		return factory.validateObject(obj);
	}
}
