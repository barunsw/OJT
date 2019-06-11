package com.barunsw.ojt.gtkim.day16.chatting;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	
	public ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void push(String msg) throws RemoteException {
		ClientMain.eventQueueWorker.push(msg);
	}
}
