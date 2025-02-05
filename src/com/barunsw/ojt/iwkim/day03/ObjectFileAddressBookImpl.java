//package com.barunsw.ojt.iwkim.day03;
//
//import java.util.List;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.barunsw.ojt.iwkim.common.AddressBookInterface;
//import com.barunsw.ojt.iwkim.common.Person;
//
//public class ObjectFileAddressBookImpl implements AddressBookInterface {
//	private static Logger LOGGER = LogManager.getLogger(ObjectFileAddressBookImpl.class);
//	
//	private final String ADDRESS_BOOK_FILE = "addressbook.dat";
//	
//	private List<Person> personList;
//	
//	public ObjectFileAddressBookImpl() {
//		try {
//			loadFile();
//		}
//		catch (Exception ex) {
//			LOGGER.error(ex.getMessage(), ex);
//		}
//	}
//	
//	private void loadFile() throws Exception {
//		// 파일로 부터 데이터 로딩
//	}
//	
//	private void writeFile() throws Exception {
//		
//	}
//	
//	@Override
//	public List<Person> selectList(Person param) throws Exception {
//		// TODO Auto-generated method stub
//		// 1. personList를 반환한다.
//		return personList;
//	}
//
//	@Override
//	public int insertPerson(Person param) throws Exception {
//		// TODO Auto-generated method stub
//		// 0. 이미 있는지 확인한다.
//		// 1. personList에 param을 추가한다.
//		// 2. personList 내용 전체를 파일에 쓴다.
//		
//		return 0;
//	}
//
//	@Override
//	public int updatePerson(Person param) throws Exception {
//		// TODO Auto-generated method stub
//		// 0. 존재여부를 확인한다.
//		// 1. personList에서 param과 일치하는 데이터를 찾아 수정한다.
//		// 2. 파일에 쓴다.
//		
//		return 0;
//	}
//
//	@Override
//	public int deletePerson(Person param) throws Exception {
//		// TODO Auto-generated method stub
//		// 0. 존재여부를 확인한다.
//		// 1. personList에서 param과 일치하는 데이터를 찾아 삭제한다.
//		// 2. 파일에 쓴다.
//		
//		return 0;
//	}
//
//}
