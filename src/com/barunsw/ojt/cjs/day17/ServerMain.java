package com.barunsw.ojt.cjs.day17;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);
	
	public static final String BIND_NAME = "Rack";
	public static final int PORT = 50001;
	
	public void start() {
		LOGGER.debug(String.format("+++ ServerMain started."));

		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			ServerInterface serverIf = new ServerImpl();

			registry.bind(BIND_NAME, serverIf);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		LOGGER.debug(String.format("--- ServerMain started."));
	}

	public static void main(String[] args) {
		new ServerMain().start();
	}
}
