/*
 * @(#) RunServer.java - Main for ClassServer
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 *
 */

package com.seongmin.test.classloader.remote.jload;

/**
 * <br>Class which executes ClassServer.<br>
 * Syntax: java RunServer port<br>
 * default port is 5050. <br>
 * Note: Program generates an execption if no name is associated with this machine.<br>
 *
 * @version		1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class RunServer {
    public static void main (String[] args) {
        int port = 5050;        

        try {
            if ( args.length > 0 )
                port = Integer.parseInt( args[0] );

            ClassServer cs = new ClassServer(port);
            cs.start();
        } catch(Throwable t){
            t.printStackTrace();
        }
    }	//end main()
}	//end class RunServer.
