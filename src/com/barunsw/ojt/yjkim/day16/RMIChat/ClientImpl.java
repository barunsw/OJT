package com.barunsw.ojt.yjkim.day16.RMIChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl extends UnicastRemoteObject
	implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);
	
	public ClientImpl() throws RemoteException {
		super();
	}
	
	@Override
	public void push(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("msg:" + msg);
		
		ClientTestPanel.eventQueueWorker.push(msg);
	}
}
