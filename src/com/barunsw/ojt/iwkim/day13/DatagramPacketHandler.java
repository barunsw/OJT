package com.barunsw.ojt.iwkim.day13;

import java.net.DatagramPacket;

public class DatagramPacketHandler extends Thread {
	public DatagramPacketHandler(DatagramPacket recvPacket) {
		
	}
	
	@Override
	public void run() {
//		LOGGER.debug(String.format("[%s]receivedData:[%s]", recvPacket.getSocketAddress(), new String(recvPacket.getData(), 0, recvPacket.getLength())));
//		
//		DatagramPacket sendPacket = new DatagramPacket("result".getBytes(), 10, recvPacket.getSocketAddress());
//		UdpTestServer.addressBookImpl.sele
	}
}
