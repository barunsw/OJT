package com.barunsw.ojt.jyb.day2;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCheckExample {
	private static final Logger logger = LoggerFactory.getLogger(FileCheckExample.class);

	public static void main(String[] args) {
		File file = new File("example.txt"); // 경로에 해당하는 파일 객체 생성. 실제 파일 생성이 아님

		if (file.exists()) {
			logger.info("파일이 존재함");
		} else {
			logger.info("파일이 존재하지 않음");
		}
	}

}
