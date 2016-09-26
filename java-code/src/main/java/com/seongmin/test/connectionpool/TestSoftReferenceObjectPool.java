package com.seongmin.test.connectionpool;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.SoftReferenceObjectPool;

/**
 * @author Rodney Waldhoff
 * @author Sandy McArthur
 * @version $Revision: 774099 $ $Date: 2009-05-12 17:29:02 -0400 (Tue, 12 May 2009) $
 */
public class TestSoftReferenceObjectPool extends TestBaseObjectPool {
    public TestSoftReferenceObjectPool(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestSoftReferenceObjectPool.class);
    }

    protected ObjectPool makeEmptyPool(int cap) {
        return new SoftReferenceObjectPool(
            new PoolableObjectFactory()  {
                int counter = 0;
                public Object makeObject() { return String.valueOf(counter++); }
                public void destroyObject(Object obj) { }
                public boolean validateObject(Object obj) { return true; }
                public void activateObject(Object obj) { }
                public void passivateObject(Object obj) { }
            }
            );
    }

    protected ObjectPool makeEmptyPool(final PoolableObjectFactory factory) {
        return new SoftReferenceObjectPool(factory);
    }

    protected Object getNthObject(int n) {
        return String.valueOf(n);
    }

    protected boolean isLifo() {
        return false;
    }

    protected boolean isFifo() {
        return false;
    }

}
