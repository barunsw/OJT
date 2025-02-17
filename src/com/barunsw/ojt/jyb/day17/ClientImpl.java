package com.barunsw.ojt.jyb.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);
	
	public ClientImpl() throws RemoteException {
		super();
	}
    
    @Override
    public void push(String msg) throws RemoteException {
        LOGGER.debug("클라이언트의 메세지 push가 실행됨: " + msg);
        
        ClientMain.eventQueueWorker.push(msg); //클라이언트 메세지를 큐에 푸시
    }

}
