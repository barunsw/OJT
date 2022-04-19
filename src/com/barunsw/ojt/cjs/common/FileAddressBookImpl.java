package com.barunsw.ojt.cjs.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAddressBookImpl implements AddressBookInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
	public static Properties addressBook_Properties = new Properties();
	private List<AddressVo> addressList = new ArrayList<AddressVo>(); // 리스트 객체를 먼저 선언해놓고 저장한다.
	private String addressFilePath;

	public FileAddressBookImpl() throws Exception {
		super();
		Reader reader = Resources.getResourceAsReader("AddressBookApp.properties"); //파일의 클래스패스가져와서
		addressBook_Properties.load(reader); //경로를 읽어 Properites 객체에 저장
		
		Iterator<Object> keySet = addressBook_Properties.keySet().iterator();
		while (keySet.hasNext()) {
			Object key = keySet.next();
			Object value = addressBook_Properties.get(key); 
			LOGGER.debug(String.format("%s = %s", key, value));
		}
		LOGGER.debug("==============================");
		String pathName = FileAddressBookImpl.addressBook_Properties.getProperty("addressFilePath"); //해당 키값의 맞는 value 리턴 
		addressFilePath = pathName;
		loadFile();
		// 1. AddressBookApp.properties로 부터 파일 정보를 가져온다. 전역의 addressFilePath에 담는다.
		// 2. loadFile을 호출한다.
	}

	private void loadFile() throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(addressFilePath))) {
			Object obj;
			while ((obj = ois.readObject()) != null) {
				AddressVo address = (AddressVo) obj;
				addressList.add(address);
				LOGGER.debug(obj + "");
			}
		} 
		catch (Exception e) {
			return;
		}
		// 최초 한번 파일(전역 addressFilePath)의 내용을 읽어와(loadFile 메소드 호출) addressList에 담는다.
		// filePath에서 ObjectInputStream으로 주소 정보를 가져온다.
	}

	private void saveFile() {
		// addressList의 내용을 전역 addressFilePath에 ObjectOutputStream으로 저장한다.
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(addressFilePath))) {
			for (AddressVo address : addressList) {
				oos.writeObject(address);
			}
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		// 단순히 addressList를 반환한다.
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		addressList.add(addressVo);
		saveFile();
		// saveFile을 호출한다.
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getName().equals(addressVo.getName())) // 리스트에 있는 i번째 행에 있는 이름이랑 같은지 확인
				addressList.set(i, addressVo); // 리스트 i번째에 있는 객체 바꿔줌
		}
		saveFile();
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
		saveFile();
		// saveFile을 호출한다.
		return 0;
	}
}
