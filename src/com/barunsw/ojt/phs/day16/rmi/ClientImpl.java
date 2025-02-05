package com.barunsw.ojt.phs.day16.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl extends UnicastRemoteObject
	implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);
	
	public ClientImpl() throws RemoteException {
	}
	
	@Override
	public void push(Object o) throws RemoteException {
		LOGGER.debug("msg:" + o);
		
		ClientPanel.eventQueueWorker.push(o);
	}
}
