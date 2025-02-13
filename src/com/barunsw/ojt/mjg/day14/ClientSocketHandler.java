package com.barunsw.ojt.mjg.day14;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.AddressVo;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.mjg.day11.MybatisAddressBookImpl;
import com.barunsw.ojt.mjg.day14.ClientSocketHandler;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	
	private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

	private AddressBookInterface addressBookInterface;
	
	public ClientSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		initAddressBookIf();
	}

	private void initAddressBookIf() {
    	Properties properties = new Properties();
    	
    	try (Reader reader = Resources.getResourceAsReader("config.properties")) {
            properties.load(reader);
            
            String addressIfClass = properties.getProperty("address_if_class");
            
            Object o = Class.forName(addressIfClass).newInstance();
            
            addressBookInterface = (AddressBookInterface)o;
        }
		catch (Exception e) {
			throw new RuntimeException("config.properties 로드 실패", e);
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
			
		try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("+++ readLine:%s", readLine));
				
				String[] cmdSplit = readLine.split(":");
				AddressVo addressVo;
				
				String cmd = cmdSplit[0];
				switch (cmd) {
				case "INSERT":
					addressVo = parseCmd(cmdSplit[1]);
					handleInsert(addressVo);
					break;
				case "SELECT":
					handleSelect();
					writer.flush();
                    break;
				case "UPDATE":
					addressVo = parseCmd(cmdSplit[1]);
					handleUpdate(addressVo);
					break;
				case "DELETE":
					addressVo = parseCmd(cmdSplit[1]);
					handleDelete(addressVo);
					break;
				}
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		LOGGER.debug("--- ClientSocketHandler run");
	}

/*
	private void handleSelect() throws Exception {
	    if (addressBookInterface == null) {
	        throw new RuntimeException("addressBookInterface가 초기화되지 않았습니다.");
	    }
	    
	    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
	        List<AddressVo> addrList = addressBookInterface.selectAddressList(new AddressVo());

	        if (addrList.isEmpty()) {
	            writer.write("NO_RESULT\n");  // 데이터가 없을 경우
	        }
	        else {
	            for (AddressVo addressVo : addrList) {
	                writer.write(String.format("%s,%d,%s,%s,%s\n",
                		addressVo.getName()
                		, addressVo.getAge()
                		, addressVo.getGender()
                		, addressVo.getPhone()
                		, addressVo.getAddress()));
	            }
	            writer.write("END_OF_DATA\n");
	        }
	        writer.flush();  // 클라이언트가 데이터를 받을 수 있도록 flush() 실행
	    }
	}
*/
	
	private void handleSelect() throws Exception {
	    if (addressBookInterface == null) {
	        throw new RuntimeException("addressBookInterface가 초기화되지 않았습니다.");
	    }
	    
	    List<AddressVo> addrList = addressBookInterface.selectAddressList(new AddressVo());

	    if (addrList.isEmpty()) {
	        writer.write("NO_RESULT\n");
	    }
	    else {
	        for (AddressVo addressVo : addrList) {
	            writer.write(String.format("%s,%d,%s,%s,%s\n",
	                addressVo.getName()
	                , addressVo.getAge()
	                , addressVo.getGender()
	                , addressVo.getPhone()
	                , addressVo.getAddress()));
	        }
	        writer.write("END_OF_DATA\n");  // 클라이언트가 정상적으로 읽기 종료할 수 있도록 신호 추가
	    }
	    writer.flush();  // 데이터를 즉시 전송
	}

	
	private void handleInsert(AddressVo addressVo) throws Exception {
		LOGGER.debug(String.format("+++ handleInsert [%s]", addressVo));
		addressBookInterface.insertAddress(addressVo);
	}
	
	private void handleUpdate(AddressVo addressVo) throws Exception {
		LOGGER.debug(String.format("+++ handleInsert [%s]", addressVo));
		addressBookInterface.updateAddress(addressVo);
	}
	
	private void handleDelete(AddressVo addressVo) throws Exception {
		LOGGER.debug(String.format("+++ handleInsert [%s]", addressVo));
		addressBookInterface.deleteAddress(addressVo);
	}
}
