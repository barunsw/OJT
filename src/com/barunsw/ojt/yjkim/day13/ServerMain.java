package com.barunsw.ojt.yjkim.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final int PORT = 50000;

	private boolean runFlag;
	
	public void start() {
		LOGGER.debug(String.format("ServerMain started."));

		runFlag = true;
		UDPObjectClientSocketHandler handler;
		try {
			handler = new UDPObjectClientSocketHandler(new DatagramSocket(PORT));
			handler.start();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		/*UDPClientSocketHandler handler;
		try {
			handler = new UDPClientSocketHandler(new DatagramSocket(PORT));
			handler.start();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}*/
		/*try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			while (runFlag) {
				Socket socket = serverSocket.accept();

				LOGGER.debug(String.format("client connected(%s)", socket.getRemoteSocketAddress()));
				
				Object 방식 
				ObjectClientSocketHandler handler;
				try {
					handler = new ObjectClientSocketHandler(socket);
					handler.start();

				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
				
				Stream 방식
				StreamClientSocketHandler handler;
				try {
					handler = new StreamClientSocketHandler(socket);
					handler.start();
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
				
				
			}
		} 
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}*/
	}
	
	public static void main(String[] args) {
		new ServerMain().start();
	}
}
