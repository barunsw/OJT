package com.barunsw.ojt.day15;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface{
	protected ClientImpl() throws RemoteException{
		super();
	}
	
	

}
