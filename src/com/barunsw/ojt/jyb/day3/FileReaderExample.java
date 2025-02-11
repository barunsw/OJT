package com.barunsw.ojt.jyb.day3;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReaderExample {
	private static final Logger logger = LoggerFactory.getLogger(FileReaderExample.class);

	public static void main(String[] args) {
		try (FileReader fr = new FileReader("src/main/resources/example.txt");
				BufferedReader br = new BufferedReader(fr)) {
			String line;
			while ((line = br.readLine()) != null) {
				logger.info(line);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
