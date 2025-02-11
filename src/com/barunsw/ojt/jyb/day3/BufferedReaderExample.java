package com.barunsw.ojt.jyb.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BufferedReaderExample {
	private static final Logger logger = LoggerFactory.getLogger(com.barunsw.ojt.jyb.day3.BufferedReaderExample.class);

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/example.txt"))) {
			String line;
			while ((line = br.readLine()) != null) { //파일에서 읽을 수 있는 줄이 있는 동안 계속해서 반복
				logger.info(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
