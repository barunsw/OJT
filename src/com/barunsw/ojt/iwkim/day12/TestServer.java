package com.barunsw.ojt.iwkim.day12;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestServer {
	//public static final int PORT = 50001;
	
	private static final Logger LOGGER = LogManager.getLogger(TestServer.class);
	
	private ServerSocket serverSocket;
	
	private boolean runFlag;
	
	public TestServer() {
		try {
			initSocket();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initSocket() throws Exception {
		LOGGER.debug("+++ initSocket");
		
		//serverSocket = new ServerSocket(PORT);
		
		/*
		InetSocketAddress addr = new InetSocketAddress(PORT);
		serverSocket = new ServerSocket();
		serverSocket.bind(addr);
		*/
		runFlag = true;
		while (runFlag) {
			// 클라이언트 연결을 기다린다.
			Socket clientSocket = serverSocket.accept();
			
			// 클라이언트 연동을 ClientSocketHandler에게 맡긴다.
			TestClientSocketHandler handler = new TestClientSocketHandler(clientSocket);
			handler.start();
		}
	}
	
	public static void main(String[] args) {
		new TestServer();
	}
}
