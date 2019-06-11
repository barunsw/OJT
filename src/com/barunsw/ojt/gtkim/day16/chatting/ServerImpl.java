package com.barunsw.ojt.gtkim.day16.chatting;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	private Map<String, ClientInterface> clientRepo = new ConcurrentHashMap<>();
	
	public ServerImpl() throws RemoteException { 
		super();
	}
	
	@Override
	public void register(String name, ClientInterface clientInterface) throws RemoteException {
		LOGGER.debug("Register name : " + name);
		
		synchronized (clientRepo) {
			clientRepo.put(name, clientInterface);
		}
	}

	@Override
	public void send(String name, String msg) throws RemoteException {
		LOGGER.debug("Send Msg to All register");
		
		synchronized (clientRepo) {
			for (ClientInterface oneIf : clientRepo.values()) {
					oneIf.push(String.format("[%s] : %s", name, msg));
					LOGGER.debug(String.format("[%s] : %s", name, msg));
			}
		}
	}

}
