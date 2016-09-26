package com.seongmin.test.connectionpool;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * A poolable object factory that tracks how {@link MethodCall methods are called}.
 *
 * @author Sandy McArthur
 * @version $Revision: 606064 $ $Date: 2007-12-20 19:12:02 -0500 (Thu, 20 Dec 2007) $
 * @see MethodCall
 */
public class MethodCallPoolableObjectFactory implements PoolableObjectFactory {
    private final List methodCalls = new ArrayList();
    private int count = 0;
    private boolean valid = true;
    private boolean makeObjectFail;
    private boolean activateObjectFail;
    private boolean validateObjectFail;
    private boolean passivateObjectFail;
    private boolean destroyObjectFail;

    public void reset() {
        count = 0;
        getMethodCalls().clear();
        setMakeObjectFail(false);
        setActivateObjectFail(false);
        setValid(true);
        setValidateObjectFail(false);
        setPassivateObjectFail(false);
        setDestroyObjectFail(false);
    }

    public List getMethodCalls() {
        return methodCalls;
    }

    public int getCurrentCount() {
        return count;
    }

    public void setCurrentCount(final int count) {
        this.count = count;
    }

    public boolean isMakeObjectFail() {
        return makeObjectFail;
    }

    public void setMakeObjectFail(final boolean makeObjectFail) {
        this.makeObjectFail = makeObjectFail;
    }

    public boolean isDestroyObjectFail() {
        return destroyObjectFail;
    }

    public void setDestroyObjectFail(final boolean destroyObjectFail) {
        this.destroyObjectFail = destroyObjectFail;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(final boolean valid) {
        this.valid = valid;
    }

    public boolean isValidateObjectFail() {
        return validateObjectFail;
    }

    public void setValidateObjectFail(final boolean validateObjectFail) {
        this.validateObjectFail = validateObjectFail;
    }

    public boolean isActivateObjectFail() {
        return activateObjectFail;
    }

    public void setActivateObjectFail(final boolean activateObjectFail) {
        this.activateObjectFail = activateObjectFail;
    }

    public boolean isPassivateObjectFail() {
        return passivateObjectFail;
    }

    public void setPassivateObjectFail(final boolean passivateObjectFail) {
        this.passivateObjectFail = passivateObjectFail;
    }

    public Object makeObject() throws Exception {
        final MethodCall call = new MethodCall("makeObject");
        methodCalls.add(call);
        int count = this.count++;
        if (makeObjectFail) {
            throw new PrivateException("makeObject");
        }
        final Integer obj = new Integer(count);
        call.setReturned(obj);
        return obj;
    }

    public void activateObject(final Object obj) throws Exception {
        methodCalls.add(new MethodCall("activateObject", obj));
        if (activateObjectFail) {
            throw new PrivateException("activateObject");
        }
    }

    public boolean validateObject(final Object obj) {
        final MethodCall call = new MethodCall("validateObject", obj);
        methodCalls.add(call);
        if (validateObjectFail) {
            throw new PrivateException("validateObject");
        }
        final boolean r = valid;
        call.returned(new Boolean(r));
        return r;
    }

    public void passivateObject(final Object obj) throws Exception {
        methodCalls.add(new MethodCall("passivateObject", obj));
        if (passivateObjectFail) {
            throw new PrivateException("passivateObject");
        }
    }

    public void destroyObject(final Object obj) throws Exception {
        methodCalls.add(new MethodCall("destroyObject", obj));
        if (destroyObjectFail) {
            throw new PrivateException("destroyObject");
        }
    }
}
