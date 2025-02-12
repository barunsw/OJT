package com.barunsw.ojt.jyb.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.jyb.day10.JdbcAddressBookImpl;
import com.barunsw.ojt.vo.AddressVo;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);

	private Socket clientSocket; // 클라이언트와의 소켓 연결 저장

	private AddressBookInterface addressBookIf = new JdbcAddressBookImpl();

	static {
		try {
			// config.properties 파일을 읽어들임
			Properties properties = new Properties();
			try (Reader reader = Resources.getResourceAsReader("config.properties")) {
				properties.load(reader);
			}

			// address_if_class 프로퍼티 값을 읽어옴
			String addressIfClassName = properties.getProperty("address_if_class");

			if (addressIfClassName == null) {
				throw new RuntimeException("address_if_class 프로퍼티가 설정되지 않았습니다.");
			}

			// 클래스를 동적으로 로드
			Class<?> clazz = Class.forName(addressIfClassName);
		}
		catch (Exception e) {
			throw new RuntimeException("config.properties 로드 실패", e);
		}
	}

	public ClientSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	private AddressVo parseCmd(String paramStr) throws Exception { // 클라이언트가 보낸 데이터를 파싱하여 AddressVo 객체 생성
		AddressVo addressVo = new AddressVo();

		String[] paramList = paramStr.split(",");
		for (String oneParam : paramList) {
			if (oneParam.trim().isEmpty()) {
				continue;
			}

			String[] oneParamData = oneParam.split("=");

			String key = oneParamData[0].trim();
			String val = oneParamData[1].trim();

			System.out.println("Key: " + key + ", Value: " + val);

			switch (key) {
			case "NAME":
				addressVo.setName(val);
				break;
			case "AGE":
				addressVo.setAge(Integer.parseInt(val));
				break;
			case "GENDER":
				addressVo.setGender(Gender.toGender(val));
				break;
			case "ADDRESS":
				addressVo.setAddress(val);
				break;
			case "PHONE":
				addressVo.setPhone(val);
				break;
			}
		}
		return addressVo;
	}

	@Override
	public void run() { // 소켓을 통해 클라이언트의 요청 처리
		LOGGER.debug("+++ ClientSocketHandler run");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("+++ readLine:%s", readLine));

				String[] cmdSplit = readLine.split(":");

				String cmd = cmdSplit[0];
				switch (cmd) {
				case "INSERT":
					AddressVo addressVo = parseCmd(cmdSplit[1]);
					handleInsert(addressVo);
					break;
				}
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		LOGGER.debug("--- ClientSocketHandler run");
	}

	private void handleSelect() throws Exception {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
			// List<AddressBookVo> addrList = addrImpl.selectList();
			// List -> String
			// writer.write(message);
		}
	}

	private void handleInsert(AddressVo addressVo) throws Exception { // INSERT 명령이 들어왔을 때 호출되는 메소드
		LOGGER.debug(String.format("+++ handleInsert [%s]", addressVo));
		addressBookIf.insertAddress(addressVo);
	}
}
