package com.barunsw.ojt.yjkim.day14;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.RmiAddressBookInterface;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	private CommonFunction commonFunction = new CommonFunction();

	private boolean runFlag;
	
	public void start() {
		LOGGER.debug(String.format("+++ ServerMain started."));

		try {
			Registry registry = LocateRegistry.createRegistry(commonFunction.getPort());
			
			RmiAddressBookInterface addressBookIf = 
					new AddressBookImpl();
			
			registry.bind("ADDRESSBOOK", addressBookIf);
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
