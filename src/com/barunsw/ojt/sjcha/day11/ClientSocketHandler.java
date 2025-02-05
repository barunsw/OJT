package com.barunsw.ojt.sjcha.day11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	private AddressBookInterface mybatisAddressBookImpl = new MybatisAddressBookImpl();

	private Socket clientSocket;
	
	// 생성자 (클라이언트의 받아들이면 들어와서 클라이언트를 가져온다.)
	public ClientSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));) {
			// 데이터를 읽어와서 넣는다.
			String readLine = null;

			while ((readLine = reader.readLine()) != null) {
				// : 기준으로 split 한다. 
				String[] cmdSplit = readLine.split(":");
				// DB 명령어 cmdSplit[0]
				LOGGER.debug("split Command : " + cmdSplit[0]);
				
				switch (cmdSplit[0]) {
				case "SELECT":
					String result = handleSelect();
					
					LOGGER.debug("result:" + result);
					
					writer.write(result + "\n");
					writer.flush();
					break;
				case "INSERT":
					AddressVo addressVo = parseCmd(cmdSplit[1]);
					int insertResult = handleInsert(addressVo);
					writer.write(insertResult + "\n");
					writer.flush();
					break;
				case "UPDATE":
					addressVo = parseCmd(cmdSplit[1]);
					int updateResult = mybatisAddressBookImpl.updateAddress(addressVo);
					writer.write(updateResult + "\n");
					writer.flush();
					break;
				case "DELETE":
					addressVo = parseCmd(cmdSplit[1]);
					int deleteResult = mybatisAddressBookImpl.deleteAddress(addressVo);
					writer.write(deleteResult + "\n");
					writer.flush();
					break;
				default :
					LOGGER.error("입력이 잘못되었습니다.");
					break;
				}
			}   
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private AddressVo parseCmd(String paramStr) throws Exception {
		AddressVo addressVo = new AddressVo();
		
		String[] paramList = paramStr.split(",");
		for (String oneParam : paramList) {
			String[] oneParamData = oneParam.split("=");
			String key = oneParamData[0].trim();
			String val = oneParamData[1].trim();
			
			switch (key) {
			case "NAME":
				addressVo.setName(val);
				break;
			case "GENDER":
				addressVo.setGender(Gender.toGender(val));
				break;
			case "AGE":
				addressVo.setAge(Integer.parseInt(val));
				break;
			case "PHONE":
				addressVo.setPhone(val);
				break;
			case "ADDRESS":
				addressVo.setAddress(val);
				break;
			}
		}
		
		return addressVo;
	}
	private String handleSelect() throws Exception {
		List<AddressVo> addressList = mybatisAddressBookImpl.selectAddressList(new AddressVo());
			
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT:");
		for (AddressVo onePerson : addressList) {
			sb.append("NAME=" + onePerson.getName() + ",")
			.append("GENDER=" + onePerson.getGender() + ",")
			.append("AGE=" + onePerson.getAge() + ",")
			.append("PHONE=" + onePerson.getPhone() + ",")
			.append("ADDRESS=" + onePerson.getAddress() + "/" );

			LOGGER.info("List - String : " + sb.toString());
		}
		
		return sb.toString();
	}
	
	private int handleInsert(AddressVo addressVo) throws Exception {
		int insertResult = mybatisAddressBookImpl.insertAddress(addressVo);
		return insertResult;
	}
	
	private int handleUpdate(AddressVo addressVo) throws Exception {
		int updateResult = mybatisAddressBookImpl.updateAddress(addressVo);
		return updateResult;
	}
	
	private int handleDelete(AddressVo addressVo) throws Exception {
		int deleteResult = mybatisAddressBookImpl.deleteAddress(addressVo);
		return deleteResult;
	}
}
