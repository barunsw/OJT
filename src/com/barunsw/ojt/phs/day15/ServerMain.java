package com.barunsw.ojt.phs.day15;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ServerMain {

	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static void main(String[] args) {

		try {
			RackViewInterface rackViewInterface = new RackViewImpl();
			
			Registry registry = LocateRegistry.createRegistry(35000);
			registry.rebind("RackView", rackViewInterface);
		}
		catch (Exception e) {
			LOGGER.debug(e.getLocalizedMessage(), e);
		}
		
	}

}
