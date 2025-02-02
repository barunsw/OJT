package com.barunsw.ojt.iwkim.day13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.AddressBookInterface;
import com.barunsw.ojt.iwkim.common.PersonVO;

public class UDPAddressBookImpl implements AddressBookInterface {
	private static Logger LOGGER = LogManager.getLogger(UdpTestClient.class);

	private DatagramSocket socket;
	private SocketAddress dest;

	public UDPAddressBookImpl(String host, int port) {
		
		try {
			socket = new DatagramSocket();
			dest = new InetSocketAddress(host, port);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}


	@Override
	public List<PersonVO> selectList() throws Exception {
		LOGGER.info("+++ selectList()");

		List<PersonVO> addressList = new ArrayList<>();
		byte[] command = "SELECT:ALL\n".getBytes();
		DatagramPacket sendPacket = new DatagramPacket(command, command.length, dest);
		socket.send(sendPacket);

		byte[] buf = new byte[1024];
		DatagramPacket recvPacket = new DatagramPacket(buf, buf.length); 
		socket.receive(recvPacket);

		String receivedData = new String(recvPacket.getData()); 
		LOGGER.info("DB에서 받은 데이터 : " + receivedData);

		if (receivedData.startsWith("SELECT")) {
			String[] addressInfoList = receivedData.split(":");


			String[] receivedList = addressInfoList[1].trim().split("~"); 

			for (String data : receivedList) {
				LOGGER.info("개인정보 : " + data);
				PersonVO onePersonInfo = new PersonVO();
				String[] columnList = data.split(",");

				for (String columnData : columnList) {
					String[] column = columnData.split("=");

					switch (column[0]) {
					case "NAME":
						onePersonInfo.setName(column[1]);
						break;
					case "AGE":
						onePersonInfo.setAge(Integer.parseInt(column[1]));
						break;
					case "GENDER":
						onePersonInfo.setGender(column[1]);
						break;
					case "PHONE":
						onePersonInfo.setPhone(column[1]);
						break;
					case "ADDRESS":
						onePersonInfo.setAddress(column[1]);
						break;
					default:
						LOGGER.error("잘못 입력되었습니다.");
						LOGGER.error("잘못입력된 값 column[0] : " + column[0]);
					}
				}

				addressList.add(onePersonInfo);
			}
		}
		LOGGER.info("--- selectList()");
		return addressList;
		
	}

	@Override
	public int insertPerson(PersonVO param) throws Exception {
		LOGGER.info("+++ insertPerson()");

		byte[] command = String.format("INSERT:NAME=%s,AGE=%s,GENDER=%s,PHONE=%s,ADDRESS=%s\n" 
				, param.getName()
				, String.valueOf(param.getAge())
				, param.getGender()
				, param.getPhone()
				, param.getAddress())
				.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(command, command.length, dest);
		
		socket.send(sendPacket);

		byte[] buf = new byte[1024];
		DatagramPacket recvPacket = new DatagramPacket(buf, buf.length); 
		socket.receive(recvPacket);

		String receivedData = new String(recvPacket.getData()); 
		LOGGER.info("DB에서 받은 데이터 : " + receivedData);
		int queryExcResult = Integer.parseInt(receivedData.trim());
		LOGGER.info("--- insertPerson()");
		return queryExcResult;
	}

	@Override
	public int updatePerson(PersonVO param) throws Exception {
		LOGGER.info("+++ updatePerson()");

		byte[] command = String.format("UPDATE:NAME=%s,AGE=%s,GENDER=%s,PHONE=%s,ADDRESS=%s\n" 
				, param.getName()
				, String.valueOf(param.getAge())
				, param.getGender()
				, param.getPhone()
				, param.getAddress())
				.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(command, command.length, dest);
		
		socket.send(sendPacket);

		byte[] buf = new byte[1024];
		DatagramPacket recvPacket = new DatagramPacket(buf, buf.length); 
		socket.receive(recvPacket);

		String receivedData = new String(recvPacket.getData()); 
		LOGGER.info("DB에서 받은 데이터 : " + receivedData);
		int queryExcResult = Integer.parseInt(receivedData.trim());
		LOGGER.info("--- updatePerson()");
		return queryExcResult;
	}

	@Override
	public int deletePerson(String name) throws Exception {
		LOGGER.info("+++ deletePerson()");

		byte[] command = String.format("DELETE:NAME=%s\n", name).getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(command, command.length, dest);
		
		socket.send(sendPacket);

		byte[] buf = new byte[1024];
		DatagramPacket recvPacket = new DatagramPacket(buf, buf.length); 
		socket.receive(recvPacket);

		String receivedData = new String(recvPacket.getData()); 
		LOGGER.info("DB에서 받은 데이터 : " + receivedData);
		int queryExcResult = Integer.parseInt(receivedData.trim());
		LOGGER.info("--- deletePerson()");
		return queryExcResult;
	}
}
