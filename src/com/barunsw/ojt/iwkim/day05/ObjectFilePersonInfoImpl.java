package com.barunsw.ojt.iwkim.day05;

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

import com.barunsw.ojt.iwkim.common.AddressBookInterface;
import com.barunsw.ojt.iwkim.common.PersonInfo;

public class ObjectFileAddressBookImpl implements AddressBookInterface{
	private static Logger LOGGER = LogManager.getLogger(ObjectFileAddressBookImpl.class);
	
	private final String ADDRESS_BOOK_FILE = "data/iwkim/addressbook.txt";
	
	private List<PersonInfo> personList;
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Boolean isExistName;
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ObjectFileAddressBookImpl() {
		try {
			loadFile();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	//파일로부터 데이터 로딩
	private void loadFile() throws Exception {
		ois = new ObjectInputStream(new FileInputStream(ADDRESS_BOOK_FILE));
		personList = new ArrayList<>();
		Object o;
		while ( (o = ois.readObject()) != null ) {
			LOGGER.info(" oneObject : " + o);
			personList.add((PersonInfo)o);
		}
		ois.close();
	}
	
	//파일에 출력하는 메서드 
	private void writeFile() throws Exception{
		oos = new ObjectOutputStream(new FileOutputStream(ADDRESS_BOOK_FILE));
		for (PersonInfo person : personList) {
			oos.writeObject(person);
		}
		oos.close();
	}
	
	
	
	
	
	@Override
	public List<PersonInfo> selectList(PersonInfo param) throws Exception {
		// personList를 반환한다.
		return personList;
	}

	@Override
	public int insertPerson(PersonInfo param) throws Exception {
		// 이미 있는지 확인한다.
		isExistName = isExistName(param);
		if (!isExistName) {
			System.out.println("중복된 이름입니다. 다시입력해주세요");
			return 0;
		}
		// personList에 param을 추가한다.
		personList.add(param);
		// personList 내용 전체를 파일에 쓴다.
		writeFile();
		return 1;
	}

	@Override
	public int updatePerson(PersonInfo param) throws Exception {
		// 존재여부를 확인한다.
		isExistName = isExistName(param);
		if (!isExistName) {
			System.out.println("해당 이름에 대한 정보가 존재하지 않습니다. 다시 입력해 주세요.");
			return 0;
		}
		// personList에서 param과 일치하는 데이터를 찾아 수정한다.
		for (PersonInfo person : personList) {
			if (person.getName().equals(param.getName())) {
				person.setName(param.getName());
				person.setGender(param.getGender());
				person.setBirth(param.getBirth());
				person.setEmail(param.getBirth());
				person.setUpdateDate(date.format(new Date()));
			}
		}
		// 파일에 쓴다.
		writeFile();
		
		return 1;
	}

	private Boolean isExistName(PersonInfo param) {
		for (PersonInfo person : personList) {
			if (person.getName().equals(param.getName())) {
				return true;
			}
		} 
		return false;
	}

	@Override
	public int deletePerson(PersonInfo param) throws Exception {
		// 존재여부를 확인한다.
		Boolean flag = isExistName(param);
		if (!isExistName) {
			System.out.println("해당 이름에 대한 정보가 존재하지 않습니다. 다시 입력해 주세요.");
			return 0;
		}
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
	
	
	
	
	
	

}















