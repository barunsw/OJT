package com.barunsw.ojt.jyb.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	private Map<String, ClientInterface> clientRepo = new ConcurrentHashMap<>(); //클라이언트 정보를 저장할 Map

	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public void register(String name, ClientInterface clientInterface) throws RemoteException { //클라이언트 이름과 인터페이스로 클라이언트 등록

		LOGGER.debug("register");

		synchronized (clientRepo) { //스레드 안전성 보장
			clientRepo.put(name, clientInterface);
		}
	}

	@Override
	public void deregister(String name) throws RemoteException { //클라이언트 해제
		LOGGER.debug("deregister");
	}

	@Override
	public void send(String name, String msg) throws RemoteException { //특정 클라이언트에게 메세지 보냄
		LOGGER.debug(String.format("+++Server send name : [%s] msg : [%s]", name, msg));

		synchronized (clientRepo) {
			for (ClientInterface oneClient : clientRepo.values()) {
				oneClient.push(String.format("[%s]:%s", name, msg));
			}
		}
	}

}
