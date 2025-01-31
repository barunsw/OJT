package com.barunsw.ojt.iwkim.day13;

import java.net.DatagramSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.day12.DBAddressBookImpl;


public class UDPServerMain {
	public static final int PORT = 50011;
	private static Logger LOGGER = LogManager.getLogger(UdpTestServer.class);
	
	public static DBAddressBookImpl dbAddressBookImpl;
	
	private DatagramSocket socket;
	
	public UDPServerMain() {
		try {
			initSocket();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initSocket() throws Exception {
		LOGGER.info("+++ initSocket");
		
		socket = new DatagramSocket(PORT);
		dbAddressBookImpl = new DBAddressBookImpl();
		
		UDPClientSocketHandler udpHandler = new UDPClientSocketHandler(socket);
		udpHandler.start();
		
	}
	
	public static void main(String[] args) throws Exception {
		new UDPServerMain();
	}
}
