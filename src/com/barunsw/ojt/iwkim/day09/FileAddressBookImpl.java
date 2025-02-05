package com.barunsw.ojt.iwkim.day09;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.AddressBookInterface;
import com.barunsw.ojt.iwkim.common.PersonVO;

public class FileAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImpl.class);
	
	private final String ADDRESS_BOOK_FILE = "data/iwkim/addressBook.dat";
	
	private List<PersonVO> personList;
	
	//생성자로 file 로딩을 진행하자! crud는 로드된 리스트만 조작하면 된다!
	public FileAddressBookImpl() {
		try {
			loadFile();
		}
		catch (EOFException eofe) {
			LOGGER.info("더 이상 읽을 내용이 없습니다.");
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void loadFile() throws Exception {
		File file = new File(ADDRESS_BOOK_FILE);
		personList = new ArrayList<>();
		if (!file.createNewFile()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ADDRESS_BOOK_FILE))) {
				LOGGER.info("파일로드 시작!");
	
				Object o;
				while ((o = ois.readObject()) != null) {
					personList.add((PersonVO)o);
				}
			}
		}
	}
	
	private void writeFile() throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ADDRESS_BOOK_FILE))) {
			LOGGER.info("파일출력 시작!");
			
			for (PersonVO person : personList) {
				oos.writeObject(person);
			}
		}
	}
	
	@Override
	public List<PersonVO> selectList() throws Exception {
		return personList;
	}

	@Override
	public int insertPerson(PersonVO param) throws Exception {
		personList.add(param);
		writeFile();
		return 1;
	}

	@Override
	public int updatePerson(PersonVO param) throws Exception {
		for (PersonVO person : personList) {
			if (person.getName().equals(param.getName())) {
				person.setAge(param.getAge());
				person.setGender(param.getGender());
				person.setPhone(param.getPhone());
				person.setAddress(param.getAddress());
			}
		}
		writeFile();
		return 1;
	}

	@Override
	public int deletePerson(String name) throws Exception {
//	    for문 안에 list를 넣고 돌리는 도중 remove를 호출하면 
//	    기존 index 값과 remove 호출로 인해 줄어든 size 값이 맞지 않기 때문에 예외가 발생한다. 
//		int count = 0;
//		for (PersonVO person : personList) {
//			if (person.getName().equals(name)) {
//				personList.remove(count++);
//			}
//		}
//      위와같은 코드는 반복자를 사용해서 처리하자
		Iterator<PersonVO> iterator = personList.iterator();
		while (iterator.hasNext()) {
			 PersonVO person = iterator.next();
			 if (person.getName().equals(name)) {
				 iterator.remove();
			 }
			 writeFile();
			 return 1;
		}
		return 0;
	}
}
