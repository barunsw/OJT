package com.barunsw.ojt.cjs.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientImpl.class);

	public ClientImpl() throws RemoteException {
		super();
	}
	@Override
	public void push(String msg) throws RemoteException {
		LOGGER.debug("msg:" + msg);
		ClientMain.eventQueueWorker.push(msg);
	}
}
