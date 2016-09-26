/*
 * @(#) ClassPacket.java - A structure to communicate the byte array for
 * a class.
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 */

package com.seongmin.test.classloader.remote.jload;

/**
 * It is used to sent the class byte array to the requester.<br>
 * <br>
 * It could also store some further information.<br>
 *
 * @version		1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class ClassPacket extends ResourcePacket
{
    /**
       Costructor for errors

       @param error the error that was found on the server
     */
    public ClassPacket(String error)
    {
        super (error);
    }

    /**
       Costructor for the byte array

       @param bytes the byte array for the requested class
     */
    public ClassPacket(byte[] bytes)
    {
        super (bytes);
    }

    /**
       return the byte array
     */
    public byte[] getClassBytes() { return getResourceBytes (); }
}
