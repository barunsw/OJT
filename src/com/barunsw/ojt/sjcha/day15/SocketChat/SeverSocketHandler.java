package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeverSocketHandler extends Thread {
	public static final Logger LOGGER = LogManager.getLogger(SeverSocketHandler.class);

	private Socket socket;

	private BufferedReader reader;

	private ClientInterface clientIf;

	public SeverSocketHandler(Socket socket, ClientInterface clientIf) throws IOException {
		this.socket = socket;
		this.clientIf = clientIf;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override 
	public void run() {
		try {
			String message;

			while ((message = reader.readLine()) != null) {
				LOGGER.debug("Print New message : " + message);
				clientIf.push(message);
			}
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}
	}
}