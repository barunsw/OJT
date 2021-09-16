package com.barunsw.ojt.sjcha.day16;

import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomTest extends Thread {
	private static Logger LOGGER = LogManager.getLogger(RandomTest.class);

	private BoardVo boardData = new BoardVo();

	private ServerInterface serverIf;

	public RandomTest(ServerInterface serverIf) {
		this.serverIf = serverIf;
	}

	@Override
	public void run() {
		while (true) {
			try {
				boardData();
				for (ClientInterface oneClient : ServerImpl.clientData) {
					oneClient.pushAlarm(boardData);
				}
				LOGGER.debug("randomTest thread in");
				Thread.sleep(5000);
			} 
			catch (InterruptedException | RemoteException e) {
				LOGGER.debug(e.getMessage(), e);
			}
		}
	}
	
	public BoardVo boardData() {
		boardData.setBoardId((int) (Math.random() * 38));
		boardData.setSeverity((int) (Math.random() * 4));

		return boardData;
	}
}
