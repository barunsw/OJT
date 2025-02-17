package com.barunsw.ojt.mjg.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.mjg.day17.ClientMain;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);
	
	public ClientImpl() throws RemoteException {
		super();
	}
    
    @Override
    public void push(String msg) throws RemoteException {
        LOGGER.debug("+++(ClientImpl.push 실행됨) msg: " + msg);
        
        ClientMain.eventQueueWorker.push(msg);
    }

}
