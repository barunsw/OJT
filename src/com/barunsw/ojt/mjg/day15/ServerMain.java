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
	
	public static final int PORT = 50014;

	private boolean runFlag;
	
	public void start() {
		LOGGER.debug(String.format("+++ ServerMain started."));
		
		Properties properties = new Properties();
		try (Reader reader = Resources.getResourceAsReader("config.properties")) {
            properties.load(reader);
            
            String addressIfClass = properties.getProperty("address_if_class");
            
            Object o = Class.forName(addressIfClass).newInstance();
            
            rmiAddressBookInterface = (RmiAddressBookInterface)o;
			
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
