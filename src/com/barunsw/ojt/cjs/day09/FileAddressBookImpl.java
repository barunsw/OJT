package com.barunsw.ojt.cjs.day09;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.vo.AddressVo;

public class FileAddressBookImpl implements AddressBookInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
	private File file = new File("data/day03/cjs/address.dat");
	private List<AddressVo> addressList = new ArrayList<AddressVo>(); // 리스트 객체를 먼저 선언해놓고 저장한다.

	public FileAddressBookImpl() {
		// 1. AddressBookApp.properties로 부터 파일 정보를 가져온다. 전역의 addressFilePath에 담는다.
		// 2. loadFile을 호출한다.
	}
	
	private void loadFile() {
		// 최초 한번 파일(전역 addressFilePath)의 내용을 읽어와(loadFile 메소드 호출) addressList에 담는다.
		// filePath에서 ObjectInputStream으로 주소 정보를 가져온다.
	}
	
	private void saveFile() {
		// addressList의 내용을 전역 addressFilePath에 ObjectOutputStream으로 저장한다.
	}
	
	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		// 단순히 addressList를 반환한다.
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			Object obj;
			addressList.clear(); //클리어안해주면 계속 반복해서 동일내용이 계속 추가됨
			while ((obj = ois.readObject()) != null) {
				AddressVo address = (AddressVo) obj;
				addressList.add(address);
				LOGGER.debug(obj + "");
			}
		} catch (Exception e) {
			return addressList; //ObjectInputStream은 모든 문자를 읽고 나서 -1을 리턴하지않아 EOFException이 발생한다, 그래서 예외갈 발생할 때 리턴값에 처리를 해줌
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		addressList.add(addressVo);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			for (AddressVo address : addressList) {
				oos.writeObject(address);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		// saveFile을 호출한다.
		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getName().equals(addressVo.getName())) // 리스트에 있는 i번째 행에 있는 이름이랑 같은지 확인
				addressList.set(i, addressVo); // 리스트 i번째에 있는 객체 바꿔줌
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			for (AddressVo address : addressList) {
				oos.writeObject(address);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		// saveFile을 호출한다.
		
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getName().equals(addressVo.getName())) {
				addressList.remove(i);
				break;
			}
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			for (AddressVo address : addressList) {
				oos.writeObject(address);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		// saveFile을 호출한다.
		
		return 0;
	}
}
