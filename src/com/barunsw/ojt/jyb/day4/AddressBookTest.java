package com.barunsw.ojt.jyb.day4;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;
import com.barunsw.ojt.common.AddressBookInterface;

public class AddressBookTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookTest.class);

	public static void main(String[] args) throws Exception {

		// File 기반 테스트
		AddressBookInterface fileAddressBook = new FileAddressBookImpl();
		testAddressBook(fileAddressBook);

		// Object 기반 테스트
		AddressBookInterface objectAddressBook = new ObjectAddressBookImpl();
		testAddressBook(objectAddressBook);

		// JDBC 기반 테스트
		AddressBookInterface jdbcAddressBook = new JdbcAddressBookImpl();
		testAddressBook(jdbcAddressBook);

	}

	private static void testAddressBook(AddressBookInterface addressBook) throws Exception {
		LOGGER.debug("Testing {}", addressBook.getClass().getSimpleName());

		// Insert
		AddressVo newAddress = new AddressVo();
		newAddress.setName("abc");
		newAddress.setAge(26);
		newAddress.setGender(Gender.MAN);
		newAddress.setAddress("abc");
		addressBook.insertAddress(newAddress);

		// Select
		List<AddressVo> addressList = addressBook.selectAddressList(null);
		LOGGER.debug("Address List:");
		for (AddressVo address : addressList) {
			LOGGER.debug("{}", address);
		}

		// Update
		if (!addressList.isEmpty()) {
			AddressVo updatedAddress = new AddressVo();
			updatedAddress.setSeq(addressList.get(0).getSeq());
			updatedAddress.setName("cd");
			updatedAddress.setAge(50);
			updatedAddress.setGender(Gender.MAN);
			updatedAddress.setAddress("cd");
			addressBook.updateAddress(updatedAddress);
		}

		// Update 확인
		addressList = addressBook.selectAddressList(null);
		LOGGER.debug("Address List After Update:");
		for (AddressVo address : addressList) {
			LOGGER.debug("{}", address);
		}

		// Delete
		if (!addressList.isEmpty()) {
			AddressVo deleteAddress = new AddressVo();
			deleteAddress.setSeq(addressList.get(0).getSeq());
			addressBook.deleteAddress(deleteAddress);
		}

		// Delete 확인
		addressList = addressBook.selectAddressList(null);
		LOGGER.debug("Address List After Delete:");
		for (AddressVo address : addressList) {
			LOGGER.debug("{}", address);
		}

	}
}
