package com.barunsw.ojt.phs.day16.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final String BIND_NAME = "CHAT";
	public static final int PORT = 33333;
	
	public void start() {
		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			
			ServerInterface serverIf = 
					new ServerImpl();
			registry.bind(BIND_NAME, serverIf);
			
			AlaramSignal alaramGenerator = new AlaramSignal(serverIf);
			alaramGenerator.start();
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		new ServerMain().start();
	}
}
