package com.barunsw.ojt.iwkim.day13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.gtkim.day10.DBAddressBookImpl;

public class UdpTestServer {
	public static final int PORT = 50001;
	private static Logger LOGGER = LogManager.getLogger(UdpTestServer.class);
	
	public static DBAddressBookImpl addressBookImpl;
	
	private DatagramSocket socket;
	
	public UdpTestServer() {
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
		
		while (true) {
			byte[] buf = new byte[1024];
			DatagramPacket recvPacket = new DatagramPacket(buf, 0, buf.length);

			socket.receive(recvPacket);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
	}
}
