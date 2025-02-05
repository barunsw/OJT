package com.barunsw.ojt.sjcha.day15.SocketChat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);

	@Override
	public int push(String message) throws Exception {
		LOGGER.debug("Send Message : " + message);

		ChatPanel.eventQueueWorker.push(message);

		return 0;
	}
}