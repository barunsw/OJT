package com.barunsw.ojt.iwkim.day15;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private static final long serialVersionUID = 1806085071078823822L;
	
	private static Logger LOGGER = LogManager.getLogger(ClientImpl.class); 
	
	private ServerInterface serverIf;
	
	public ClientImpl() throws RemoteException {
		try {
			initRmi();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initRmi() throws Exception {
		Registry registry = LocateRegistry.getRegistry(1099);

		serverIf = (ServerInterface)registry.lookup("CHAT");
	}
	
	public ResultVo register(String name) throws Exception {
		if (serverIf == null)
			initRmi();
			
		return serverIf.register(name, this);
	}

	
	@Override
	public void push(String msg) throws RemoteException {
		// 메세지를 보내는 작업은 eventQeueWorker에게 전담시킨다.
		LOGGER.debug("message:" + msg);
		
		ClientMain.eventQueueWorker.push(msg);
	}
}
