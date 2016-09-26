/*
 * @(#) JavaPackageException.java 
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 *
 */

package com.seongmin.test.classloader.remote.jload;

/**
 * The exception is generated when we try to load a class of package java.*
 * from a source that is different from the local file system.<br>
 *
 * The JVM provides java.* classes with particular privilidges.<br>
 * This is why it is forbidden such a loading operation.<br>
 *
 * @version		1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class JavaPackageException extends ClassNotFoundException {

    /**
     * @param s string describing the exception
     */
    
    public JavaPackageException(String s) {
        super(s);
    }

}	//end class.
