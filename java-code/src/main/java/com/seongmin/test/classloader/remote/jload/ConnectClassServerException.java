/*
 * @(#) ConnectClassServerException.java 
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 */

package com.seongmin.test.classloader.remote.jload;

/**
 * When there are connection problem to the server. <br>
 *
 * @version		1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class ConnectClassServerException extends ClassNotFoundException {

    /**
     * @param s string describing the exception
     */
    public ConnectClassServerException(String s) {
        super(s);
    }
}	//end class.
