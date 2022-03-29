package com.barunsw.ojt.sjcha.day15.RmiChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day15.RmiChat.ChatPanel;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);

	public ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public int push(String message) throws RemoteException {
		LOGGER.debug("Send Message : " + message);

		ChatPanel.eventQueueWorker.push(message);
		return 0;
	}
}