package com.barunsw.ojt.cjs.day09;

import java.io.EOFException;
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
	private List<AddressVo> addressList = new ArrayList<AddressVo>(); //리스트 객체를 먼저 선언해놓고 저장한다.

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			Object obj; 
			addressList.clear();
			while ((obj = ois.readObject()) != null) {
				AddressVo address = (AddressVo) obj; 
				addressList.add(address);
				LOGGER.debug(obj + "");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
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
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getName().equals(addressVo.getName())) //리스트에 있는 i번째 행에 있는 이름이랑 같은지 확인
				addressList.set(i, addressVo); //리스트 i번째에 있는 객체 바꿔줌
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			for (AddressVo address : addressList) {
				oos.writeObject(address);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
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
		return 0;
	}
}
