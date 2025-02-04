package com.barunsw.ojt.gtkim.day09;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DP_Banner {
	private static final Logger LOGGER = LogManager.getLogger(DP_Banner.class);

	private String string;
	
	public DP_Banner(String string) {
		this.string = string;
	}
	
	public void showWithParen() {
		LOGGER.debug("(" + string + ")");
	}
	
	public void showWithAster() {
		LOGGER.debug("*" + string + "*");
	}
}
