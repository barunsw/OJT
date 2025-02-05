package com.barunsw.ojt.sjcha.day13.Socketchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sever_ClientSocketHandler extends Thread {
	public static final Logger LOGGER = LogManager.getLogger(Sever_ClientSocketHandler.class);

	private Socket socket;

	private BufferedReader reader;

	private ClientInterface clientImpl;

	public Sever_ClientSocketHandler(Socket socket, ClientInterface clientImpl) throws IOException {
		this.socket = socket;
		this.clientImpl = clientImpl;

		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override 
	public void run() {
		try {
			String message;

			while ((message = reader.readLine()) != null) {
				LOGGER.debug("Print New message : " + message);
				clientImpl.push(message);
			}
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}
	}
}