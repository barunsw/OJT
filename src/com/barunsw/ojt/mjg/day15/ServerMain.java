package com.barunsw.ojt.mjg.day15;

import java.io.IOException;
import java.io.Reader;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	private RmiAddressBookInterface rmiAddressBookInterface;
	
	public static final int PORT = 50000;

	private boolean runFlag;
	
	public void start() {
		LOGGER.debug(String.format("+++ ServerMain started."));
		
		try {
			
			rmiAddressBookInterface = new RmiAddressBookImpl();
		
			Registry registry = LocateRegistry.createRegistry(PORT);
			
			registry.rebind("ADDRESSBOOK", rmiAddressBookInterface);

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
