package com.barunsw.ojt.jyb.day3;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileInputStreamExample {

	private static final Logger logger = LoggerFactory.getLogger(FileInputStreamExample.class);

	public static void main(String[] args) {
		try (FileInputStream fis = new FileInputStream("src/main/resources/example.txt")) {
			int data; // read() 메소드가 int타입의 값을 반환
			while ((data = fis.read()) != -1) { // 파일의 끝에 도달하면 -1 반환
				char character = (char) data; // 바이트를 문자로 변환
				logger.info(String.valueOf(character)); // 문자를 문자열로 변환
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
