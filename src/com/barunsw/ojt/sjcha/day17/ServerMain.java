package com.barunsw.ojt.sjcha.day17;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day17.ServerImpl;

public class ServerMain {
	public static final Logger LOGGER = LogManager.getLogger(ServerMain.class);

	public static final int PORT = 5000;

	public static ServerImpl server;

	public void initServerStart() {
		LOGGER.debug("initServerStart(): Starting the server...");

		try {
			server = new ServerImpl();

			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("RMIRACKVIEW", server);

			LOGGER.debug("randomtest start");

			AlarmGenerator alarmGenerator = new AlarmGenerator(); 
			alarmGenerator.start();
		}
		catch (Exception ex) {
			LOGGER.error("initServerStart(): Failed to start server: " + ex.getMessage(), ex);
		}
	}

	public static void main(String[] args) {
		new ServerMain().initServerStart();
	}
}
