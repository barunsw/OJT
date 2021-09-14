package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);

	private Socket socket;

	private ServerInterface serverImpl;

	public ClientSocketHandler(Socket socket, ServerInterface serverImpl) {
		this.socket = socket;
		this.serverImpl = serverImpl;
	}

	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			LOGGER.debug("ClientSocketHanler run()");

			String readLine = null;

			while ((readLine = reader.readLine()) != null) {
				String[] cmdSplit = readLine.split(":");

				LOGGER.debug("명령어 : " + cmdSplit[0]);

				switch (cmdSplit[0]) {
				case "REG":
					handRegister(cmdSplit[1]);
					break;
				case "DEREG":
					handDeregister(cmdSplit[1]);
					break;
				case "SEND":
					String[] splitMessage = cmdSplit[1].split(",");
					LOGGER.debug("send message!!!" + splitMessage[0] + splitMessage[1]);
					handSend(splitMessage[0], splitMessage[1]);
					break;
				}
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	public int handRegister(String userName) throws Exception {
		int registerResult = serverImpl.register(userName, new ServerWriteImpl(socket));
		return registerResult;
	}

	public int handDeregister(String userName) throws Exception {
		int deregisterResult = serverImpl.deregister(userName);
		return deregisterResult;
	}

	public int handSend(String userName, String message) throws Exception {
		int sendResult = serverImpl.send(userName, message);
		return sendResult;
	}
}