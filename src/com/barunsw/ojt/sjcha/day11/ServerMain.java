package com.barunsw.ojt.sjcha.day11;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day11.ClientSocketHandler;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
	// port 번호
	public static final int PORT = 5000;
	
	private boolean runFlag;
	
	public ServerMain() {
		try {
			initServerSocket();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void initServerSocket () {
		LOGGER.debug("ServerSocket");
		runFlag = true;
		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			while (runFlag) {
				// client의 요청을 받아들인다. 연결을 기다림.
				Socket socket = serverSocket.accept();
				LOGGER.debug("accept do. - {}", socket.getPort());
				// client 연동을 ClientSocketHandler에서 한다. 
				// Thread를 상속 받아서 run()을 구현하고 start를 하여 연동을 한다. 
				ClientSocketHandler handler = new ClientSocketHandler(socket);
				handler.start();
			}
		}
		catch (Exception ex) {
			LOGGER.debug(ex.getMessage(), ex);
		}
	}
	
	// Socket은 close를 해주어야 한다. 
	public static void main(String[] args) {
		new ServerMain();
	}
}
