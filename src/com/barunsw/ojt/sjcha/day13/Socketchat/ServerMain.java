package com.barunsw.ojt.sjcha.day13.Socketchat;

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
			// client의 요청을 기다린다.
			while (runFlag) {
				Socket socket = serverSocket.accept();

				Client_ServerSocketHandler handler = new Client_ServerSocketHandler(socket, serverImpl);
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