package com.seongmin.test.connectionpool;

/**
 * An exception that only is thrown by these tests.
 */
public class PrivateException extends RuntimeException {
    public PrivateException(final String message) {
        super(message);
    }
}


