package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	public static final Logger LOGGER = LogManager.getLogger(ServerMain.class);

	public static final int PORT = 5000;

	private boolean runFlag;

	private ServerInterface serverImpl = new ServerImpl();

	public void initServerStart() {
		runFlag = true;

		LOGGER.debug("initServerStart() : starting Server ...");

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			while (runFlag) {
				Socket socket = serverSocket.accept();

				ClientSocketHandler handler = new ClientSocketHandler(socket, serverImpl);
				handler.start();
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		LOGGER.debug("initServerStart() : Server has been started.");
	}

	public static void main(String[] args) {
		new ServerMain().initServerStart();
	}
}