package com.barunsw.ojt.phs.day16.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);
	
	public ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void push(MessageVO messageVo) throws RemoteException {
		LOGGER.debug("ClientImple push");
		ClientMain.eventQueueWorker.push(messageVo);
	}
}
