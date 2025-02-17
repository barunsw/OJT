package com.barunsw.ojt.day16;

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
		
		EventVo event = new EventVo();
		event.setType(EventType.MSG);
		event.setMessage(msg);
		
		ClientMain.eventQueueWorker.push(event);
	}
}
