package com.barunsw.ojt.gtkim.day13;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatagramServerMain {
	private static final Logger LOGGER = LogManager.getLogger(DatagramServerMain.class);
	
	public static final int PORT = 9998;
	public static final int SIZE = 1024;
	
	private boolean runFlag;
	private byte[] buffer;

	private void start() {
		LOGGER.debug("Datagram Server Main Start!");
		
		runFlag = true;
		
		DatagramSocket socket = null;
		DatagramPacket packet = null;	
		try {
			socket = new DatagramSocket(PORT);
			while (runFlag) {
				buffer = new byte[SIZE];
				packet = new DatagramPacket(buffer, buffer.length);
				
				socket.receive(packet);
				
//				ClientDatagramHandler handle = 
//						new ClientDatagramHandler(socket, packet);
				ClientDatagramHandlerObj handle =
						new ClientDatagramHandlerObj(socket, packet);
				handle.start();
			
				LOGGER.debug(String.format("Client Connected [%s]:%d", 
						packet.getAddress(), socket.getPort()));
			}
		}
		catch (SocketException se) {
			LOGGER.error(se.getMessage(), se);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		finally {
			if (socket != null) {
				socket.close();
			}
		}
	}
	
	public static void main(String[] args) {
		new DatagramServerMain().start();

	}

}
