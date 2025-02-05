package com.barunsw.ojt.sjcha.day08;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day08.AddressVo;

public class FileAddressBookImplement implements AddressBookInterface {
	private List<AddressVo> personList = new ArrayList<>();
	
	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImplement.class);
	
	File outputFile = new File("data/day08/sjcha/address.dat");
	
	// 주소록 조회
	@Override
	public List<AddressVo> selectAddressList(AddressVo person) {
		return personList;
	}
		
	// 주소록 데이터 추가 - 추가 버튼 클릭
	public int insertAddress(AddressVo person) throws Exception {
		personList.add(person);
		output(personList);
		input();
		return 0;
	}
		
	// 주소록 데이터 업데이트 - 변경 버튼 클릭
	public int updateAddress(AddressVo person) throws Exception {
		//personList.set(selectedRow, person);		
		
		int addressBookCount = personList.size();
		for (int i = 0; i < addressBookCount; i++) {
			AddressVo oneAddress = personList.get(i);
			if (oneAddress != null && oneAddress.getName().equals(person.getName())) {
				personList.set(i, person);
			}
		}
		output(personList);
		input();
		return 0;
	}
		
	// 주소록 데이터 삭제 - 오른쪽 마우스 버튼 클릭
	public int deleteAddress(AddressVo person) throws Exception {
		
		int addressBookCount = personList.size();
		int deleteIndex = -1;
		for (int i = 0; i < addressBookCount; i++) {
			AddressVo oneAddress = personList.get(i);
			if (oneAddress != null && oneAddress.getName().equals(person.getName())) {
				deleteIndex = i;
				LOGGER.debug(deleteIndex);
				break;
			}
		}
		
		if (deleteIndex >= 0) {
			personList.remove(deleteIndex);
		}
		
		output(personList);
		input();
		return 0;
	}
	
	public void output (List<AddressVo> personList) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputFile))) {
			for (AddressVo person : personList) {
				// person 객체를 outputStream에 쓰기
				outputStream.writeObject(person);
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
	
	public void input () {
		try (ObjectInputStream objectinputStream = new ObjectInputStream(new FileInputStream(outputFile))) {
			Object object;
			while ((object = objectinputStream.readObject()) != null) {
				LOGGER.debug("oneObject : " + object);
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		// 에러메시지를 작성하면 안된다.
		catch (EOFException eof) {
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} 
		catch (ClassNotFoundException classnotfound) {
			LOGGER.error(classnotfound.getMessage(), classnotfound);
		}
	}
}
