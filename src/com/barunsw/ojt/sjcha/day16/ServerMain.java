package com.barunsw.ojt.sjcha.day16;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	public static final Logger LOGGER = LogManager.getLogger(ServerMain.class);

	public static final int PORT = 5000;

	public void initServerStart() {
		LOGGER.debug("initServerStart(): Starting the server...");

		try {
			ServerInterface server = new ServerImpl();

			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("RMIRACKVIEW", server);

			LOGGER.debug("randomtest start");

			RandomTest randomTest = new RandomTest(server); 
			randomTest.start();
		}
		catch (Exception ex) {
			LOGGER.error("initServerStart(): Failed to start server: " + ex.getMessage(), ex);
		}
	}

	public static void main(String[] args) {
		new ServerMain().initServerStart();
	}
}