package com.barunsw.ojt.iwkim.day16;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final String BIND_NAME = "ALARM";
	public static final int RMI_PORT = 1099;
	
	private boolean runFlag;
	
	public ServerMain() {
		try {
			initRmi();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initRmi() throws Exception {
		Registry registry = LocateRegistry.createRegistry(RMI_PORT);
		
		ServerInterface serverIf = new ServerImpl();
		
		registry.bind(BIND_NAME, serverIf);
	}
	
	public static void main(String[] args) throws Exception {
		new ServerMain();
	}
}
