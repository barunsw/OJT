package com.barunsw.ojt.sjcha.day16;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day16.ServerInterface;

public class RandomTest extends Thread {
	private static Logger LOGGER = LogManager.getLogger(RandomTest.class);

	public static List<BoardVo> boardData = new ArrayList<>();
	private ServerInterface serverIf;

	public RandomTest(ServerInterface serverIf) {
		this.serverIf = serverIf;
	}

	@Override
	public void run() {
		while (true) {
			try {
				serverIf.selectBoardList();
				LOGGER.debug("randomTest thread in");
				Thread.sleep(5000);
			} 
			catch (InterruptedException | RemoteException e) {
				LOGGER.debug(e.getMessage(), e);
			}
		}
	}
}
