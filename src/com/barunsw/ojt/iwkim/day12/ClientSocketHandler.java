package com.barunsw.ojt.iwkim.day12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	
	private Socket socket;
	private boolean runFlag;
	
	public ClientSocketHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		LOGGER.debug("+++ run");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String readLine = null;
			
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug("recv message:" + readLine);
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		LOGGER.debug("--- run");
	}
}
