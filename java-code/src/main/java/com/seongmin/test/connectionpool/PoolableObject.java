package com.seongmin.test.connectionpool;

import org.apache.commons.pool.ObjectPool;

public interface PoolableObject {
	
	public void setObjectPool(ObjectPool pool);
	
	public ObjectPool getObjectPool();
	
	public void setActive(boolean isactive);
	
	public boolean isRemovedFromPool();
	
	public boolean isActive();
}

 
 
