package com.barunsw.ojt.iwkim.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.AddressBookInterface;
import com.barunsw.ojt.iwkim.common.PersonVO;

public class SocketAddressBookImpl implements AddressBookInterface {
	private static Logger LOGGER = LogManager.getLogger(SocketAddressBookImpl.class);

	// 클라이언트에서 서버와 통신하려면 소켓이 필요하다.
	private Socket clientSocket;
	BufferedReader reader; 
	BufferedWriter writer;

	public SocketAddressBookImpl(String host, int port) {
		try {
			System.out.println("생성자가 생성됨");
			clientSocket = new Socket(host, port);
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
			writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); 
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	// 서버에 select인 것을 알리고 서버에서 값을 받아와야 한다.
	@Override
	public List<PersonVO> selectList() {

		try {
			List<PersonVO> addressList = new ArrayList<>();
			String command = String.format("SELECT:ALL\n");
			writer.write(command);
			writer.flush();

			StringBuffer buf = new StringBuffer();
			String readLine = null;

			LOGGER.info("ㅡㅡㅡㅡㅡㅡreaderTestㅡㅡㅡㅡㅡㅡㅡ");
			while ((readLine = reader.readLine()) != null) {
				System.out.println("readLine");
				if (readLine.startsWith("SELECT")) {
					buf.append(readLine).delete(0, 7);
					break;
				}
			}
			LOGGER.info("받은 readLine : " + buf.toString());
			// buf의 내용을 list에 넣어주자
			String[] receivedData = buf.toString().trim().split("~"); 
			LOGGER.info("개인정보" + receivedData[0]);
			for (String data : receivedData) {
				LOGGER.info("받은 data : " + data);
				PersonVO onePersonInfo = new PersonVO();
				String[] columnList = data.split(",");

				for (String columnData : columnList) {
					String[] column = columnData.split("=");

					switch (column[0]) {
					case "NAME":
						onePersonInfo.setName(column[1]);
						break;
					case "AGE":
						onePersonInfo.setAge(Integer.parseInt(column[1]));
						break;
					case "GENDER":
						onePersonInfo.setGender(column[1]);
						break;
					case "PHONE":
						onePersonInfo.setPhone(column[1]);
						break;
					case "ADDRESS":
						onePersonInfo.setAddress(column[1]);
						break;
					default:
						LOGGER.error("잘못 입력되었습니다.");
						LOGGER.info("column : " + column[0] + " = " + column[1]);
					}
				}

				addressList.add(onePersonInfo);
			}

			return addressList;
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public int insertPerson(PersonVO param) throws Exception {

		try{
			String command = String.format("INSERT:NAME=%s,AGE=%s,GENDER=%s,PHONE=%s,ADDRESS=%s\n"
					, param.getName()
					, String.valueOf(param.getAge())
					, param.getGender()
					, param.getPhone()
					, param.getAddress());
			LOGGER.info("INSERT command : " + command);
			writer.write(command);
			writer.flush();

			int queryExcResult = reader.read();

			return queryExcResult;
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return 0;
	}

	@Override
	public int updatePerson(PersonVO param) throws Exception {

		try{
			String command = String.format("UPDATE:NAME=%s,AGE=%s,GENDER=%s,PHONE=%s,ADDRESS=%s\n"
					, param.getName()
					, String.valueOf(param.getAge())
					, param.getGender()
					, param.getPhone()
					, param.getAddress());
			LOGGER.info("UPDATE command : " + command);
			writer.write(command);
			writer.flush();

			int queryExcResult = reader.read();

			return queryExcResult;
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return 0;
	}

	@Override
	public int deletePerson(String name) throws Exception {

		try{
			String command = String.format("DELETE:NAME=%s\n", name);
			LOGGER.info("DELETE command : " + command);
			writer.write(command);
			writer.flush();

			int queryExcResult = reader.read();

			return queryExcResult;
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return 0;
	}
}