package com.barunsw.ojt.gtkim.day12;

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

public class SocketAddressBookImplByString implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(SocketAddressBookImplByString.class);

	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private String host;
	private int port;

	public SocketAddressBookImplByString(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private void connect(String host, int port) {
		try {
			if (clientSocket == null || clientSocket.isClosed()) {
				LOGGER.debug("새로운 연결을 생성합니다");
				clientSocket = new Socket(host, port);
			}
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	@Override
	public List<AddressVo> selectAddressList() throws Exception {	
		connect(host, port);
		
		List<AddressVo> addressList = new ArrayList<AddressVo>();
		String command = "SELECT:ALL\n";

		writer.write(command);
		writer.flush();

		StringBuffer sb = new StringBuffer();
		String readLine = null;
		while ((readLine = reader.readLine()) != null) {
			sb.append(readLine).append("\n");
		}
		LOGGER.debug("수신 데이터 " + sb.toString());
		
		String[] readData = sb.toString().split("\n");
		for (String data : readData) {
			AddressVo oneAddress = parseData(data);
			addressList.add(oneAddress);
		}

		
		close(clientSocket, writer, reader);
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		connect(host, port);
		
		String command = String.format("INSERT:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s\n",
				addressVo.getName(), addressVo.getGender().toString(), Integer.toString(addressVo.getAge()),
				addressVo.getPhone(), addressVo.getAddress());

		writer.write(command);
		writer.flush();
		
		int result = reader.read();

		close(clientSocket, writer, reader);
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		connect(host, port);

		String command = String.format("UPDATE:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s,SEQ=%s\n",
				addressVo.getName(), addressVo.getGender().toString(), Integer.toString(addressVo.getAge()),
				addressVo.getPhone(), addressVo.getAddress(), Integer.toString(addressVo.getSeq()));
		
		writer.write(command);
		writer.flush();
		
		int result = reader.read();
		
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		connect(host, port);
		String command = String.format("DELETE:SEQ=%s\n", Integer.toString(addressVo.getSeq()));
		LOGGER.debug("delete 내용 : " + command);
		writer.write(command);
		writer.flush();

		int result = reader.read();

		close(clientSocket, writer, reader);
		return result;
	}

	public void close(Socket clientSocket, BufferedWriter writer, BufferedReader reader) {
		try {
			if (reader != null) {
				reader.close();
			}

			if (writer != null) {
				writer.close();
			}

			if (clientSocket != null) {
				clientSocket.close();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private AddressVo parseData(String param) throws Exception {
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
			default:
				LOGGER.error("올바르지 않은 Key 입니다. key=" + key + " val=" + val);
			}
		}
		return oneAddress;
	}

}
