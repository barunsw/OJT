package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class socketImpl implements ServerInterface {

	private static final Logger LOGGER = LogManager.getLogger(socketImpl.class);

	private Socket socket;
	private BufferedWriter writer;

	private ClientInterface clientIf;

	public socketImpl(String host, int port, ClientInterface clientIf) throws Exception {
		socket = new Socket(host, port);
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		this.clientIf = clientIf;
		SeverSocketHandler handler = new SeverSocketHandler(socket, clientIf);
		handler.start();
	}

	@Override
	public int register(String userId, ClientInterface clientIf) throws Exception {
		String command = String.format("REG:" + userId);

		LOGGER.debug("command : " + command);

		writer.write(command + "\n");

		writer.flush();

		return 0;
	}

	@Override
	public int deregister(String userId) throws Exception {
		String command = String.format("DEREG:" + userId);

		LOGGER.debug("command : " + command);

		writer.write(command + "\n");

		writer.flush();

		return 0;
	}

	@Override
	public int send(String userId, String message) throws Exception {
		String command = String.format("SEND:" + userId + "," + message);

		LOGGER.debug("command : " + command);

		writer.write(command + "\n");

		writer.flush();
		
		return 0;
	}
}