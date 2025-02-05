package com.barunsw.ojt.phs.day16.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl extends UnicastRemoteObject 
	implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	private List<ClientInterface> clientRepo = new ArrayList<>();
	
	public ServerImpl() throws RemoteException {
	}
	
	@Override
	public void register(ClientInterface clientInterface) throws RemoteException {
		synchronized (clientRepo) {
			clientRepo.add(clientInterface);
		}
	}

	@Override
	public void alaram() throws RemoteException {
		LOGGER.debug("alaram");
		synchronized (clientRepo) {
			for (ClientInterface oneClient : clientRepo) {
					AlaramVo alaramVo = new AlaramVo();
					oneClient.push(alaramVo);
			}
		}
	}
}
