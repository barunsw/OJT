package com.barunsw.ojt.day01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewLoggerTest {
	private static Logger LOGGER = LoggerFactory.getLogger(NewLoggerTest.class);
	
	public static void main(String[] args) {
		LOGGER.debug("Hello World");
	}
}
