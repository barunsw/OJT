package com.barunsw.ojt.phs.day14;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S_ServerMain {
	
	private static final Logger LOGGER = LogManager.getLogger(S_ServerMain.class);
	
	public static void main(String[] args) {
		
		try {
			S_AddressBookInterface addressBook = new S_DBAddressBookImpl();
			addressBook.createStorage();
			
			// RMI registry를 local에 생성
			Registry registry = LocateRegistry.createRegistry(30000); 
			
			// RMI registry에 등록
			registry.rebind("AddressBook", addressBook);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
	}

}
