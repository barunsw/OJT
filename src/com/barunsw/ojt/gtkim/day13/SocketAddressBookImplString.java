package com.barunsw.ojt.gtkim.day13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.gtkim.day12.AddressBookInterface;
import com.barunsw.ojt.gtkim.day12.AddressVo;

public class SocketAddressBookImplString implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(SocketAddressBookImplString.class);
	
	private Socket clientSocket;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public SocketAddressBookImplString(String host, int port) throws Exception {
		this.clientSocket = new Socket(host, port);
		this.reader =
				new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.writer = 
				new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	}
	
	private AddressVo parseData(String data) throws Exception {
		AddressVo oneAddress = new AddressVo();
		
		String[] paramList = data.split(",");
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
			default:
				LOGGER.error("올바르지 않은 Key 입니다. key=" + key + " val=" + val);
			}
		}
		
		return oneAddress;
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> addressList = new ArrayList<>();
		
		String command ="SELECT:NAME=ALL\n";
		
		writer.write(command);
		writer.flush();

		StringBuffer stringBuffer = new StringBuffer();
		String readLine = null;
		while ((readLine = reader.readLine()) != null) {
			if (readLine.equals("end")) {
				break;
			}
			
			stringBuffer.append(readLine).append("\n");
		}
		readLine = stringBuffer.toString();
		
		String[] parseData = readLine.split("\n");
		for (String data : parseData) {
			AddressVo oneAddress = parseData(data);
			addressList.add(oneAddress);
		}
		
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String command = String.format("INSERT:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s\n",
				addressVo.getName(), addressVo.getGender().toString(), Integer.toString(addressVo.getAge()),
				addressVo.getPhone(), addressVo.getAddress());

		writer.write(command);
		writer.flush();
		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String command = String.format("UPDATE:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s,SEQ=%s\n",
				addressVo.getName(), addressVo.getGender().toString(), Integer.toString(addressVo.getAge()),
				addressVo.getPhone(), addressVo.getAddress(), Integer.toString(addressVo.getSeq()));
		
		writer.write(command);
		writer.flush();
		
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String command = String.format("DELETE:SEQ=%s\n", Integer.toString(addressVo.getSeq()));
		
		writer.write(command);
		writer.flush();

		return 0;
	}
	
	private void close() {
		if (reader != null) {
			try {
				reader.close();
			}
			catch (Exception ex) {
				LOGGER.debug(ex.getMessage(), ex);
			}
		}
		
		if (writer != null) {
			try {
				writer.close();
			}
			catch (Exception ex) {
				LOGGER.debug(ex.getMessage(), ex);
			}
		}
		
		if (clientSocket != null) {
			try {
				clientSocket.close();
			}
			catch (Exception ex) {
				LOGGER.debug(ex.getMessage(), ex);
			}
		}
	}
}
