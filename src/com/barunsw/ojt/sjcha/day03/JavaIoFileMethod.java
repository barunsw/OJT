package com.barunsw.ojt.sjcha.day03;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaIoFileMethod {
	public static final Logger LOGGER = LogManager.getLogger(JavaIoFileMethod.class);
	
	public static void main(String args[]) {
		LOGGER.debug("main");
		
		File inputFile = new File("data/day03/sjcha/address.txt");
		
		// 파일이 존재하는지 판단
		LOGGER.debug("File exist? : " + inputFile.exists());
		
		// 폴더인지 파일인지 판단
		if (inputFile.isDirectory()) {
			LOGGER.debug("디렉토리(폴더)입니다.");
		}
		else if (inputFile.isFile()) {
			LOGGER.debug("파일입니다.");
		}
		else {
			LOGGER.debug("경로가 이상합니다.");
		}
		
		File inputFile2 = new File("data/day03/sjcha/address2.txt");
		
		// 파일이 없다면 파일 생성
		try {
			if (inputFile2.exists() == false) {
				inputFile2.createNewFile();
			}	
		}
		catch (Exception e) {
			LOGGER.debug("파일이 생성되지 못했습니다.");
		}
	}
}
