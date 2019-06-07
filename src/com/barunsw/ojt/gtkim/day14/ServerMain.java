package com.barunsw.ojt.gtkim.day14;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.*;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final int PORT = 50000;
	
	private void start() {
		LOGGER.debug("Rmi Server started!");
		
		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			
			RmiAddressBookInterface addressBookIf = 
					new FileAddressBookImpl();
			
		//	Naming.rebind("rmi://localhost:" + PORT +"/ADDRESSBOOK", addressBookIf);
			registry.bind("ADDRESSBOOK", addressBookIf);
		}
		catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		LOGGER.debug("Rmi Server final Line");
	}
	
	public static void main(String[] args) {
		new ServerMain().start();
	}

}
