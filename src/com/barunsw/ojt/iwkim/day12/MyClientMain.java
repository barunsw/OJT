package com.barunsw.ojt.iwkim.day12;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.AddressBookInterface;

public class MyClientMain {
	private static final Logger LOGGER = LogManager.getLogger(MyClientMain.class);
	AddressBookInterface addressBook;

	public MyClientMain() {
		try {
			System.out.println("111111111");
			addressBook = new SocketAddressBookImpl("127.0.0.1", 50003);
			System.out.println("22222222");
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

	}

	public static void main(String[] args) {
		try {
			new MyClientMain();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
