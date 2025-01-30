package com.barunsw.ojt.gtkim.day11;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadOne extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ThreadOne.class);

	private int id;
	
	public ThreadOne(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 15; i++) {
			LOGGER.debug(String.format("[%d]%d", id, i));
		}
	}
}
