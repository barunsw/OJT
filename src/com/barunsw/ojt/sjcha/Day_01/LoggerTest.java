package com.barunsw.ojt.sjcha.Day_01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerTest {
	private static Logger LOGGER = LogManager.getLogger(LoggerTest.class);

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			LOGGER.debug("Hello, world! x" + (i + 1));
		}
	}
}
