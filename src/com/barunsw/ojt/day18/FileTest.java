package com.barunsw.ojt.day18;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileTest {
	private static final Logger LOGGER = LogManager.getLogger(FileTest.class);
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	public static void main(String[] args) {
		File root = new File("/");
		File[] fileList = root.listFiles();

		StringBuffer buf = new StringBuffer();
		
		for (File oneFile : fileList) {
			if (oneFile.isDirectory()) {
				buf.append("D");
			}
			else if (oneFile.isFile()) {
				buf.append("F");
			}

			buf.append("\t");
			buf.append(oneFile.getName());
			buf.append("(" + oneFile.getAbsolutePath() + ")");
			buf.append("\t");

			String created = "";
			try {
				BasicFileAttributes attrs = Files.readAttributes(oneFile.toPath(), BasicFileAttributes.class);
				FileTime fileTime = attrs.creationTime();		
				created = simpleDateFormat.format(new Date(fileTime.toMillis()));
			}
			catch (Exception ex) {}
			
			buf.append(created);
			buf.append("\t");
			buf.append(oneFile.length());
			buf.append("\n");
			
			if (oneFile.isDirectory() && oneFile.listFiles() != null) {
				for (File childFile : oneFile.listFiles()) {
					buf.append("\t\t" + childFile.getName() + "\n");
				}
			}
		}
		
		LOGGER.debug(buf.toString());
	}
}
