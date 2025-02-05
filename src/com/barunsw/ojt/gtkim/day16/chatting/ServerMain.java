package com.barunsw.ojt.gtkim.day16.chatting;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final String BIND_NAME = "CHAT";
	
	public static final int PORT = 9988;
	
	private boolean runFlag;
	
	public void start() {
		LOGGER.debug("Server Started!");
		
		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			
			ServerInterface serverif = new ServerImpl();
			
			registry.bind(BIND_NAME, serverif);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public static void main(String[] args) {
		new ServerMain().start();
	}

}
