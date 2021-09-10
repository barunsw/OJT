package com.barunsw.ojt.sjcha.day12.RMIchat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	private Map<String, ClientInterface> clientData = new HashMap<>();

	protected ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public int register(String userId, ClientInterface clientIf) throws RemoteException {
		LOGGER.debug("Client Register");
		
		clientData.put(userId, clientIf);

		LOGGER.debug("clientData key : "+ clientData.keySet() + "clientData value : " + clientData.values());

		return 0;
	}

	@Override
	public int deregister(String userId) throws RemoteException {
		LOGGER.debug("Client Deregister");

		clientData.remove(userId);

		return 0;
	}

	@Override
	public int send(String userId, String message) throws RemoteException {
		LOGGER.debug("Send Userid : "+ userId + "SendMessage : " + message);

		for (ClientInterface oneClient : clientData.values()) {
			oneClient.push(String.format("%s : %s", userId, message));
		}

		return 0;
	}
}
