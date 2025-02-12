package com.barunsw.ojt.jyb.day12;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.AddressVo;
import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.constants.Gender;

public class ClientMain { // 클라이언트 측에서 연결하기 위한 코드
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);

	public static void main(String[] args) {
		try {
			AddressBookInterface addressBookIf = new SocketAddressBookImpl("localhost", ServerMain.PORT);

			AddressVo addressVo = new AddressVo();
			addressVo.setName("홍길동");
			addressVo.setAge(20);
			addressVo.setGender(Gender.WOMAN);
			addressVo.setAddress("집주소");
			addressVo.setPhone("010-0000-0000");

			addressBookIf.insertAddress(addressVo);
			String request = String.format("INSERT:NAME=%s,AGE=%d,GENDER=%s,ADDRESS=%s,PHONE=%s", addressVo.getName(),
					addressVo.getAge(), addressVo.getGender(), addressVo.getAddress(), addressVo.getPhone());

			System.out.println("보낸거: " + request);

			Thread.sleep(10_000);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
