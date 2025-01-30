package com.barunsw.ojt.iwkim.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonVO;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	private Socket socket;

	public ClientSocketHandler(Socket socket) throws Exception{
		this.socket = socket;
	}

	@Override
	public void run() {
		LOGGER.info("+++ run");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			 BufferedWriter	writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
			String readLine = null;
			int queryExcResult = 0;

			while ((readLine = reader.readLine()) != null) {
				LOGGER.info("받은 readLine : " + readLine);
				LOGGER.info("-----------------------------------");
				String[] receivedData = readLine.split(":");
				LOGGER.info("Command : " + receivedData[0]);
				switch (receivedData[0]) {
				// 입력값이 select인경우는 DB에서 값을 가져와서 write로 보내주어야 한다.
				case "SELECT" :
					LOGGER.info("-------------select-----------");

					List<PersonVO> addressList = ServerMain.dbAddressBookImpl.selectList();
					LOGGER.info("addressList : " + addressList);
					// 리스트를 넘길 수 없으므로 개인의 정보 각각을 문자열로 만들어줘서 넘겨야한다.
					// 덩어리를 전체로 보내고 나눠야함
					// 그리고 보낼때도 crud를 붙여서 보내야함! -> 받을때 구분하기 위해
					// readLine은 개행을 기다린다!
					StringBuffer sb = new StringBuffer();
					sb.append("SELECT:");
					for (PersonVO onePersonInfo : addressList) {
						// 동기화가 필요한 멀티쓰레드 환경이므로 StringBuffer를 사용
						sb.append("NAME=" + onePersonInfo.getName() + ",")
						.append("AGE=" + onePersonInfo.getAge() + ",")
						.append("GENDER=" + onePersonInfo.getGender() + ",")
						.append("PHONE=" + onePersonInfo.getPhone() + ",")
						.append("ADDRESS=" + onePersonInfo.getAddress() + "~" );

						LOGGER.info("핸들러 개인정보 : " + sb.toString());
					}
					writer.write(sb.toString() + "\n");
					writer.flush();
					break;
				case "INSERT" :	
					LOGGER.info("++++++tcp {} ", getPersonInfo(receivedData[1]));
					
					queryExcResult = ServerMain.dbAddressBookImpl.insertPerson(getPersonInfo(receivedData[1]));
					LOGGER.info("INSERT RESULT : " + queryExcResult);
					writer.write(queryExcResult);
					writer.flush();
					break;
				case "UPDATE" :
					queryExcResult = ServerMain.dbAddressBookImpl.updatePerson(getPersonInfo(receivedData[1]));
					LOGGER.info("UPDATE RESULT : " + queryExcResult);
					writer.write(queryExcResult);
					writer.flush();
					break;
				case "DELETE" :
					String[] columnData = receivedData[1].split("=");
					queryExcResult = ServerMain.dbAddressBookImpl.deletePerson(columnData[1]);
					LOGGER.info("DELETE RESULT : " + queryExcResult);
					writer.write(queryExcResult);
					writer.flush();
					break;
				default :
					LOGGER.error("입력값이 잘못 되었습니다.");
					break;
				}
			}
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		LOGGER.debug("--- run");
	}

	private PersonVO getPersonInfo(String receivedData) {
		String[] columnData = receivedData.split(",");
		PersonVO onePersonInfo = new PersonVO();
		for (String column : columnData) {
			LOGGER.info("column : " + column);
			String[] value = column.split("=");
			switch (value[0]) {
			case "NAME" :
				onePersonInfo.setName(value[1]);
				break;
			case "AGE" :
				onePersonInfo.setAge(Integer.parseInt(value[1]));
				break;
			case "GENDER" :
				onePersonInfo.setGender(value[1]);
				break;
			case "PHONE" :
				onePersonInfo.setPhone(value[1]);
				break;
			case "ADDRESS" :
				onePersonInfo.setAddress(value[1]);
				break;
			default :
				LOGGER.info("잘못 입력되었습니다.");
				break;
			}	
		}

		return onePersonInfo;
	}
}