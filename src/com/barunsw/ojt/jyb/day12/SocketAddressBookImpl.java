package com.barunsw.ojt.jyb.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;

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
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		String command = "SELECT\n";
		List<AddressVo> list = new ArrayList<>();

		try {
			writer.write(command);
			writer.flush();
		}
		catch (IOException e) {
			LOGGER.error("Error writing SELECT command", e);
			return list;
		}

		try {
			String readLine;

			while ((readLine = reader.readLine()) != null) {
				if (readLine.trim().isEmpty()) {
					break;
				}
				LOGGER.debug("received: " + readLine);

				AddressVo vo = null;
				try {
					vo = parseResponse(readLine);
				}
				catch (Exception e) {
					LOGGER.error("Error parsing response: " + readLine, e);
				}

				if (vo != null) {
					list.add(vo);
				}
			}
		}
		catch (IOException e) {
			LOGGER.error("Error reading response", e);
		}

		return list;
	}

	private AddressVo parseResponse(String response) throws Exception {
		if (response == null || response.trim().isEmpty()) {
			throw new IllegalArgumentException("Response is empty or null.");
		}

		AddressVo addressVo = new AddressVo();

		String[] params = response.split(",");

		for (String param : params) {
			String[] keyValue = param.split("=");

			if (keyValue.length < 2) { // 배열 길이 체크 추가
				throw new IllegalArgumentException("Invalid response format: " + param);
			}

			String key = keyValue[0].trim();
			String value = keyValue[1].trim();

			switch (key) {
			case "SEQ":
				addressVo.setSeq(Integer.parseInt(value));
				break;
			case "NAME":
				addressVo.setName(value);
				break;
			case "AGE":
				addressVo.setAge(Integer.parseInt(value));
				break;
			case "GENDER":
				addressVo.setGender(Gender.toGender(value));
				break;
			case "ADDRESS":
				addressVo.setAddress(value);
				break;
			case "PHONE":
				addressVo.setPhone(value);
				break;
			}
		}
		return addressVo;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String command = String.format("INSERT:NAME=%s,AGE=%s,GENDER=%s,ADDRESS=%s,PHONE=%s\n", addressVo.getName(),
				addressVo.getAge(), addressVo.getGender(), addressVo.getAddress(), addressVo.getPhone());

		LOGGER.debug("명령어: " + command);
		writer.write(command);
		writer.flush();
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String command = String.format("UPDATE:NAME=%s,AGE=%d,GENDER=%s,ADDRESS=%s,PHONE=%s\n", addressVo.getName(),
				addressVo.getAge(), addressVo.getGender(), addressVo.getAddress(), addressVo.getPhone());

		writer.write(command);
		writer.flush();

		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String command = String.format("DELETE:NAME=%s,AGE=%d,GENDER=%s,ADDRESS=%s,PHONE=%s\n", addressVo.getName(),
				addressVo.getAge(), addressVo.getGender(), addressVo.getAddress(), addressVo.getPhone());

		writer.write(command);
		writer.flush();

		writer.write(command);
		writer.flush();

		return 0;
	}

	public void close() {

	}
}
