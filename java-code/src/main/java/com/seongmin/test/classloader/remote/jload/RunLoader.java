/*
 * @(#) RunLoader.java - Main for NetworkClassLoader.
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 *
 */

package com.seongmin.test.classloader.remote.jload;

/**
 * The class which executes the NetworkClassLoader.<br>
 * <br>
 * syntax: java RunLoader hostname port nameclass (no .class)<br>
 * Example: java RunLoader gemini 5050 Main  <br>
 * <br>
 * Note: newInstance() method uses default constructor of the class.<br>
 *
 * @version		1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class RunLoader {
    public static void main (String args[]) {
        String hostName = null;
        String nameClass = null ;
        int port = 5050;
        
        if (args.length == 3 ) {
            hostName = args[0];
            port = Integer.parseInt (args[1]);
            nameClass = args[2];            
        } else {
            System.out.println("Syntax: java RunLoader <hostname> <port> <nameclass>");
            System.exit(0);
        }
        
        try {
            ClassLoader loader = new NetworkClassLoader(hostName, port);
            Class c = loader.loadClass(nameClass);
            Object main = c.newInstance();
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }	//end main()
}	//end class RunLoader.
