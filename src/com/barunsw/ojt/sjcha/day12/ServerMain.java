package com.barunsw.ojt.sjcha.day12;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	public static final int PORT = 5000;
	
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public ServerMain() {
		// TODO Auto-generated constructor stub
		try {
			initServerStart();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void initServerStart() {
		try {
			LOGGER.debug("+++ Server start +++");
			// PORT에 대한 registry 생성
			Registry registry = LocateRegistry.createRegistry(PORT);
			
			AddressBookInterface addressBookInterface = new RmiImpl();
			
			// "ADDRESSBOOK"의 register를 연결
			registry.bind("ADDRESSBOOK", addressBookInterface);
			LOGGER.debug("--- Server start ---");
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public static void main(String[] args) {
		new ServerMain();
	}
}
