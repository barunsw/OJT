package com.barunsw.ojt.phs.day03;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IOTest {

	private static final Logger LOGGER = LogManager.getLogger(IOTest.class);
	
	public static void main(String[] args) {
		
		//상대경로로 지정함.
		//맨앞에  'D://' 부터 전부다 적어줄 경우 절대경로임
		File file = new File("data/day03/phs/address.txt");
		
		try {
			//파일을 생성하는데 만들어질경우 true, 이미존재할경우 false 반환
			if(file.createNewFile()) {
				LOGGER.debug("파일이 생성 되었습니다. 파일이름: " + file.getName());
			}else {
				LOGGER.debug("파일이 이미 존재합니다. 파일이름: " + file.getName());
			}
		} catch (IOException e) {
			LOGGER.debug(e);
		}
		
//		=================================================================
		
		//InputStream은 Byte 단위로 읽기때문에 Byte배열 선언
		byte[] data = new byte[10]; 
		
		//Stream 선언을 try 오른쪽 괄호 안에 넣어주면 finally를 이용해 close()를 사용할 필요가없음
		//코드가 줄어들어 간편해짐
		try (FileInputStream fis = new FileInputStream(file)){
			fis.read(data);
			LOGGER.debug(String.format("%s", new String(data)));
		}catch(Exception e) {
			LOGGER.debug(e);
		}
		
//		=================================================================
		
		//reader는 Char단위로 읽기 때문에 char배열 선언
		char[] data2 = new char[1024];
		
		try (FileReader fr = new FileReader(file)){
			fr.read(data2);
			LOGGER.debug(String.format("%s", new String(data2)));
		}catch(Exception e) {
			LOGGER.debug(e);
		}
		
//		=================================================================
		
		List<Person> personList = new ArrayList<Person>();
		
		//file 내용을 한줄씩 읽기 위해 BufferedReader 사용
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String readLine = null;
			
			//한줄씩 읽어서 readLine에 집어넣고 값이 없을때까지 반복
			while((readLine = br.readLine()) != null) {
				LOGGER.debug(readLine);
				
				//한줄 불러서와서 String배열로 만듬
				String[] lineData = readLine.split(",");
				
				//person객체 만듬
				Person onePerson = new Person();
				onePerson.setName(lineData[1]);
				onePerson.setGender(lineData[2]);
				onePerson.setAge(lineData[3]);
				onePerson.setPhone(lineData[4]);
				onePerson.setAddress(lineData[5]);
				
				//명단에 사람추가
				personList.add(onePerson);
				
				LOGGER.debug(String.format("+++ onePerson:%s", onePerson));
			}
		}catch(Exception e) {
			LOGGER.debug(e);
		}
		
//		=================================================================
		
		File outputFile = new File("data/day03/phs/address.dat");
		
		try {
			//dat 파일이 존재하지않을경우 만듬
			if(outputFile.createNewFile()) {
				LOGGER.debug("파일이 생성 되었습니다. 파일이름: " + outputFile.getName());
			}else {
				LOGGER.debug("파일이 이미 존재합니다. 파일이름: " + outputFile.getName());
			}
		} catch (IOException e) {
			LOGGER.debug(e);
		}
		
		//personList에 들어있는 person객체들을 dat파일에 전부다 쓰기
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))){
			for(Person p:personList) {
				LOGGER.debug("(dat에 쓰기) -> " + p);
				oos.writeObject(p);
			}
		}catch(Exception e) {
			LOGGER.debug(e);
		}
		
//		=================================================================
		
		//dat파일에 있는 데이터 불러오기
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputFile))){
			Object obj = null;
			while((obj = ois.readObject()) != null) {
				LOGGER.debug("dat파일에서 불러옴 -> " + obj);
			}
		}catch(Exception e) {
			LOGGER.debug(e +" 불러올 데이터없음");
		}
		
//		=================================================================
		
		FileInputStream fis = null;
		try {
			new FileInputStream(file);
			//...내용생략
		}catch(IOException e) {
			LOGGER.debug(e);
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				LOGGER.debug(e);
			}
		}
		
		
		
		
		
	}
}
