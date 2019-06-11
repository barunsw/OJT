package com.barunsw.ojt.yjkim.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl extends UnicastRemoteObject 
	implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	private List<ClientInterface> clientRepo = 
			new ArrayList<>();
	
	public ServerImpl() throws RemoteException {
		super();
	}
	
	@Override
	public void register(ClientInterface clientInterface) throws RemoteException {
		LOGGER.debug("register");
		synchronized (clientRepo) {
			clientRepo.add(clientInterface);
		}
	}

	@Override
	public void send() throws RemoteException {
		LOGGER.debug("send");
		synchronized (clientRepo) {
			for (ClientInterface oneClient : clientRepo) {
					AlaramVo alaramVo = new AlaramVo();
					oneClient.push(alaramVo);
			}
		}
					
	}
}
