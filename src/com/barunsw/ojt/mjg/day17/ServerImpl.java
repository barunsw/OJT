package com.barunsw.ojt.mjg.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl extends UnicastRemoteObject 
	implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	private Map<String, ClientInterface> clientRepo = 
			new ConcurrentHashMap<>();
	
	public ServerImpl() throws RemoteException {
		super();
	}
	
	@Override
	public void register(String name, ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("register");
		
		synchronized (clientRepo) {
			clientRepo.put(name, clientInterface);
		}
	}
	
	@Override
	public void deregister(String name) throws RemoteException {
		LOGGER.debug("deregister");
		// 지워준다.
	}

	@Override
	public void send(String name, String msg) throws RemoteException {
	    LOGGER.debug(String.format("+++Server send name : [%s] msg : [%s]", name, msg));

	    synchronized (clientRepo) {
	        for (ClientInterface oneClient : clientRepo.values()) {
	            if (name == null || name.isEmpty()) {
	                // 입장 메시지
	                oneClient.push(msg);
	            } else {
	                // 일반 메시지는 기존 형식대로
	                oneClient.push(String.format("[%s]: %s", name, msg));
	            }
	        }
	    }
	}

/*
	// QueueWorker의 messageRepo 사이즈 10000으로 찍어보기 
	// QueueWorker가 어떻게 동작하는지, 어떤 역할을 하는지
	@Override
	public void send(String name, String msg) throws RemoteException {
	    LOGGER.debug(String.format("+++Server send name : [%s] msg : [%s]", name, msg));

	    synchronized (clientRepo) {
	        for (ClientInterface oneClient : clientRepo.values()) {
	            for (int i = 1; i <= 10000; i++) { 
	                oneClient.push(String.format("[%s]:%s - %d", name, msg, i));
	                
	            }
	        }
	    }
	}
*/

}
