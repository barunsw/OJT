package com.barunsw.ojt.gtkim.day13;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	
	public static final int PORT = 9998;
	
	private boolean runFlag;
	
	private void start() {
		LOGGER.debug("ServerMain Started");
	
		runFlag = true;
		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			Socket socket = null;
			while (runFlag) {
				socket = serverSocket.accept();
				
				//ClientSocketHandlerObj handler = new ClientSocketHandlerObj(socket);
				ClientSocketHandlerString handler = new ClientSocketHandlerString(socket);
				
				handler.start();
				
				LOGGER.debug(String.format("client connected(%s)", socket.getRemoteSocketAddress()));
			}
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		new ServerMain().start();
	}
	
}