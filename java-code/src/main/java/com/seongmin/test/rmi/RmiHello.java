package com.seongmin.test.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiHello extends Remote {
	String sayHello() throws RemoteException;
}
