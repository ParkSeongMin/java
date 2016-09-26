/*
 * @(#) ResourceRequest.java - A structure to request a resource.
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 */

package com.seongmin.test.classloader.remote.jload;

/**
 * It is used to sent the request for a resource to the server.<br>
 * <br>
 * It could also store some further information.<br>
 *
 * @version		1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class ResourceRequest implements java.io.Serializable
{
    String resource_name; // the complete resource name (incl. path)
    String resource_type; // what kind of resource? (class, image...)

    /**
       Costructor

       @param name the complete name of the resource
       @param type the type of the resource (CLASS, BINARY)
     */
    public ResourceRequest(String name, String type)
    {
        resource_name = name;
        resource_type = type;
    }

    /**
       return the name of the resource
     */
    public String getResourceName() { return resource_name; }

    /**
       return the type of the resource
     */
    public String getResourceType() { return resource_type; }
}
