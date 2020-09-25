package com.barunsw.ojt.phs.day10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileAddressBookImpl implements AddressBookInterface{

	private static final Logger LOGGER = LogManager.getLogger(DBAddressBookImpl.class);
	private File file = new File("data/phs/addressList.dat");
	private List<AddressVo> addressList = new ArrayList<AddressVo>();
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)) ) {
			Object obj = null;
			addressList.clear();
			while ( (obj = ois.readObject()) != null ) {
					AddressVo onePerson = (AddressVo)obj;
					addressList.add( onePerson );
					LOGGER.debug(obj+"!!!!!!!!!!!!!!!!");
			}
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage()+"오류");
		}
		LOGGER.debug(addressList.size() + ": 불러온크기");
		
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		addressList.add( addressVo );
		int result = writeObject();
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		for ( int i=0 ; i<addressList.size() ; i++ ) {
			if ( addressList.get(i).getName().equals(addressVo.getName()) ) {
				addressList.set(i, addressVo);
				break;
			}
		}
		int result = writeObject();
		
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		for ( int i=0 ; i<addressList.size() ; i++ ) {
			if ( addressList.get(i).getName().equals(addressVo.getName()) ) {
				addressList.remove(i);
				break;
			}
		}
		int result = writeObject();

		return result;
	}

	@Override
	public int createStorage() throws Exception {
		int result = 0;
		if(file.createNewFile()) {
			LOGGER.debug("파일이 생성 되었습니다. 파일이름: " + file.getName());
			result = 1;
		}
		else {
			LOGGER.debug("파일이 이미 존재합니다. 파일이름: " + file.getName());
		}
		
		return result;
	}
	
	private int writeObject() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			for (AddressVo vo : addressList) {
				oos.writeObject(vo);
			}
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return -1;
		}
		
		return 0;
	}

}
