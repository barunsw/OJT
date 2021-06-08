package com.barunsw.ojt.iwkim.day05;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonInfoInterface;
import com.barunsw.ojt.iwkim.common.DateUtil;
import com.barunsw.ojt.iwkim.common.PersonInfo;

public class ObjectFilePersonInfoImpl implements PersonInfoInterface {
	private static Logger LOGGER = LogManager.getLogger(ObjectFilePersonInfoImpl.class);
	
	private final String PERSON_INFO_FILE = "data/iwkim/personInfo.txt";
	
	private List<PersonInfo> personList;
	
	public ObjectFilePersonInfoImpl() {
		try {
			loadFile();
		}
		catch(EOFException eofe) {
			LOGGER.info("더 이상 읽을 내용이 없습니다.");
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	//파일로부터 데이터 로딩
	private void loadFile() throws Exception {
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(PERSON_INFO_FILE)));
			personList = new ArrayList<>();
			LOGGER.info("personList : " + personList);
			Object o;
			while ((o = ois.readObject()) != null) {
				personList.add((PersonInfo)o);
			}
		}
		finally {
			if (ois != null) {
				ois.close();
			}
		}
	}
	
	//파일에 출력하는 메서드 
	private void writeFile() throws Exception{
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(PERSON_INFO_FILE)));
			for (PersonInfo person : personList) {
				oos.writeObject(person);
			}
		}
		finally {
			if(oos != null) {
				oos.close();
			}
		}
	}
	
	@Override
	public PersonInfo select(String name) throws Exception {
		// personList에서 name과 일치하는 데이터를 찾는다.
		if ( personList == null ) {
			return null;
		}
		
		for (PersonInfo person : personList) {
			if (person.getName().equals(name)) {
				return person;
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
				person.setUpdateDate(DateUtil.DEFAULT_DATE_FORMAT.format(new Date()));
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
			if (person.getName().equals(person.getName())) {
				personList.remove(count++);
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
}