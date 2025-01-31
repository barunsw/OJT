package com.barunsw.ojt.iwkim.day12;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	public static final int PORT = 50005;
	public static DBAddressBookImpl dbAddressBookImpl;
	
	private static final Logger LOGGER = LogManager.getLogger(TestServer.class);
	
	private ServerSocket serverSocket;
	
	private boolean runFlag;
	
	public ServerMain() {
		try {
			initSocket();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initSocket() throws Exception {
		LOGGER.info("+++ initSocket");
		
		serverSocket = new ServerSocket(PORT);
		dbAddressBookImpl = new DBAddressBookImpl();
		
		runFlag = true;
		while (runFlag) {
			// 클라이언트 연결을 기다린다.
			Socket clientSocket = serverSocket.accept();
			// 클라이언트 연동을 ClientSocketHandler에게 맡긴다.
			ClientSocketHandler handler = new ClientSocketHandler(clientSocket);
			handler.start();
		}
	}
	
	public static void main(String[] args) {
		new ServerMain();
	}
}
