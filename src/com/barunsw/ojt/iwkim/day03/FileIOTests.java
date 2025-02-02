package com.barunsw.ojt.iwkim.day03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.Person;

public class FileIOTests {
	
	private static Logger LOGGER = LogManager.getLogger(FileIOTests.class);
	
	public static void main(String[] args) {
		
		File pathFile = new File("data/day03/iwKim");
		
		 if (pathFile.mkdirs()) { 
			 LOGGER.info("디렉토리 생성 성공"); 
		 }
		 else {
			 LOGGER.info("디렉토리 생성 실패"); 
		 }
		
		File exFile = new File("data/day03/iwkim/address.txt");
		
		//전달된 경로가 디렉토리인지 검사
		if (exFile.isDirectory()) {
			LOGGER.info("디렉토리 존재");
		}
		else {
			LOGGER.info("디렉토리 존재");
		}
		
		//전달된 경로가 숨김형태인지 검사
		LOGGER.info("전달된 경로가 숨김형태인지 검사 : " + exFile.isHidden());
		
		//절대경로값 추출
		LOGGER.info("절대 경로 값 : " + exFile.getAbsolutePath());
		
		//생성자에 전달된 파일이나 디렉터리가 물리적으로 존재하는지를 검사
		LOGGER.info("존재여부 : " + exFile.exists());
		
		//파일만들기
		try {
			exFile.createNewFile();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("전달된 경로가 파일인지 검사 : " + exFile.isFile());
		LOGGER.info(exFile.getName());
		LOGGER.info(exFile.getParent());
		
		LOGGER.info("====================================================");
		//이제 파일은 생성을 하였고 IO를 테스트해보자
		// Inputstream과 outputStream은 바이트 기반이다.
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		//보조스트림은 생성시 인자값으로 기반스트림 객체를 넣어서 객체를 생성한다.
		try {
			inputStream = new FileInputStream(exFile);
			LOGGER.info("InputStream 객체 생성");
			
			// InputStream에서 read메서드는 바이트배열 형태로 읽어 들이기 때문에
			// 바이트 배열 생성
			byte[] data = new byte[1024];
			int readNumber = inputStream.read(data); // data에 읽은 데이터가 저장된다.
			//new String(byte[])은 들어간 바이트배열을 디코딩하여 새로운 String 객체를 만들게 된다.  
			LOGGER.info(String.format("[%d]Byte msg : %s", readNumber, new String(data)));
			
			// 지정된 파일객체가 나타내는 파일에 쓸 파일 출력 스트림을 만든다.
			// 파일이 있지만 디렉토리인 경우, 존재하지 않지만 작성할 수 없거나 다른이유로 열 수 없는경우 FileNotFoundException발생
			File file1 = new File("data/day03/iwkim/address1.txt");
			outputStream = new FileOutputStream(file1); // 여기서 address1.txt파일 생성함과 동시에 바이트배을 보낼 통로를 열게됨
			outputStream.write(data); // 바이트 배열의 내용을 문자로 바꿔서 파일에 출력한다.
		
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.info(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.info(ioe.getMessage(), ioe);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				}
			}
			if(outputStream != null) {
				try {
					outputStream.close();
				}
				catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				}
			}
		}
		
		LOGGER.info("====================================================");
		
		//Try-with-resources를 사용하여 자원을 쉽게 해제할 수 있다.
		try (InputStream testStream = new FileInputStream(exFile)){ // 주어진 File객체가 가리키는 파일을 바이트 스트림으로 읽기 위한 FileInputStream객체를 생성
			byte[] data = new byte[100];
			int readNumber = testStream.read(data);
			LOGGER.info(String.format("[%d]Byte msg : %s", readNumber, new String(data)));
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.info(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.info(ioe.getMessage(), ioe);
		}
		
		// Reader와 Writer는 문자기반 입출력 스트림이다.
		try (Reader reader = new FileReader(exFile)) {
			char[] charData = new char[50];
			int readNum = reader.read(charData);
			LOGGER.info(String.format("[%d]Char msg : %s ", readNum, new String(charData) ));
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.info(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.info(ioe.getMessage(), ioe);
		}
		
		LOGGER.info("====================================================");
		//여기서 보조스트림인 BufferedReader를 사용하면 줄 별로 읽을 수 있다.
		// 먼저 직렬화가 가능한 클래스로 리스트를 생성하자
		List<Person> list = new ArrayList<>();
		BufferedReader reader = null; // 다형성을 이용하여 Reader타입의 객체를 생성하면 자식클래스의 메서드인 readLine을 사용할 수 없으므로 BufferReader를 사용했다.
		BufferedWriter writer = null; 
		
		try {
			reader = new BufferedReader(new FileReader(exFile)); 
			writer = new BufferedWriter(new FileWriter(
					new File("data/day03/iwkim/address2")));
			String readLine = null;
			while ((readLine = reader.readLine()) != null) { // 한줄씩 읽어들여서 null이 아닐때까지 반복
				LOGGER.info(String.format("line : [%s]", readLine));
				String[] splitData = readLine.split(",");
				//개행기능을 추가
				// 현재 저장되어있는내용 전송하고 버퍼를 비우는 기능 추가
				writer.write(readLine + "\n");
				writer.flush(); // close하기전에 버퍼에 있는 내용을 출력하고 버퍼를 비워야하므로 flush()메서드 사용
				
				if (splitData.length > 5) {
					Person onePerson = new Person();
					try {
						onePerson.setName(splitData[1]);
						onePerson.setGender(splitData[2]);
						onePerson.setAge(Integer.parseInt(splitData[3]));
						onePerson.setPhone(splitData[4]);
						onePerson.setAddress(splitData[5]);
						
						list.add(onePerson);
					}
					catch (Exception e) {
						LOGGER.info(e.getMessage(), e);
					} 
				}
			}			
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.info(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.info(ioe.getMessage(), ioe);
		}
		finally {
			if (reader != null ) {
				try {
					reader.close();
				}
				catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				}
			}
			if (writer != null) {
				try {
					writer.close();
				}
				catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				}
			}
		}
		
		LOGGER.info("====================================================");
		
		//이제 객체 단위로 IO를 해보자
		File objFile = new File("data/day03/iwkim/address5.dat");
		
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(objFile))) {
			for (Person onePerson : list) {
				oos.writeObject(onePerson);
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objFile))) {
			Object o;
			while ((o = ois.readObject()) != null) {
				LOGGER.info("--- oneObject:" + o);
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

		try (BufferedReader reader1 = Files.newBufferedReader(Paths.get("data/day03/iwkim/address.txt"), StandardCharsets.UTF_8)) {
			String readLine = null;
			while ((readLine = reader1.readLine()) != null) {
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













