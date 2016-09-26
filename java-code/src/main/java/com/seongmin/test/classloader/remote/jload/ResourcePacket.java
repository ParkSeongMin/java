/*
 * @(#) ResourcePacket.java - A structure to communicate the byte array for
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

public class ResourcePacket implements java.io.Serializable
{
    String error; // if an error was found by the ClassServer
    byte[] resourceBytes;

    /**
       Costructor for errors

       @param error the error that was found on the server
     */
    public ResourcePacket(String error)
    {
        this.error = error;
    }

    /**
       Costructor for the byte array

       @param bytes the byte array for the requested class
     */
    public ResourcePacket(byte[] bytes)
    {
        resourceBytes = bytes;
    }

    /**
       if no error happened
     */
    public boolean isOK() { return error == null; }

    /**
       return the string for the error
     */
    public String getError() { return error; }

    /**
       return the byte array
     */
    public byte[] getResourceBytes() { return resourceBytes; }
}
