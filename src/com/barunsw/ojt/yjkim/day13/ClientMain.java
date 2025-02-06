package com.barunsw.ojt.yjkim.day13;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain extends Thread{
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);
	 AddressBookInterface addressBookIf;
	public ClientMain() {
		 try {
			//addressBookIf = new ObjectSocketAddressImpl("localhost", ServerMain.PORT);
			//addressBookIf = new StreamSocketAddressImpl("localhost", ServerMain.PORT);
			//addressBookIf = new UDPAddressImpl("localhost",2000);
			 addressBookIf = new UDPObjectAddressImpl("localhost",2000);
		 } catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	public static void main(String[] args) {
		try {
			SwingTest.main(null);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
