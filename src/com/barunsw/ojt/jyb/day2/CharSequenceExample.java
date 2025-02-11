package com.barunsw.ojt.jyb.day2;

import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharSequenceExample {

	private static final Logger logger = LoggerFactory.getLogger(StringSharingExample.class);

	public static void main(String[] args) {
		CharSequence charSequence = "Hello";

		IntStream charStream = charSequence.chars();

		charStream.forEach(codePoint -> logger.info(String.valueOf(codePoint)));
	}

}
