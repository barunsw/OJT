package com.barunsw.ojt.iwkim.day05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonInfoInterface;
import com.barunsw.ojt.iwkim.common.PersonInfo;

public class TextFilePersonInfoImpl implements PersonInfoInterface{

private static Logger LOGGER = LogManager.getLogger(ObjectFilePersonInfoImpl.class);
	
	private final String PERSON_INFO_FILE = "data/iwkim/personInfo2.csv";
	
	private List<PersonInfo> personList;
	
	BufferedReader reader = null; // 다형성을 이용하여 Reader타입의 객체를 생성하면 자식클래스의 메서드인 readLine을 사용할 수 없으므로 BufferReader를 사용했다.
	BufferedWriter writer = null; 
	Boolean isExistName;
	String name;
	String gender;
	String birth;
	String email;
	String regDate;
	String updateDate;
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	File file = new File(PERSON_INFO_FILE);
	
	public TextFilePersonInfoImpl() {
		try {
			loadFile();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	//파일로부터 데이터 로딩
	private void loadFile() throws Exception {
		
		reader = new BufferedReader(new FileReader(PERSON_INFO_FILE));
		personList = new ArrayList<>();
		String readLine = null;
		while ( (readLine = reader.readLine()) != null ) {
			LOGGER.info(String.format("line : [%s]", readLine));
			String[] splitData = readLine.trim().split(",");
			PersonInfo person = new PersonInfo();
			person.setName(splitData[1]);
			person.setGender(splitData[2]);
			person.setBirth(splitData[3]);
			person.setEmail(splitData[4]);
			person.setRegDate(splitData[5]);
			person.setUpdateDate(splitData[6]);
			
			personList.add(person);
		}
		reader.close();
		
	}
	
	//파일에 출력하는 메서드 
	private void writeFile() throws Exception{
		writer = new BufferedWriter(new FileWriter(PERSON_INFO_FILE));
		for (PersonInfo person : personList) {
			name = person.getName();
			gender = person.getGender();
			birth = person.getBirth();
			email = person.getEmail();
			regDate = person.getRegDate();
			updateDate = person.getUpdateDate();
			writer.write(String.format("%s, %s, %s, %s, %s, %s", name, gender, birth, email, regDate, updateDate));
			writer.flush();
		}
		writer.close();
	}
	
	@Override
	public PersonInfo select(String name) throws Exception {
		// personList에서 name과 일치하는 데이터를 찾는다.
		for (PersonInfo person : personList) {
			if (person.getName().equals(name)) {
				PersonInfo onePerson = new PersonInfo();
				onePerson.setName(name);
				onePerson.setGender(person.getGender());
				onePerson.setBirth(person.getBirth());
				onePerson.setEmail(person.getEmail());
				onePerson.setRegDate(person.getRegDate());
				onePerson.setUpdateDate(person.getUpdateDate());
				return person;
			}
		}
		System.out.println("검색 결과가 없습니다.");
		return null;
	}

	@Override
	public int insertPerson(PersonInfo param) throws Exception {
		if (param != null) {
			// personList에 param을 추가한다.
			personList.add(param);
			// personList 내용 전체를 파일에 쓴다.
			writeFile();
			return 1;
		}
		return 0;
	}

	@Override
	public int updatePerson(PersonInfo param) throws Exception {
		// personList에서 param과 일치하는 데이터를 찾아 수정한다.
		for (PersonInfo person : personList) {
			if (person.getName().equals(param.getName())) {
				person.setName(param.getName());
				person.setGender(param.getGender());
				person.setBirth(param.getBirth());
				person.setEmail(param.getEmail());
				person.setRegDate(param.getRegDate());
				person.setUpdateDate(date.format(new Date()));
			}
		}
		// 파일에 쓴다.
		writeFile();
		return 1;
	}

	@Override
	public int deletePerson(String name) throws Exception {
		// personList에서 param과 일치하는 데이터를 찾아 삭제한다.
		int count = 0;
		for (PersonInfo person : personList) {
			count++;
			if (person.getName().equals(person.getName())) {
				personList.remove(count);
			}
		}
		// 파일에 쓴다.
		writeFile();
		return 1;
	}
	
	
	@Override
	public boolean isExistName(String name) {
		
		if ( personList == null ) {
			return false;
		}
		
		for (PersonInfo person : personList) {
			if (person.getName().equals(name)) {
				return true;
			}
		} 
		return false;
	}
	

}
