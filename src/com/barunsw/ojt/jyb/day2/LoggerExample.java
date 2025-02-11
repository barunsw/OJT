package com.barunsw.ojt.jyb.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerExample {
	private static final Logger logger = LoggerFactory.getLogger(LoggerExample.class);

	public static void main(String[] args) {

		long startTimeSLF4J = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			logger.info("SLF4J 번호 : ", i);
		}

		long endTimeSLF4J = System.currentTimeMillis();

		System.out.println("SLF4J 로깅 시간 : " + (endTimeSLF4J - startTimeSLF4J) + " ms");

		long startTimePrint = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			System.out.println("Print 번호: " + i);
		}

		long endTimePrint = System.currentTimeMillis();

		System.out.println("Print 출력 시간: " + (endTimePrint - startTimePrint) + " ms");
	}
}