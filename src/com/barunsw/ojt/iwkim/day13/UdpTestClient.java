package com.barunsw.ojt.iwkim.day13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UdpTestClient {
	private static Logger LOGGER = LogManager.getLogger(UdpTestClient.class);
	
	public static void main(String[] args) throws Exception {
		DatagramSocket socket = new DatagramSocket();
		
		SocketAddress dest = new InetSocketAddress("localhost", 50000);
		
		byte[] message = "Hello World".getBytes();
		DatagramPacket packet = new DatagramPacket(message, message.length, dest);
		
		socket.send(packet);
		socket.send(packet);
		socket.send(packet);
		socket.send(packet);
		socket.send(packet);
		socket.send(packet);
		socket.send(packet);
		socket.send(packet);
	}
}
