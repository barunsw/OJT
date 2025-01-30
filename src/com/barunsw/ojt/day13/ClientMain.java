package com.barunsw.ojt.day13;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain {
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);
	
	public static void main(String[] args) {
		try {
			AddressBookInterface addressBookIf = 
					new SocketAddressBookImpl("localhost", ServerMain.PORT);
			
			AddressVo addressVo = new AddressVo();
			addressVo.setName("홍길동");
			addressVo.setAge(20);
			
			addressBookIf.insertAddress(addressVo);
			
			addressVo = new AddressVo();
			addressVo.setName("유관순");
			addressVo.setAge(30);
			
			addressBookIf.insertAddress(addressVo);
			
			List<AddressVo> addressList = addressBookIf.selectAddressList(new AddressVo());
			for (AddressVo oneAddressVo : addressList) {
				LOGGER.debug("oneAddressVo:" + oneAddressVo);
			}
			
			Thread.sleep(10_000);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
