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
			return list; // 오류 발생 시 빈 리스트 반환
		}

		try {
			String readLine;
			while ((readLine = reader.readLine()) != null) {
				if ("END_OF_DATA".equals(readLine.trim())) {
					break; // END_OF_DATA를 받으면 루프 종료
				}
				LOGGER.debug("received: " + readLine);

				// 데이터를 AddressVo 객체로 변환하여 리스트에 추가
				AddressVo vo = null;
				try {
					vo = parseResponse(readLine);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			return null;
		}

		AddressVo addressVo = new AddressVo();
		String[] params = response.split(",");

		for (String param : params) {
			String[] keyValue = param.split("=");
			if (keyValue.length != 2) {
				continue;
			}

			String key = keyValue[0].trim();
			String value = keyValue[1].trim();

			switch (key) {
			case "NAME":
				addressVo.setName(value);
				break;
			case "AGE":
				try {
					addressVo.setAge(Integer.parseInt(value));
				}
				catch (NumberFormatException e) {
					LOGGER.error("AGE 변환 오류: {}", value, e);
				}
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

		try {
			writer.write(command);
			writer.flush();
			LOGGER.debug("서버로 명령어가 전송되었습니다.");

			// END_OF_DATA 추가
			writer.write("END_OF_DATA\n");
			writer.flush();
			LOGGER.debug("END_OF_DATA가 서버로 전송되었습니다.");

		}
		catch (IOException e) {
			LOGGER.error("서버로 데이터를 전송하는 중 IOException이 발생했습니다: ", e);
			throw new Exception("서버로 데이터를 전송하는 중 오류가 발생했습니다.", e);
		}

		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String command = String.format("UPDATE:NAME=%s,AGE=%d,GENDER=%s,ADDRESS=%s,PHONE=%s\n", addressVo.getName(),
				addressVo.getAge(), addressVo.getGender(), addressVo.getAddress(), addressVo.getPhone());

		writer.write(command);
		writer.flush();

		// END_OF_DATA 추가
		writer.write("END_OF_DATA\n");
		writer.flush();
		LOGGER.debug("END_OF_DATA written");

		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String command = String.format("DELETE:NAME=%s,AGE=%d,GENDER=%s,ADDRESS=%s,PHONE=%s\n", addressVo.getName(),
				addressVo.getAge(), addressVo.getGender(), addressVo.getAddress(), addressVo.getPhone());

		writer.write(command);
		writer.flush();

		// END_OF_DATA 추가
		writer.write("END_OF_DATA\n");
		writer.flush();
		LOGGER.debug("END_OF_DATA written");

		return 0;
	}

	public void close() {

	}
}
