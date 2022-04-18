package com.barunsw.ojt.cjs.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

	private Map<String, ClientInterface> clientRepo = new ConcurrentHashMap<>();

	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public void register(String name, ClientInterface clientInterface) throws RemoteException {
		LOGGER.debug("register" + name);

		synchronized (clientRepo) {
			clientRepo.put(name, clientInterface);
		}
	}
	@Override
	public void deregister(String name) throws RemoteException {
		LOGGER.debug("deregister"+ name);
		synchronized (clientRepo) {
			clientRepo.remove(name);
		}
		// 지워준다.
	}
	@Override
	public void send(String name, String msg) throws RemoteException {
		LOGGER.debug(String.format("Server send name : [%s] msg : [%s]", name, msg));

		synchronized (clientRepo) {
			for (ClientInterface oneClient : clientRepo.values()) {
				oneClient.push(String.format("[%s]:%s", name, msg));
			}
		}
	}
}
