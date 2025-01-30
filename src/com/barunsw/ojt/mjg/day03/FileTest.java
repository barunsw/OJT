package com.barunsw.ojt.mjg.day03;

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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;

public class FileTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileTest.class);
	
	public static void main(String[] args) {
//		LOGGER.debug("main");
		
		// 절대경로
//		File addressAbsoluteFile = new File("D:\\git\\OJT\\data\\day03"); 
		// 상대경로
//		File addressFile = new File("data/day03/address.txt");
//		File addressFileNotExists = new File("data/day03/address123.txt");
		File addressDirectoryNotExists = new File("data/day11");
		File addressDirectoryExists = new File("data/day03");
		
		File root = new File("C:\\");
		File addressFile = new File("data/day03/address.txt");
		
		LOGGER.debug(String.valueOf(addressFile.lastModified()));
		LOGGER.debug(String.valueOf(root.length()));
		
        long lastModifiedMillis = addressFile.lastModified();

        if (lastModifiedMillis == 0L) {
            System.out.println("파일이 존재하지 않거나 오류가 발생했습니다.");
        } else {
            // UTC 시간 기준 Instant 생성
            Instant instant = Instant.ofEpochMilli(lastModifiedMillis);

            // 한국 시간대(KST)로 변환
            LocalDateTime lastModifiedKST = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));

            // 출력 포맷 지정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 포맷된 한국 시간 출력
            System.out.println("마지막 수정 시간 (KST): " + lastModifiedKST.format(formatter));
        }
		
//		LOGGER.debug(String.valueOf(addressFile.getParent()));
//		LOGGER.debug(String.valueOf(root.getParent()));
//		LOGGER.debug(String.valueOf(addressFile.getName()));
//		LOGGER.debug(String.valueOf(addressFile.isDirectory()));
//		LOGGER.debug(String.valueOf(addressDirectoryNotExists.isDirectory()));
//		LOGGER.debug(String.valueOf(addressDirectoryExists.isDirectory()));
		
		
//		LOGGER.debug(String.valueOf(addressFile.isFile()));
//		LOGGER.debug(String.valueOf(addressFileNotExists.isFile()));
		
//		LOGGER.debug("addressFile exists:" + addressFile.exists());
//		LOGGER.debug("addressFileNotExists exists:" + addressFileNotExists.exists());
//		LOGGER.debug("addressAbsoluteFile exists:" + addressAbsoluteFile.exists());
//		LOGGER.debug("addressFile AbsolutePath:" + addressFile.getAbsolutePath());
//		LOGGER.debug(addressFile.getAbsolutePath());
//		LOGGER.debug(addressAbsoluteFile.getAbsolutePath());
		
		
		
/*
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
*/
        
        List<AddressBook> addressBooks = new ArrayList<AddressBook>();
        
        // 파일 객체 생성 (직렬화된 데이터 저장용 파일)
// 		List<AddressBook> addressBookList = new ArrayList<AddressBook>();
 		
 		File addressBook = new File("data/day03/addressBook.dat");
 		// AddressBook 객체 단위로 write
 		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(addressBook))) {
 			for (AddressBook onePerson : addressBooks) {
 				oos.writeObject(onePerson);
 			}
 		}
 		catch (FileNotFoundException fnfe) {
 			LOGGER.error(fnfe.getMessage(), fnfe);
 		}
 		catch (IOException ioe) {
 			LOGGER.error(ioe.getMessage(), ioe);
 		}
 		
        // 직렬화된 AddressBook 객체를 파일에서 읽어옴
 		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(addressBook))) {
 			Object o;
 			while ((o = ois.readObject()) != null) {
 				LOGGER.debug("--- oneObject:" + o);

 				if (o instanceof AddressBook) {
 					AddressBook p = (AddressBook) o;
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

 		try (BufferedReader reader = Files.newBufferedReader(Paths.get("data/day03/addressBook.txt"),
 				StandardCharsets.UTF_8)) {
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
