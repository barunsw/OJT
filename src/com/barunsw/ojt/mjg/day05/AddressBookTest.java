package com.barunsw.ojt.mjg.day05;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;
import com.barunsw.ojt.constants.Gender;

public class AddressBookTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookTest.class);

    public static void main(String[] args) throws Exception {
/*	
        // File 기반 테스트
        AddressBookInterface fileAddressBook = new FileAddressBookImpl();
        testAddressBook(fileAddressBook);

        // Object 기반 테스트
        AddressBookInterface objectAddressBook = new ObjectAddressBookImpl();
        testAddressBook(objectAddressBook);
*/
        // JDBC 기반 테스트
        AddressBookInterface jdbcAddressBook = new JdbcAddressBookImpl();
        testAddressBook(jdbcAddressBook);

    }

    private static void testAddressBook(AddressBookInterface addressBook) throws Exception {
        LOGGER.debug("Testing {}", addressBook.getClass().getSimpleName());

        // Insert
        addressBook.insertAddress(new AddressVo());
        addressBook.insertAddress(new AddressVo());

        // Select
        List<AddressVo> addressList = addressBook.selectAddressList(null);
        LOGGER.debug("Address List:");
        for (AddressVo address : addressList) {
            LOGGER.debug("{}", address);
        }

        // Update
        AddressVo updatedAddress = new AddressVo();
        addressBook.updateAddress(updatedAddress);

        // Update 확인
        addressList = addressBook.selectAddressList(null);
        LOGGER.debug("Address List After Update:");
        for (AddressVo address : addressList) {
            LOGGER.debug("{}", address);
        }

        // Delete
        addressBook.deleteAddress(new AddressVo());

        // Delete 확인
        addressList = addressBook.selectAddressList(null);
        LOGGER.debug("Address List After Delete:");
        for (AddressVo address : addressList) {
            LOGGER.debug("{}", address);
        }
    }
}
