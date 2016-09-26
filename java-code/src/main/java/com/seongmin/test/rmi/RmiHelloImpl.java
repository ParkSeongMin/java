package com.seongmin.test.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiHelloImpl extends UnicastRemoteObject implements RmiHello {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	protected RmiHelloImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String sayHello() throws RemoteException {
		return "Hello, world!";
	}

}
