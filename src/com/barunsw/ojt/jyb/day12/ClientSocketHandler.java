package com.barunsw.ojt.jyb.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.List;
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

	private AddressBookInterface addressBookIf;

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
	public void run() {
		LOGGER.debug("+++ ClientSocketHandler run");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) { // 클라이언트의 요청을 계속 읽기
				LOGGER.debug(String.format("+++ readLine: %s", readLine));
				System.out.println("클라이언트에서 받은 요청 : " + readLine);

				String[] cmdSplit = readLine.split(":");
				String cmd = cmdSplit[0];

				String result = "OK";
				try {
					// 파라미터가 있을 경우 처리
					switch (cmd) {
					case "INSERT":
						System.out.println("INSERT 실행됨");
						AddressVo addressVo = parseCmd(cmdSplit[1]);
						handleInsert(addressVo);
						break;
					case "SELECT":
						System.out.println("SELECT 실행됨!!");
						LOGGER.debug("Handling SELECT command");
						result = handleSelect();
						System.out.println("handleSelect 결과 : " + result);
						System.out.println("응답 전송 완료");

						break;
					case "UPDATE":
						System.out.println("UPDATE 실행됨");
						AddressVo addressVo2 = parseCmd(cmdSplit[1]);
						handleUpdate(addressVo2);
						break;
					case "DELETE":
						System.out.println("DELETE 실행됨");
						AddressVo addressVo3 = parseCmd(cmdSplit[1]);
						handleDelete(addressVo3);
						break;
					default:
						LOGGER.error("Unknown command: " + cmd);
					}

				}
				catch (Exception e) {
					LOGGER.error("Error processing command: " + cmd, e);
				}

				writer.write(result);
				writer.newLine();
				writer.flush();
			}
		}
		catch (IOException ex) {
			LOGGER.error("Error reading from client", ex);
		}
		catch (Exception ex) {
			LOGGER.error("Error in handling client request", ex);
		}

		LOGGER.debug("--- ClientSocketHandler run");
	}

	private String handleSelect() throws Exception {
		if (addressBookIf == null) {
			throw new RuntimeException("addressbookinterface가 초기화되지않음");
		}

		AddressVo searchCondition = new AddressVo();
		List<AddressVo> addrList = addressBookIf.selectAddressList(searchCondition);

		StringBuffer buffer = new StringBuffer();

		for (AddressVo address : addrList) {
			buffer.append(String.format("SEQ=%d,NAME=%s,AGE=%d,GENDER=%s,ADDRESS=%s,PHONE=%s\n", address.getSeq(),
					address.getName(), address.getAge(), address.getGender(), address.getAddress(),
					address.getPhone()));
		}
		return buffer.toString();
	}

	private void handleInsert(AddressVo addressVo) throws Exception {
		LOGGER.debug(String.format("+++ handleInsert [%s]", addressVo));
		addressBookIf.insertAddress(addressVo);
	}

	private void handleUpdate(AddressVo addressVo) throws Exception {
		LOGGER.debug(String.format("+++ handleUpdate [%s]", addressVo));
		addressBookIf.updateAddress(addressVo);
	}

	private void handleDelete(AddressVo addressVo) throws Exception {
		LOGGER.debug(String.format("+++ handleDelete [%s]", addressVo));
		addressBookIf.deleteAddress(addressVo);
	}
}
