package com.barunsw.ojt.sjcha.day12.RMIchat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);

	private ChatPanel chatPanel;

	public ClientImpl(ChatPanel chatPanel) throws RemoteException {
		super();
		this.chatPanel = chatPanel;
	}

	@Override
	public int push(String message) throws RemoteException {
		LOGGER.debug("Send Message : " + message);

		chatPanel.push(message);
		return 0;
	}
}