package com.barunsw.ojt.iwkim.day14;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.day12.ServerMain;

public class RmiServerMain {
	private static Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final int PORT = 50003;
	public static final String REMOTE_NAME = "ADDRESS_BOOK";
	
	public RmiServerMain() {
		try {
			initRmi();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initRmi() throws Exception {
		LOGGER.info("+++ RMI start");
		// RMI registry를 로컬의 PORT에 생성!
		Registry registry = LocateRegistry.createRegistry(PORT);
		
		AddressBookInterface addressBook = new DBAddressBookImpl();
		// RMI registry에 등록!
		registry.bind(REMOTE_NAME, addressBook);

	}
	
	public static void main(String[] args) {
		new RmiServerMain();
	}
}
