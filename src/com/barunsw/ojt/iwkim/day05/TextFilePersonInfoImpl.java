package com.barunsw.ojt.iwkim.day05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.DateUtil;
import com.barunsw.ojt.iwkim.common.PersonInfo;
import com.barunsw.ojt.iwkim.common.PersonInfoInterface;

public class TextFilePersonInfoImpl implements PersonInfoInterface {
	private static Logger LOGGER = LogManager.getLogger(ObjectFilePersonInfoImpl.class);
	
	private final String PERSON_INFO_FILE = "data/iwkim/personInfo2.csv";
	
	private List<PersonInfo> personList;
	
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
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File(PERSON_INFO_FILE)));
			personList = new ArrayList<>();
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.info(String.format("line : [%s]", readLine));
				
				String[] splitData = readLine.replace(" ","").split(",");
				for (String str : splitData) {
					LOGGER.info("str : " + str);
				}
				PersonInfo person = new PersonInfo();
				person.setName(splitData[0]);
				person.setGender(splitData[1]);
				person.setBirth(splitData[2]);
				person.setEmail(splitData[3]);
				person.setRegDate(splitData[4]);
				person.setUpdateDate(splitData[5]);

				personList.add(person);
			}
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	//파일에 출력하는 메서드 
	private void writeFile() throws Exception {
		/*
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(PERSON_INFO_FILE));
			for (PersonInfo person : personList) {
				String name 		= person.getName();
				String gender 		= person.getGender();
				String birth 		= person.getBirth();
				String email 		= person.getEmail();
				String regDate 		= person.getRegDate();
				String updateDate 	= person.getUpdateDate();
				
				writer.write(String.format("%s, %s, %s, %s, %s, %s", name, gender, birth, email, regDate, updateDate));
				writer.flush();
			}
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
		*/
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(PERSON_INFO_FILE));) {
			for (PersonInfo person : personList) {
				String name 		= person.getName();
				String gender 		= person.getGender();
				String birth 		= person.getBirth();
				String email 		= person.getEmail();
				String regDate 		= person.getRegDate();
				String updateDate 	= person.getUpdateDate();
				
				writer.write(String.format("%s, %s, %s, %s, %s, %s", name, gender, birth, email, regDate, updateDate));
				writer.flush();
			}
		}
	}
	
	@Override
	public PersonInfo select(String name) throws Exception {
		// personList에서 name과 일치하는 데이터를 찾는다.
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
