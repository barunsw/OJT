package com.barunsw.ojt.iwkim.day01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerTest {
	private static Logger LOGGER = LogManager.getLogger(LoggerTest.class);
	
	public static void main(String[] args) {
		LOGGER.debug("main");
	}
}
