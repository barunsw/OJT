package com.barunsw.ojt.sjcha.day11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.iwkim.common.PersonVO;

public class SocketAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(SocketAddressBookImpl.class);

	private Socket clientSocket;

	private BufferedReader reader;
	private BufferedWriter writer;

	public SocketAddressBookImpl(String host, int port) throws Exception {
		clientSocket = new Socket(host, port);
		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception {
		List<AddressVo> addressList = new ArrayList<>();
		try {
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
			LOGGER.debug("StringBuffer : " + buf);

			String[] spliteReadLine = buf.toString().split("/");
			LOGGER.info("splite : " + spliteReadLine[0]);
			
			for (String onePerson : spliteReadLine) {
				LOGGER.debug("oneperson : " + onePerson);
				AddressVo personData = new AddressVo();
				String[] columnList = onePerson.split(",");
				
				for (String columnData : columnList) {
					String[] data = columnData.split("=");
					
					switch (data[0]) {
					case "NAME":
					case "SELECT:NAME":
						personData.setName(data[1]);
						break;
					case "GENDER":
						personData.setGender(Gender.toGender(data[1]));
						break;
					case "AGE":
						personData.setAge(Integer.parseInt(data[1]));
						break;
					case "PHONE":
						personData.setPhone(data[1]);
						break;
					case "ADDRESS":
						personData.setAddress(data[1]);
						break;
					}
				}
				// 리스트에 한사람 정보 입력
				addressList.add(personData);
			}
			LOGGER.debug(addressList);
		}
		
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String command = String.format("INSERT:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s\n", 
				addressVo.getName(), addressVo.getGender(), addressVo.getAge(), addressVo.getPhone(), addressVo.getAddress());

		LOGGER.debug("command:" + command);

		writer.write(command);

		LOGGER.debug("writer.write()");

		writer.flush();

		LOGGER.debug("writer.flush()");

		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String command = String.format("UPDATE:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s\n",
				addressVo.getName(), addressVo.getGender(), addressVo.getAge(), addressVo.getPhone(), addressVo.getAddress());

		writer.write(command);
		writer.flush();

		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String command = String.format("DELETE:NAME=%s\n", addressVo.getName());

		writer.write(command);
		writer.flush();

		return 0;
	}
}
