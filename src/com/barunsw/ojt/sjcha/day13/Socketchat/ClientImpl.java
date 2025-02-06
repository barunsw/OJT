package com.barunsw.ojt.sjcha.day13.Socketchat;

import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);

	private ChatPanel chatPanel;

	private Socket socket;

	private ClientInterface clientIf;

	public ClientImpl(ChatPanel chatPanel) {
		this.chatPanel = chatPanel;
	}

	@Override
	public int push(String message) throws Exception {
		LOGGER.debug("Send Message : " + message);
		
		// message를 panel에 보내서 textarea에 쓰기
		chatPanel.push(message);
		return 0;
	}
}