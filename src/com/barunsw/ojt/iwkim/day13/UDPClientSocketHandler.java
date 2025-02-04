package com.barunsw.ojt.iwkim.day13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonVO;
import com.barunsw.ojt.iwkim.day12.ClientSocketHandler;
import com.barunsw.ojt.iwkim.day12.ServerMain;

public class UDPClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	private DatagramSocket socket;
	
	
	public UDPClientSocketHandler(DatagramSocket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		LOGGER.info("+++ run");
		try {
			while (true) {
				
				// String 생성자에 바이트배열담으면 byte -> String으로 변환
				// 패킷에 담긴 바이트배열을 가져오는 메서드 -> getData()
				byte[] buf = new byte[1024];
				DatagramPacket recvPacket = new DatagramPacket(buf, buf.length); 
				socket.receive(recvPacket);
				int queryExcResult = 0;
				DatagramPacket sendPacket = null;
				
	
				String receivedData = new String(recvPacket.getData()).trim();
				LOGGER.info("Server received Command : " + receivedData);
				String[] command = receivedData.split(":");
				
				switch (command[0]) {
				case "SELECT" :
					LOGGER.info("-------------select-----------");
					List<PersonVO> addressList = UDPServerMain.dbAddressBookImpl.selectList();
					StringBuffer sb = new StringBuffer();
					sb.append("SELECT:");
					for (PersonVO onePersonInfo : addressList) {
						// 동기화가 필요한 멀티쓰레드 환경이므로 StringBuffer를 사용
						sb.append("NAME=" + onePersonInfo.getName() + ",")
						.append("AGE=" + onePersonInfo.getAge() + ",")
						.append("GENDER=" + onePersonInfo.getGender() + ",")
						.append("PHONE=" + onePersonInfo.getPhone() + ",")
						.append("ADDRESS=" + onePersonInfo.getAddress() + "~" );
                
						LOGGER.info("핸들러 개인정보 : " + sb.toString());
					}
					byte[] selectSendData = sb.toString().getBytes();
					sendPacket = new DatagramPacket(selectSendData, selectSendData.length, recvPacket.getAddress(), recvPacket.getPort());
					socket.send(sendPacket);
					LOGGER.info("보낼주소 : {}, {} ", recvPacket.getAddress(), recvPacket.getPort());
					LOGGER.info("send완료!");
					break;
				case "INSERT" :
					LOGGER.info("command[1] : " + command[1]);
					LOGGER.info("upd+++ {}", getPersonInfo(command[1]));
					
					queryExcResult = UDPServerMain.dbAddressBookImpl.insertPerson(getPersonInfo(command[1]));
					LOGGER.info("INSERT RESULT : " + queryExcResult);
					byte[] insertSendData = String.valueOf(queryExcResult).getBytes();
					sendPacket = new DatagramPacket(insertSendData, insertSendData.length, recvPacket.getAddress(), recvPacket.getPort());
					socket.send(sendPacket);
					break;
				case "UPDATE" :
					LOGGER.info("command[1] : " + command[1]);
					LOGGER.info("upd+++ {}", getPersonInfo(command[1]));
					
					queryExcResult = UDPServerMain.dbAddressBookImpl.updatePerson(getPersonInfo(command[1]));
					LOGGER.info("UPDATE RESULT : " + queryExcResult);
					
					byte[] updateSendData = String.valueOf(queryExcResult).getBytes();
					sendPacket = new DatagramPacket(updateSendData, updateSendData.length, recvPacket.getAddress(), recvPacket.getPort());
					socket.send(sendPacket);
					break;
				case "DELETE" :
					LOGGER.info("command[1] : " + command[1]);
					LOGGER.info("upd+++ {}", getPersonInfo(command[1]));
					
					String[] columnData = command[1].split("=");
					LOGGER.info("DELETE NAME : " + columnData[1]);
					queryExcResult = UDPServerMain.dbAddressBookImpl.deletePerson(columnData[1]);
					LOGGER.info("DELETE RESULT : " + queryExcResult);
					
					byte[] deleteSendData = String.valueOf(queryExcResult).getBytes();
					sendPacket = new DatagramPacket(deleteSendData, deleteSendData.length, recvPacket.getAddress(), recvPacket.getPort());
					socket.send(sendPacket);
					break;
				default :
					LOGGER.error("입력값이 잘못 되었습니다.");	
					break;
				}
				
			} 
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		
		LOGGER.info("--- run");
	}
	
	private PersonVO getPersonInfo(String receivedData) {
		String[] columnData = receivedData.split(",");
		PersonVO onePersonInfo = new PersonVO();
		for (String column : columnData) {
			LOGGER.info("column : " + column);
			String[] value = column.split("=");
			switch (value[0]) {
			case "NAME" :
				onePersonInfo.setName(value[1]);
				break;
			case "AGE" :
				onePersonInfo.setAge(Integer.parseInt(value[1]));
				break;
			case "GENDER" :
				onePersonInfo.setGender(value[1]);
				break;
			case "PHONE" :
				onePersonInfo.setPhone(value[1]);
				break;
			case "ADDRESS" :
				onePersonInfo.setAddress(value[1]);
				break;
			default :
				LOGGER.info("잘못 입력되었습니다.");
				break;
			}	
		}

		return onePersonInfo;
	}
}
