package com.barunsw.ojt.day12_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	
	private Socket clientSocket;
	
	public ClientSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		LOGGER.debug("+++ ClientSocketHandler run");
			
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("+++ readLine:%s", readLine));
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		LOGGER.debug("--- ClientSocketHandler run");
	}
}
