package com.barunsw.ojt.day12_1;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain {
	private static Logger LOGGER = LogManager.getLogger(ClientMain.class);
	
	public static void main(String[] args) {
		LOGGER.debug("+++ ClientMain");

		try {
			Socket s = new Socket("localhost", 50000);
			
			try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
				writer.write("Hello World");
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		LOGGER.debug("--- ClientMain");
	}
}
