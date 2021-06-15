package com.barunsw.ojt.iwkim.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonVO;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	
	private Socket socket;
	private DBAddressBookImpl dbConnection = new DBAddressBookImpl(); 
	
	public ClientSocketHandler(Socket socket) throws Exception{
		this.socket = socket;
	}
	
	@Override
	public void run() {
		LOGGER.info("+++ run");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			 BufferedWriter	writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
			String readLine = null;
			
			while ((readLine = reader.readLine()) != null) {
				LOGGER.info("받은 readLine : "  + readLine);
				
				String[] receivedData = readLine.split(":");
				switch (receivedData[0]) {
				// 입력값이 select인경우는 DB에서 값을 가져와서 write로 보내주어야 한다.
				case "SELECT" :
					List<PersonVO> addressList = dbConnection.selectList();
	
					// 리스트를 넘길 수 없으므로 개인의 정보 각각을 문자열로 만들어줘서 넘겨야한다.
					for (PersonVO onePersonInfo : addressList) {
						// 동기화가 필요한 멀티쓰레드 환경이므로 StringBuffer를 사용
						StringBuffer sb = new StringBuffer();
						sb.append("NAME=" + onePersonInfo.getName() + ",")
						.append("AGE=" + onePersonInfo.getAge() + ",")
						.append("GENDER=" + onePersonInfo.getGender() + ",")
						.append("PHONE=" + onePersonInfo.getPhone() + ",")
						.append("ADDRESS=" + onePersonInfo.getPhone() + ",");
						
						LOGGER.info("핸들러 개인정보 : " + sb.toString());
						
						writer.write(sb.toString());
						writer.flush();
						
						LOGGER.info("--- SELECT 끝");
					}
					break;
				case "INSERT" :
					break;
				case "UPDATE" :
					break;
				case "DELETE" :
					break;
				default :
					LOGGER.error("입력값이 잘못 되었습니다.");
				}
			}
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		LOGGER.debug("--- run");
	}
}
