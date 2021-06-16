package com.barunsw.ojt.iwkim.day13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.gtkim.day10.DBAddressBookImpl;

public class UdpTestServer {
	private static Logger LOGGER = LogManager.getLogger(UdpTestServer.class);
	
	public static DBAddressBookImpl addressBookImpl;
	
	public static void main(String[] args) throws Exception {
		DatagramSocket socket = new DatagramSocket(50000);

		while (true) {
			byte[] buf = new byte[1024];
			DatagramPacket recvPacket = new DatagramPacket(buf, 0, buf.length);

			socket.receive(recvPacket);
		}
	}
}
