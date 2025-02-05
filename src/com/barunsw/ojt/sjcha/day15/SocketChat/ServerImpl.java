package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ServerImpl implements ServerInterface {

	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	private Map<String, ClientInterface> clientData = new HashMap<>();

	@Override
	public int register(String userId, ClientInterface clientIf) throws Exception {
		LOGGER.debug("register(userId, clientIf) : Register user from Server to Client");

		clientData.put(userId, clientIf);

		LOGGER.debug("clientData key : " + clientData.keySet() + "clientData value : " + clientData.values());

		return 0;
	}

	@Override
	public int deregister(String userId) throws Exception {
		LOGGER.debug("deregister(userId) : Deregister user from Server to Client");

		clientData.remove(userId);

		return 0;
	}

	@Override
	public int send(String userId, String message) throws Exception {
		LOGGER.debug("Send(userId, message) : Userid - " + userId + " SendMessage - " + message);

		for (ClientInterface clientIf : clientData.values()) {
			clientIf.push(userId + " : " + message + "\n");
		}

		return 0;
	}
}