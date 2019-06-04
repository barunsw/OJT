package com.barunsw.ojt.gtkim.day12;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFileManager {
	private static final Logger LOGGER = LogManager.getLogger(TestFileManager.class);
	
	private final String DIR = "data/gtkim/day10/";
	private final String TXT = ".txt";
	private Path filePath;

	public TestFileManager(String fileName) {
		try{
			filePath = Paths.get(DIR + fileName + TXT);
		    if(!Files.exists(Paths.get(DIR))){
		    	Files.createDirectories(Paths.get(DIR));
		    	LOGGER.debug("디렉토리가 생성되었습니다." + DIR);
		    }
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void fileOpen() {
		try {
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
				LOGGER.debug("파일이 생성 되었습니다 " + filePath);
			} 
			
			String time = Files.getLastModifiedTime(filePath).toString() + "\n";
			Files.write(filePath, time.getBytes(), StandardOpenOption.APPEND);
			LOGGER.debug("파일 쓰기 완료");
	
			Process notepad = Runtime.getRuntime().exec(
					new String[] { "C:\\Program Files\\Notepad++\\notepad++.exe", filePath.toAbsolutePath().toString() });
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} 
	}	
}
