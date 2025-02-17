package com.barunsw.ojt.mjg.day16;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.mjg.day16.ServerImpl;

public class ServerMain {
	public static final Logger LOGGER = LogManager.getLogger(ServerMain.class);

	public static final int PORT = 50003;

	public void initServerStart() {
		LOGGER.debug("initServerStart(): +++ServerMain Start...");

		try {

			ServerInterface server = new ServerImpl();
			
	        // RMI 레지스트리 생성
			Registry registry = LocateRegistry.createRegistry(PORT);
			
	        // 현재 객체를 "RMIRACKVIEW" 이름으로 바인딩
			registry.rebind("RMIRACKVIEW", server);

		}
		catch (Exception ex) {
			LOGGER.error("initServerStart(): Failed to start server: " + ex.getMessage(), ex);
		}
	}

	public static void main(String[] args) {
		new ServerMain().initServerStart();
	}
}
