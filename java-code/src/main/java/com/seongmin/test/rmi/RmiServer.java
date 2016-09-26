package com.seongmin.test.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {


	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public String sayHello() {
		return "Hello, world!";
	}

	public static void main(String args[]) {

		// http://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html
		
		// start rmiregistry or start rmiregistry 2001 (port)
		
		// bin directory 에서 한당..
		// start java -classpath . -Djava.rmi.server.codebase=file:. rmi.RmiServer
		// java -classpath . rmi.RmiClient
		
		try {
			RmiHelloImpl stub = new RmiHelloImpl();
//			RmiHello stub = (RmiHello) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(RmiConstant.RMI_PORT);
			registry.bind(RmiConstant.RMI_ID, stub);

			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
