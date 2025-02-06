package com.barunsw.ojt.cjs.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;

public class SocketAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketAddressBookImpl.class);

	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;

	public SocketAddressBookImpl(String host, Integer port) throws Exception {
		clientSocket = new Socket(host, port);

		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception {
		String command = String.format("SELECT:\n");

		writer.write(command);
		writer.flush();

		StringBuffer buf = new StringBuffer();
		String readLine = null;
		
		while ((readLine = reader.readLine()) != null) {
			if (readLine.startsWith("SELECT")) {
				buf.append(readLine);
				break;
			}
		}

		LOGGER.debug("received:" + readLine);

		List<AddressVo> addressList = new ArrayList<AddressVo>();
		String data[] = buf.toString().split("&");
		
		for (String read : data) {
			AddressVo address = parseCmd(read);
			addressList.add(address);
			LOGGER.debug("addressList:" + addressList);

		}
		LOGGER.debug("addressList:" + addressList);

		return addressList;
		
	}
	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String command = String.format("INSERT:NAME=%s,AGE=%d,GENDER=%s,ADDRESS=%s\n",
				addressVo.getName(),
				addressVo.getAge(),
				addressVo.getGender(),
				addressVo.getAddress()
				);

		LOGGER.debug("command:" + command);
		writer.write(command);
		LOGGER.debug("writer.write()");
		writer.flush();
		LOGGER.debug("writer.flush()");
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String command = String.format("UPDATE:NAME=%s,AGE=%s,GENDER=%s,ADDRESS=%s\n", 
				addressVo.getName(),
				addressVo.getAge(), 
				addressVo.getGender(),
				addressVo.getAddress()
		);
		writer.write(command);
		writer.flush();
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String command = String.format("DELETE:NAME=%s\n", 
				addressVo.getName()
				);
		writer.write(command);
		writer.flush();

		return 0;
	}

	private AddressVo parseCmd(String paramStr) throws Exception {
		AddressVo addressVo = new AddressVo();

		String[] paramList = paramStr.split(",");

		for (String oneParam : paramList) {
			String[] oneParamData = oneParam.split("=");
			String key = oneParamData[0].trim();
			String val = oneParamData[1].trim();

			switch (key) {
			case "SELECT:NAME": //첫 이름은 SELECT가 붙어있어서 이렇게 찾아줘야하는 듯
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
			}
		}
		return addressVo;
	}

	public void close() {

	}
}
