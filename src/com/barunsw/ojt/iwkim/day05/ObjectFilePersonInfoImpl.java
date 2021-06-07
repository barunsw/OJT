package com.barunsw.ojt.iwkim.day05;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonInfoInterface;
import com.barunsw.ojt.iwkim.common.PersonInfo;

public class ObjectFilePersonInfoImpl implements PersonInfoInterface{
	private static Logger LOGGER = LogManager.getLogger(ObjectFilePersonInfoImpl.class);
	private final String PERSON_INFO_FILE = "data/iwkim/personInfo.txt";
	private List<PersonInfo> personList = new ArrayList<>();
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Boolean isExistName;
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	File file = new File(PERSON_INFO_FILE);
	
	
	public ObjectFilePersonInfoImpl() {
		try {
			loadFile();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	//파일로부터 데이터 로딩
	private void loadFile() throws Exception {
		LOGGER.debug("loadFile");
		ois = new ObjectInputStream(new FileInputStream(file));
		personList = new ArrayList<>();
		LOGGER.info("personList : " + personList);
		Object o;
		while ( (o = ois.readObject()) != null ) {
			LOGGER.info(" oneObject : " + o);
			personList.add((PersonInfo)o);
		}
		ois.close();
	}
	
	//파일에 출력하는 메서드 
	private void writeFile() throws Exception{
		oos = new ObjectOutputStream(new FileOutputStream(PERSON_INFO_FILE));
		for (PersonInfo person : personList) {
			oos.writeObject(person);
		}
		oos.close();
	}
	
	@Override
	public PersonInfo select(String name) throws Exception {
		// personList에서 name과 일치하는 데이터를 찾는다.
		
		if ( personList == null ) {
			System.out.println("검색 결과가 없습니다.");
			return null;
		}
		
		for (PersonInfo person : personList) {
			if (person.getName().equals(name)) {
				PersonInfo onePerson = new PersonInfo();
				onePerson.setName(name);
				onePerson.setGender(person.getGender());
				onePerson.setBirth(person.getBirth());
				onePerson.setEmail(person.getEmail());
				onePerson.setRegDate(person.getRegDate());
				onePerson.setUpdateDate(person.getUpdateDate());
				return onePerson;
			}
		}
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
	public boolean isExistName(String name) throws Exception {
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
	
// NullPointerException이 발생하는 상황
	
//- null 객체에서 method를 호출하는 경우

//- null 객체의 필드에 접근하거나 값을 변경하는 경우

//- null 의 길이를 배열처럼 취하는 경우

//- null 을 throw 하는 경우

//- null 을 통해 동기화 할 경우
}















