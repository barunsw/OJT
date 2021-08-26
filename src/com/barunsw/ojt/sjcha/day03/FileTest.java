package com.barunsw.ojt.sjcha.day03;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.day03.Person;

public class FileTest {
	public static final Logger LOGGER = LogManager.getLogger(FileTest.class);
	
	public static void main(String args[]) {
		LOGGER.debug("main");
		
		// File 객체 생성자를 통해 파일 경로 지정
		File addressFile = new File("data/day03/sjcha/address.txt");
		LOGGER.debug("File exist? " + addressFile.exists());
		
		// Byte 단위 입출력 InputStream, OutputStream
		// 한글은 유니코드이기 때문에 한 글자 당 3byte이다. 
		byte[] data = new byte[20];
		InputStream inputStream = null;
		
		// InputStream은 Exception에서 closeable이 있으므로 close를 반드시 해야한다.
		// try와 catch문을 사용해서 예외 처리를 해준다.
		try {
			// 파일로부터 읽을 것이기 때문에 FileInputStream으로 instance
			inputStream = new FileInputStream(addressFile);
			LOGGER.debug("inputStream 생성");
			
			// inputStream에서 데이터의 20 바이트 단위로 읽는다. ??
			int readInput = inputStream.read(data);
			LOGGER.debug(String.format("byte 단위 입출력 [%d] %s", readInput, new String(data)));
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		// inputStream을 close하기 위함
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
		
		// implements autocloseable을 사용한 방법
		try (InputStream inputStream2 = new FileInputStream(addressFile)) {
			LOGGER.debug("inputStream2 생성");
			
			int readInput2 = inputStream2.read(data);
			LOGGER.debug(String.format("byte 단위 입출력 [%d] %s", readInput2, new String(data)));
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	
		// 문자 스트림 단위 입출력 Reader, Writer
		// 5개의 문자 공간 생성
		char[] charData = new char[5];
		try (Reader reader = new FileReader(addressFile)) {
			int readNum = reader.read(charData);
			LOGGER.debug(String.format("문자 단위 입출력 [%d] %s", readNum, new String(charData)));
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		// Person 객체의 리스트 생성
		List<Person> personList = new ArrayList<Person>();
		
		// file을 line단위로 읽는다.
		// file이 1,배수현,남,45,010-9047-3817,경기도 수원시로 이루어져 있어 ,로 split 하여 정보를 Person객체에 넣는다. 
		try (BufferedReader reader = new BufferedReader(new FileReader(addressFile))) {
			// file의 Line을 넣을 변수
			String readLine = null;
			// 다음 라인의 값이 없을 때까지 반복한다.
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("[%s]", readLine));
				
				// 라인을 ,로 split 한다.
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
						
						LOGGER.debug(String.format("onePerson : %s", onePerson));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
				
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		// output할 파일 설정. 
		File outputFile = new File("data/day03/sjcha/address.dat");
		
		// Object는 Reader, Writer가 없어서 Stream을 사용해서 write, read 해야한다. 
		// Person 객체 단위로 write
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputFile))) {
			for (Person person : personList) {
				// person 객체를 outputStream에 쓰기
				outputStream.writeObject(person);
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		// 객체를 읽어온다. 
		try (ObjectInputStream objectinputStream = new ObjectInputStream(new FileInputStream(outputFile))) {
			Object object;
			while ((object = objectinputStream.readObject()) != null) {
				LOGGER.debug("oneObject : " + object);
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		// 에러메시지를 작성하면 안된다.
		catch (EOFException eof) {
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} 
		catch (ClassNotFoundException classnotfound) {
			LOGGER.error(classnotfound.getMessage(), classnotfound);
		}

		// java.nio 새로운 io 패키지를 사용함 
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("data/day03/sjcha/address.txt"), StandardCharsets.UTF_8)) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("[%s]", readLine));
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
}
