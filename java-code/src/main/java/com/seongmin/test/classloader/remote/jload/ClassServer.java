/*
 * @(#)ClassServer.java - Server for NetworkClassLoader.
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 */

package com.seongmin.test.classloader.remote.jload;

import java.io.*;
import java.net.*;
import java.util.Hashtable;

/**
 * <br>
 * This class together with WorkerClassLoader is the server part of NetworkClassLoader. <br>
 * <br>
 * This class, which executes in a Thread uses java.net.ServerSocket. <br>
 * It listens on a port (default 5050) for incoming connections.<br>
 * For every connection a new thread (a WorkerClassLoader) is generated
 * with the following parameters:<br>
 * clientSocket - socket for the brand new connection.<br>
 * classCache - a cache, which is global in the server of classes which have already been loaded.<br>
 * <br>
 * @see		java.lang.Thread
 * @version 	1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class ClassServer extends Thread {
    
    /**
     * port for listening for incoming connections.
     * default 5050.
     */
    private int port = 5050;
    
    /**
     * serverSocket for incoming connections
     */
    private ServerSocket serverSocket = null;
        
    /**
     * Address for the machine where the ClassServer is executing.
     */
    private InetAddress inetAddress = null;

    /**
     * classCache a cache for classes already loaded in the server
     */
    private Hashtable classCache = null;
    
    
    /**
     * Costruttore.
     *
     * @param    port    port for serverSocket, if port = 0
     * default (5050) is considered.
     * @exception	 UnknownHostException No name is associated with this machine.
     * @exception   IOException		I/O error.
     */
    public ClassServer(int port) throws UnknownHostException,IOException {
        super("ClassServer");
        if ((port > 0x0) && (port <= 0xFFFF))
            this.port = port;

        try {
            inetAddress = InetAddress.getLocalHost();
            System.out.println("ClassServer starts on  " + inetAddress + ":"+ this.port + " ...");
            serverSocket = new ServerSocket(this.port);
        } catch (UnknownHostException uhe) {
            throw uhe;
        } catch (IOException ioe) {
            throw ioe;
        }	//end try catch()
        classCache = new Hashtable();
    }	//end ClassServer().
    
    
    /**
     * An infinite loop for listening for incoming connections.<br>
     * For every new accepted connection a new thread is created for communicating
     * with the client.<br>
     */
    
    public void run() {
        System.out.println("ClassServer listening on port " + port + " ... ");

	while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("\nConnection from : " + clientSocket.getInetAddress());
                WorkerClassServer wcs  = new WorkerClassServer(clientSocket, classCache);
                wcs.start();
            } catch (IOException ioe) {
                System.out.println("Connection failed " + ioe.getMessage());
                continue;
            } catch(Throwable t){
                continue;
            }	//end try-catch()
	}	//end while()
    }	//end run()
    
    /**
     * Empty cache.
     */
    protected void finalize(){
      	classCache.clear();
        classCache = null;
        try {
            super.finalize();
        } catch (Throwable t) {}
    }	//end finalize()
    
} // end class ClassServer