package com.barunsw.ojt.jyb.day3;

import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWriterExample {
	private static final Logger logger = LoggerFactory.getLogger(FileWriterExample.class);

	public static void main(String[] args) {
		try (FileWriter fw = new FileWriter("src/main/resources/example.txt")) {
			fw.write("파일 수정");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
