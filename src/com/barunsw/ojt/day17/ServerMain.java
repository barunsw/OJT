package com.barunsw.ojt.day17;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final String BIND_NAME = "ALARM";
	public static final int PORT = 50000;

	private boolean runFlag;
	
	public static ServerImpl serverImpl;
	
	public void start() {
		LOGGER.debug(String.format("+++ ServerMain started."));

		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			
			serverImpl = new ServerImpl();
			
			registry.bind(BIND_NAME, serverImpl);
			
			AlarmGenerator alarmGenerator = new AlarmGenerator();
			alarmGenerator.start();
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		LOGGER.debug(String.format("--- ServerMain started."));
	}
	
	public static void main(String[] args) {
		new ServerMain().start();
	}
}
