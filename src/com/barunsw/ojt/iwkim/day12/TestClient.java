package com.barunsw.ojt.iwkim.day12;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestClient {
	private static final Logger LOGGER = LogManager.getLogger(TestServer.class);
	
	private Socket socket;
	
	public TestClient() {
		try {
			initSocket();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initSocket() throws Exception {
		//socket = new Socket("localhost", TestServer.PORT);
		LOGGER.debug("--- initSocket");
		
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			String message = "Hello World";
			writer.write(message + "\n");
		}
	}
	
	public static void main(String[] args) {
		new TestClient();
	}
}
