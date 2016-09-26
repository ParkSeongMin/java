/*
 * @(#) NetworkClassLoader.java  - load a class from the local file system
 * or from the network.
 * This file is licensed to you under the license specified in the 
 * included file `LICENSE.txt'. Look there for further details.
 */

package com.seongmin.test.classloader.remote.jload;

import java.io.*;
import java.net.*;
import java.util.Hashtable;

/**
 * <br><b>Load a class from the local file system or from the network.</b><br>
 * When a class is loaded with this loader, this loader becomes the default loader. <br>
 * So all the other classes that are associated with this classe (super classes, or used classes)
 * will be loaded with this loader. <br>
 * <br><br>
 * First a class is searched in the local cache
 * (implemented in java.lang.ClassLoader), the in the local file system,
 * and if it's still not found, it is searched in the net.<br>
 * In the cache, only classes from the network are added.<br>
 * If all classes are available locally, no connection is established with the server.<br>
 * <br>
 * Example:<br>
 * <b>
 * ClassLoader loader = new NetworkClassLoader(host, port);<br>
 * Object main = loader.loadClass("myClass").newInstance();<br>
 * </b>
 * @see           java.lang.ClassLoader
 * @version       1.2 - August 2000
 * @author 		Lorenzo Bettini - <a href="mailto:bettini@dsi.unifi.it">bettini@dsi.unifi.it</a>
 * @author 		Donato Cappetta - <a href="mailto:cappetta@infomedia.it">cappetta@infomedia.it</a>
 * @since         JDK1.2
 */

public class NetworkClassLoader  extends ClassLoader {
    private String hostName = null;
    private int serverPort;
    private Socket socket = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private boolean connected = false;
    private int tab = -1; // just to print with indentation
    private Hashtable resourceTable = new Hashtable(); // key name, value File

    /**
     * default constructor<br>
     */
    public NetworkClassLoader() {
        this("localhost", 5050);
    }

    /**
     * Costructor.<br>
     *
     * @param  hostName host to load the classes from
     * @param  serverPort  port of the server
     */
    public NetworkClassLoader(String hostName, int serverPort) {
        super();
        this.hostName = hostName;
        this.serverPort = serverPort;
    }

    /**
     * Required method.
     * It is invoked directly from the parent class loadClass(String name) in
     * java.lang.ClassLoader, if the class was not found int the cache
     * and it couldn't be loaded by the system loader. <br>
     * In this implementation the class is requested to the remote
     * ClassServer. <br>
     *
     * @param     name    class name
     *
     * @exception ConnectClassServerException  Error connecting to the ClassServer.
     * @exception ClassNotFoundException  
     * @exception ClassFormatError    in converting from byte array to a Class.
     * @return Class - the requested class.
     */
    protected Class findClass(String className)
        throws   ConnectClassServerException, JavaPackageException,
                 ClassNotFoundException, ClassFormatError {
        byte[] classBytes = null;
        Class classClass = null;

        // try with the network server
        
        try {
            // connect to the ClassServer
            if (!connected)
                connect();
            
            classBytes = loadClassFromServer(className);
        } catch (IOException ioe){
            disconnect();
            throw new ConnectClassServerException(ioe.toString());
        }
        
        // convert the byte array into a Class and put it in the cache.
        classClass = defineClass(className, classBytes, 0, classBytes.length);
        if (classClass == null)
            throw new ClassFormatError(className);

        Print(className + " loaded from the SERVER");
        
        return classClass;
    }        //end loadClass()

    protected URL findResource(String name)
    {
      URL resourceURL;

      try {
          File localResourceFile = (File) resourceTable.get(name);

          // we have to download it
          if (localResourceFile == null) {
              Print("findResource: " + name + " at the SERVER");
      
              byte[] resourceBytes = loadResourceFromServer(name, "BINARY");

              if (resourceBytes == null) {
                  Print("Resource " + name + " not found on server!");
                  return null;
              }

              localResourceFile = createLocalResourceFile(name, resourceBytes);
              resourceTable.put(name, localResourceFile);

              Print("stored locally: " + localResourceFile);
          }

          return getLocalResourceURL(localResourceFile);
      } catch (Exception e) {
          Print("Exception " + e);
      }

      return super.findResource (name);
    }

    /**
     * open the local file for the resource and return the URL.
     *
     * @param file the local File for the resource
     * @return the URL for this file
     * @exception MalformedURLException        see java.net.MalformedURLException
    */
    protected URL getLocalResourceURL(File file) throws MalformedURLException
    {
        return file.toURL();
    }

    /**
     * create the local file for the resource and return the URL.
     * The file will be deleted when the virtual machine terminates.
     *
     * @param name the name of the resource
     * @param bytes the binary contents of this resource
     * @return the new File object
     * @exception MalformedURLException see java.net.MalformedURLException
     * @exception FileNotFoundException see java.io.FileNotFoundException
     * @exception IOException see java.io.IOException
    */
    protected File createLocalResourceFile(String name, byte[] bytes) 
        throws MalformedURLException, FileNotFoundException, IOException
    {
        File resFile = File.createTempFile
            ("__temp_res_", "_" + createLocalResourceName(name));
        resFile.deleteOnExit();

        FileOutputStream fostream = new FileOutputStream(resFile);
        fostream.write(bytes, 0, bytes.length);
        fostream.close();

        return resFile;
    }

    /**
     * it creates a name for the local file that will store the
     * resource contents.
     *
     * @param name the resource name
     * @return a name for the local file for this resource
     */
    protected String createLocalResourceName(String name)
    {
        return name.replace('/', '_');
    }

    /**
       Not required.
       It is here just to print on the screen when a class is being
       loaded.
     */
    public synchronized Class loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        ++tab;
        Print("Loading class " + name);
        Class result =  super.loadClass(name, resolve);
        --tab;
        return result;
    }

    /**
       Print on the screen with indentation to show the nesting of
       the calls.
     */
    protected void Print(String s)
    {
        for (int i = 0; i < tab; ++i )
            System.out.print(" ");

        System.out.println(s);
    }

    /**
     * connect to the ClassServer
     *
     * @exception UnknownHostException         see java.net.UnknownHostException.
     * @exception IOException                see  java.io.IOException.
     *
     */
    protected void connect() throws UnknownHostException, IOException {
        System.out.println("Connecting to the ClassServer " + hostName + ":" + serverPort);

        socket = new Socket(hostName, serverPort);
        connected = true;
        os = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        os.flush();
        is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

        System.out.println("Connected");
    }        //end connect()

    /**
     * Close the connection to the ClassServer
     */
    protected void disconnect(){
        try {
            connected = false;
            os.close(); os = null;
            is.close(); is = null;
            socket.close(); socket = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }        //end disconnect()

    /**
     * Read the class from the network and return a byte array.
     *
     * @param     className    class name
     * @exception ClassNotFoundException         class not found.
     * @exception SocketException                see java.net.SocketException.
     * @exception IOException                        see java.io.IOException.
     * @return                 byte[] byte array for the class.
     */
    protected byte[] loadClassFromServer(String className)
        throws ClassNotFoundException, SocketException, IOException
    {
        byte[] classBytes = loadResourceFromServer(className, "CLASS");

        if (classBytes == null)
            throw new ClassNotFoundException(className);

        return classBytes;
    }   //end loadClassFromServer()

    /**
     * Read the class from the network and return a byte array.
     *
     * @param     resourceName    resource name
     * @param     type            resource type
     * @exception FileNotFoundException         class not found.
     * @exception SocketException                see java.net.SocketException.
     * @exception IOException                        see java.io.IOException.
     * @return                 byte[] byte array for the class.
     */
    protected byte[] loadResourceFromServer(String resourceName, String type)
        throws FileNotFoundException, ClassNotFoundException,
               SocketException, IOException
    {
        byte[] fileBytes = null;
        // load the file data from the connection

        // send the name of the file
        sendRequest(resourceName, type);
        
        // read the packet
        ResourcePacket resourcePacket = (ResourcePacket) is.readObject();
        
        if (! resourcePacket.isOK())
            throw new FileNotFoundException(resourcePacket.getError());
        
        fileBytes = resourcePacket.getResourceBytes();

        return fileBytes;
    }   //end loadResourceFromServer()

    /**
     * send a request to the server
     * @param name the name of the requested resource
     * @param type the type of the requested resource
     * @exception IOException see java.io.IOException.
     */
    protected void sendRequest(String name, String type)
    throws IOException
    {
        os.reset();
        os.writeObject(new ResourceRequest(name, type));
        os.flush();
    }
}  //end class NetworkClassLoader
