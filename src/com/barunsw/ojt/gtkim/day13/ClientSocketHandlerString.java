package com.barunsw.ojt.gtkim.day13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.gtkim.day12.AddressVo;
import com.barunsw.ojt.gtkim.day12.DBAddressBookImpl;

public class ClientSocketHandlerString extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandlerString.class);
	
	private Socket clientSocket;

	private BufferedReader reader;
	private BufferedWriter writer;
	
	private DBAddressBookImpl dbController = new DBAddressBookImpl();

	public ClientSocketHandlerString(Socket clientSocket) throws Exception {
		this.clientSocket = clientSocket;
		this.reader = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		this.writer = new BufferedWriter(
				new OutputStreamWriter(clientSocket.getOutputStream()));
	}
		
	@Override
	public void run() {
		String readLine = null;
		
		try {
			while((readLine = reader.readLine()) != null) {
				LOGGER.debug("읽은 데이터 : " + readLine);
				
				String[] parseReadLine = readLine.split(":");
				String command = parseReadLine[0];
				AddressVo oneAddress = parseData(parseReadLine[1]);
				
				switch (command) {
				case "SELECT" :
					selectHandler(oneAddress);
					break;
				case "INSERT" :
					dbController.insertAddress(oneAddress);
					break;
				case "UPDATE" :
					dbController.updateAddress(oneAddress);
					break;
				case "DELETE" :
					dbController.deleteAddress(oneAddress);
					break;
				default :
					LOGGER.error("알 수 없는 Command 입니다. " + command);
				}
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void selectHandler(AddressVo addressvo) throws Exception {
		List<AddressVo> addressList = dbController.selectAddressList();
		
		for (AddressVo oneAddress : addressList) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("NAME=").append(oneAddress.getName()).append(",")
			   .append("GENDER=").append(oneAddress.getGender()).append(",")
			   .append("AGE=").append(oneAddress.getAge()).append(",")
			   .append("PHONE=").append(oneAddress.getPhone()).append(",")
			   .append("ADDRESS=").append(oneAddress.getAddress()).append(",")
			   .append("SEQ=").append(oneAddress.getSeq()).append("\n");
			
			writer.write(buffer.toString());
			writer.flush();
		}
		// 끝을 알리는 신호
		writer.write("end\n");
		writer.flush();
		
	}
	
	private AddressVo parseData(String param) throws Exception {
		AddressVo vo = new AddressVo();
		
		String[] paramList = param.split(",");
		for (String pair : paramList) {
			String[] pairList = pair.split("=");
			
			String key = pairList[0];
			String val = pairList[1];
			
			switch (key) {
			case "NAME" :
				vo.setName(val);
				break;
			case "GENDER" :
				vo.setGender(Gender.toGender(val));
				break;
			case "AGE" :
				vo.setAge(Integer.parseInt(val));
				break;
			case "PHONE" :
				vo.setPhone(val);
				break;
			case "ADDRESS" :
				vo.setAddress(val);
				break;
			case "SEQ" :
				vo.setSeq(Integer.parseInt(val));
				break;
			default:
				LOGGER.debug("알 수 없는 Key 입니다 : " + key);
			}
		}
		
		return vo;
	}
	
}
