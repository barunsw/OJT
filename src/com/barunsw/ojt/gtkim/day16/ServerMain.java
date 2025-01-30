package com.barunsw.ojt.gtkim.day16;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final int PORT = 9988;
	
	public static final String BIND_NAME = "ALARM";
	
	private void start() {
		try {
			LOGGER.debug("Server start!");
			Registry registry = LocateRegistry.createRegistry(PORT);
			
			ServerInterface serverIf = new ServerImpl();
			
			registry.bind(BIND_NAME, serverIf);		
			
			AlarmGenerator alarmGenerator = new AlarmGenerator(serverIf);
			alarmGenerator.start();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public static void main(String[] args) {
		new ServerMain().start();
	}

}
