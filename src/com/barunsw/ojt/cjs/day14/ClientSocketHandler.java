package com.barunsw.ojt.cjs.day14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientSocketHandler.class);
	AddressBookInterface addressBookIf ;
	private Socket clientSocket;
	
	public ClientSocketHandler(Socket clientSocket) throws Exception {
		//생성자를 통해 클라이언트의 접속 소켓을 받는다
		interfaceInfo();
		//서버에서 사용할 인터페이스 구현체받는다
		this.clientSocket = clientSocket;
	}

	private void interfaceInfo() throws Exception {
		
		String className = ServerMain.serverProperties.getProperty("address_if_class");
		LOGGER.debug(className);
		Object o = null;
		if (className.contains("SocketAddressBookImpl")) {
			String serverHost = ServerMain.serverProperties.getProperty("host");
			// String serverHost = null;
			LOGGER.debug(serverHost + "");
			// server.port를 가져온다.
			int serverPort = Integer.parseInt(ServerMain.serverProperties.getProperty("port"));
			LOGGER.debug(serverPort + "");

			Constructor c = Class.forName(className).getConstructor(String.class, Integer.class);
			o = c.newInstance(serverHost, serverPort);
			LOGGER.debug(o + "");
		} else {
			o = Class.forName(className).newInstance();
			LOGGER.debug(o + "");
		}
		if (o != null && o instanceof AddressBookInterface) {
			addressBookIf = (AddressBookInterface) o;
			LOGGER.debug(addressBookIf + "");
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
			}
		}
		return addressVo;
	}

	@Override
	public void run() {
		
		LOGGER.debug("ClientSocketHandler run!!!");
		LOGGER.debug("===================================================");

		LOGGER.debug(addressBookIf +"");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));) {

			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("+++ readLine:%s", readLine));

				String[] cmdSplit = readLine.split(":");

				String cmd = cmdSplit[0];
				switch (cmd) {
				case "SELECT":
					writer.write(handleSelect() +"\n");
					writer.flush();
					break;
				case "INSERT":
					AddressVo addressVo = parseCmd(cmdSplit[1]);
					writer.write(handleInsert(addressVo));
					writer.flush();
					break;
				case "DELETE":
					addressVo = parseCmd(cmdSplit[1]);
					writer.write(handleDelete(addressVo));
					writer.flush();
				case "UPDATE":
					addressVo = parseCmd(cmdSplit[1]);
					writer.write(handleUpadte(addressVo));
					writer.flush();
				}
			}
		} 
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		LOGGER.debug("===================================================");
		LOGGER.debug("ClientSocketHandler finish!!");
	}

	private String handleSelect() throws Exception {
			List<AddressVo> addressList = addressBookIf.selectAddressList(new AddressVo());
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("SELECT:");
			for (AddressVo address : addressList) {
				stringBuffer
				.append("NAME=" + address.getName() + ",")
				.append("AGE=" + address.getAge() + ",")
				.append("GENDER=" + address.getGender() + ",")
				.append("ADDRESS=" + address.getAddress() + "&");
			}
			LOGGER.debug(stringBuffer.toString());
			return stringBuffer.toString();
	}

	private int handleInsert(AddressVo addressVo) throws Exception {
		return addressBookIf.insertAddress(addressVo);
	}

	private int handleUpadte(AddressVo addressVo) throws Exception {
		return addressBookIf.updateAddress(addressVo);
	}

	private int handleDelete(AddressVo addressVo) throws Exception {
		return addressBookIf.deleteAddress(addressVo);
	}
}
