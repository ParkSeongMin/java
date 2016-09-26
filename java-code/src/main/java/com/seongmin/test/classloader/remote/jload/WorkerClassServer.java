/*
 * @(#) WorkerClassServer.java - class which handles communication
 * with NetworkClassLoader.
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 */

package com.seongmin.test.classloader.remote.jload;

import java.io.*;
import java.net.*;
import java.util.Hashtable;

/**
 * class which handles communication with NetworkClassLoader.<br>
 * <br>
 * It waits for a class request (the name is a String).<br>
 * The requested class is searched first in the cache (a byte array cache), and then
 * in CLASSPATH of the server machine. <br>
 * <br>
 *	If the class is found it is loaded as a byte array.<br>
 * <br>
 * To the client - NetworkClassLoader - a ClassPacket is sent with the byte array.<br>
 * <br>
 * If the class is not found a ClassPacket with an error. <br>
 *
 * @version		1.2 August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 */

public class WorkerClassServer extends Thread  {

    private Socket clientSocket = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private Hashtable classesCache = null;

    /**
     * Constructor. Initializes the I/O streams associated to the socket of the client.
     * @param       clientSocket     socket for the connection with the client.
     * @param 	    classCahce	     the cache of classes.	
     * @exception   IOException      I/O stream error.
     */
    public WorkerClassServer(Socket clientSocket, Hashtable classesCache)  throws IOException {
        super();
        this.clientSocket = clientSocket;
        this.classesCache = classesCache;
        
        try {
            os = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            os.flush();
            is = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            System.out.println("Connection established");
        } catch (IOException ioe) {
            this.clientSocket.close();
        }
    }	//end constructor

    /**
     * A loop waiting for a class name.
     * It loads the class in a byte array which is sent to the client.<br>
     * -1 is sent if the class is not found.
     */
    public void run() {
        byte[] resourceBytes;
        String resourceName;
        String resourceType;

        // which file separator is this system using?
        String fileSeparator = System.getProperty( "file.separator" ) ;

        // to read the request from the client
        ResourceRequest request;

        try {
            while (true) {
                
                //reads the name of the resource
                request = (ResourceRequest) is.readObject();

                resourceName = request.getResourceName();
                resourceType = request.getResourceType();

                if (resourceType.equals("CLASS")) {
                    System.out.print
                        ("Request for class: " + resourceName + "... ");
                    resourceName = resourceName.replace
                        ('.', fileSeparator.charAt(0)) + ".class";
                    resourceBytes = getClassBytes(resourceName);
                } else if (resourceType.equals("BINARY")) {
                    System.out.print
                        ("Request for resource: " + resourceName + "... ");
                    resourceBytes = getResourceBytes(resourceName);
                } else {
                    // unknown request type!
                    System.out.println
                        ("Unknow request " + resourceName + " of type " +
                         resourceType);
                    sendPacket("Unknown request type: " + resourceType);
                    continue;
                }
                    
                // first we try with the cache
            	// classBytes = (byte[])classesCache.get(className);

            	// otherwise load it from the local file system
            	if (resourceBytes == null) {
                    System.out.println
                        ("\nResource " + resourceName + " not found!!");
                    sendPacket("\nResource " + resourceName + " not found!!");
                    continue;
                } // end if (resourceBytes == null)

		// if it's OK first send the packet
                sendPacket(resourceBytes);
                System.out.println("sent");
            }	//end while(true);

            // if there's a problem we close the connection

        } catch(Throwable t) {
            return;
        } finally {
            System.out.println("Client disconnected!");
            try {
                is.close();
                os.close();
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } //end finally	(end try-finally)
    }	//end run()

    /**
     * get the bytes for the requested resource
     * @param resourceName the name of the resource
     * @return the byte array for the resource or an exception is thrown
     * @exception   IOException file read error.
     */
    protected byte[] getResourceBytes(String resourceName) throws IOException
    {
        return loadResource
        (getClass().getClassLoader().getResourceAsStream(resourceName));
    }

    /**
     * get the bytes for the requested class
     * @param className the name of the class
     * @return the byte array for the class or an exception is thrown
     * @exception   IOException file read error.
     */
    protected byte[] getClassBytes(String className) throws IOException
    {
        return loadResource(ClassLoader.getSystemResourceAsStream(className));
    }

    /**
     * read a file from a stream and returns a byte array.
     * @exception   FileNotFoundException File .class not found in CLASSPATH.
     * @param is the InputStream to read from
     * @return 	byte[]	byte array: contents of the file
     */
    protected byte[] loadResource(InputStream is)
        throws IOException
    {
        if (is == null)
            return null;

        int size = 0;
        byte[] resourceBytes;

        try {
            // read dimension
            size = is.available();
            resourceBytes = new byte[size];
		
            // read the file into the array
            is.read(resourceBytes);
            is.close();
        } catch(IOException ioe){
            throw ioe;
        }	//end try catch()
        
        return resourceBytes;
    }	//end loadResource()

    /**
     * send an error packet to the client
     * @exception   IOException file read error.
     */
    protected void sendPacket(String error) throws IOException
    {
        writePacket(new ResourcePacket(error));
    }

    /**
     * send a byte array packet to the client
     * @exception   IOException file read error.
     */
    protected void sendPacket(byte [] bytes) throws IOException
    {
        writePacket(new ResourcePacket(bytes));
    }

    /**
     * write a packet to the socket
     * @exception   IOException file read error.
     */
    protected void writePacket(ResourcePacket packet) throws IOException
    {
        os.reset();
        os.writeObject(packet);
        os.flush();
    }
}	//end class WorkerClassServer.
