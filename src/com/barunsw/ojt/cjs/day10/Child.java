package com.barunsw.ojt.cjs.day10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Child  {
	private static final Logger LOGGER = LoggerFactory.getLogger(Child.class);
	public String str1 = "1";

	public Child() {
	}

	public Child(String str) {
		str1 = str;
	}

	public int method4(int n) {
		LOGGER.debug("method4: " + n);
		return n;
	}
}
