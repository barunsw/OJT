package com.barunsw.ojt.sjcha.day13.Socketchat;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client_ServerWriteImpl implements ServerInterface {

	private static final Logger LOGGER = LogManager.getLogger(Client_ServerWriteImpl.class);

	private Socket socket;
	private BufferedWriter writer;

	public Client_ServerWriteImpl(String host, int port, ClientInterface clientIf) throws Exception {
		socket = new Socket(host, port);
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		Sever_ClientSocketHandler handler = new Sever_ClientSocketHandler(socket, clientIf);
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