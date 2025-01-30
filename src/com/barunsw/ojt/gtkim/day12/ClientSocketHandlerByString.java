package com.barunsw.ojt.gtkim.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class ClientSocketHandlerByString extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandlerByString.class);

	private Socket clientSocket;
	private DBAddressBookImpl dbConn = new DBAddressBookImpl();
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	
	public ClientSocketHandlerByString(Socket clientSocket) throws Exception{
		this.clientSocket = clientSocket;
		this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	
	}
	
	@Override 
	public void run() {
		try {
			String readLine = null;
			
			while (!clientSocket.isClosed()) {
				readLine = reader.readLine();
				if (readLine != null) {
					LOGGER.debug("[Server] readLine : " + readLine);

					String[] readLineSplit = readLine.split(":");
					String command = readLineSplit[0];
					AddressVo oneAddress = null;

					if (!command.equals("SELECT")) {
						oneAddress = parseCommand(readLineSplit[1]);			
						int ret = handleCommand(command, oneAddress);
						writer.write(ret);
						writer.flush();
						LOGGER.debug("Ret " + ret);
					}
					else {
						sendData(dbConn.selectAddressList());
					}
				
					
				}
			}
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}
	
	private AddressVo parseCommand(String param) throws Exception {
		AddressVo oneAddress = new AddressVo();
		
		String[] paramList = param.split(",");
		for (String pair : paramList) {
			String[] pairList = pair.split("=");
			String key = pairList[0];
			String val = pairList[1];
			
			switch (key) {
			case "NAME":
				oneAddress.setName(val);
				break;
			case "GENDER":
				oneAddress.setGender(Gender.toGender(val));
				break;
			case "AGE":
				oneAddress.setAge(Integer.parseInt(val));
				break;
			case "PHONE":
				oneAddress.setPhone(val);
				break;
			case "ADDRESS":
				oneAddress.setAddress(val);
				break;
			case "SEQ":
				oneAddress.setSeq(Integer.parseInt(val));
				break;
			default :
				LOGGER.error("올바르지 않은 Key 입니다." + key);
			}
		}
		return oneAddress;
	}
	
	private int handleCommand(String command, AddressVo addressVo) {
		int returnValue = 0;
		
		try {			
			switch (command) {
			case "INSERT":
				returnValue = dbConn.insertAddress(addressVo);
				break;
			case "UPDATE":
				returnValue = dbConn.updateAddress(addressVo);
				break;
			case "DELETE":
				returnValue = dbConn.deleteAddress(addressVo);
				break;
			default :
				LOGGER.debug("잘못된 Command 입니다.");
				returnValue = -1;
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return returnValue;
	}
	
	private void sendData(List<AddressVo> addressList) {
		try {
			BufferedWriter bw = new BufferedWriter(writer);
			for (AddressVo oneAddress : addressList) {
				StringBuffer sb = new StringBuffer();
				
				sb.append("NAME=").append(oneAddress.getName()).append(",")
				   .append("GENDER=").append(oneAddress.getGender()).append(",")
				   .append("AGE=").append(oneAddress.getAge()).append(",")
				   .append("PHONE=").append(oneAddress.getPhone()).append(",")
				   .append("ADDRESS=").append(oneAddress.getAddress()).append(",")
				   .append("SEQ=").append(oneAddress.getSeq()).append("\n");
				
				LOGGER.debug(sb.toString());
					  
				writer.write(sb.toString());
				writer.flush();
			}
			bw.close();
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
}
