package com.barunsw.ojt.day03;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;

public class FileTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileTest.class);
	
	public static void main(String[] args) {
		LOGGER.debug("main");
		
		// 상대경로
		File addressFile = new File("data/day03/address.txt");
		// 절대경로
		//File addressFile = new File("D:\\git\\OJT\\data\\day03"); 
		LOGGER.debug("addressFile exists:" + addressFile.exists());

		// Stream 단위 입출력
		//byte[] data = new byte[1024];
		byte[] data = new byte[10];
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(addressFile);
			LOGGER.debug("inputStream 생성 완료");
			
			int readNum = inputStream.read(data);
			LOGGER.debug(String.format("[%d]%s", readNum, new String(data)));
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} 
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException ioe) {
					LOGGER.error(ioe.getMessage(), ioe);
				}
			}
		}

		try (InputStream inputStream2 = new FileInputStream(addressFile)) {
			int readNum = inputStream2.read(data);
			LOGGER.debug(String.format("[%d]%s", readNum, new String(data)));
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} 
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		char[] charData = new char[5];
		try (Reader reader = new FileReader(addressFile)) {
			int readNum = reader.read(charData);
			LOGGER.debug(String.format("+++ [%d]%s", readNum, new String(charData)));
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		List<Person> personList = new ArrayList<Person>();
		
		// line  단위로 읽기 위해 BufferedReader 사용
		try (BufferedReader reader = new BufferedReader(new FileReader(addressFile))) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("+++ [%s]", readLine));
				
				String[] splitData = readLine.split(",");
				if (splitData.length >= 6) {
					try {
						Person onePerson = new Person();
	
						onePerson.setName(splitData[1]);
						onePerson.setGender(Gender.toGender(splitData[2]));
						onePerson.setAge(Integer.parseInt(splitData[3]));
						onePerson.setPhone(splitData[4]);
						onePerson.setAddress(splitData[5]);
						
						personList.add(onePerson);
						
						LOGGER.debug(String.format("+++ onePerson:%s", onePerson));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		File outputFile = new File("data/day03/address.dat");
		// Person 객체 단위로 write
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
			for (Person onePerson : personList) {
				oos.writeObject(onePerson);
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputFile))) {
			Object o;
			while ((o = ois.readObject()) != null) {
				LOGGER.debug("--- oneObject:" + o);
				
				if (o instanceof Person) {
					Person p = (Person)o;
				}
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (EOFException eofe) {
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} 
		catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}

		try (BufferedReader reader = Files.newBufferedReader(Paths.get("data/day03/address.txt"), StandardCharsets.UTF_8)) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("=== [%s]", readLine));
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
}
