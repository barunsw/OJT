package com.barunsw.ojt.gtkim.day13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.gtkim.day12.AddressVo;
import com.barunsw.ojt.gtkim.day12.DBAddressBookImpl;

public class DatagramHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(DatagramHandler.class);
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	private InetAddress address;
	private int port;
	
	private String message;
	
	private DBAddressBookImpl dbController = new DBAddressBookImpl();
	
	public DatagramHandler (DatagramSocket socket, DatagramPacket packet) throws Exception { 
		this.message = new String(packet.getData()).trim();
		this.address = packet.getAddress();
		this.port  	 = packet.getPort();	
		this.socket  = socket;
		
		LOGGER.debug(message + " Handler가 생성되었습니다. ");
	}
	
	@Override 
	public void run() {
		try {
			LOGGER.debug("받은 데이터 : " + message);
			
			String[] splitMessage = message.split(":");
			String command = splitMessage[0];
			AddressVo oneAddress = parseData(splitMessage[1]);
			
			switch (command) {
			case "SELECT" :
				selectHandler(oneAddress);
				break;
			case "INSERT" :
				dbController.insertAddress(oneAddress);
				break;
			case "UPDATE" :
				dbController.updateAddress(oneAddress);
				break;
			case "DELETE" :
				dbController.deleteAddress(oneAddress);
				break;
			default :
				LOGGER.error("알 수 없는 Command 입니다. " + command);
			}
		} 
		catch (Exception e) {
		}

	}
	
	private void selectHandler(AddressVo addressVo) throws Exception {
		List<AddressVo> addressList = dbController.selectAddressList();
		
		StringBuffer buffer = new StringBuffer();
		for (AddressVo oneAddress : addressList) {
			buffer.append("NAME=").append(oneAddress.getName()).append(",")
			   .append("GENDER=").append(oneAddress.getGender()).append(",")
			   .append("AGE=").append(oneAddress.getAge()).append(",")
			   .append("PHONE=").append(oneAddress.getPhone()).append(",")
			   .append("ADDRESS=").append(oneAddress.getAddress()).append(",")
			   .append("SEQ=").append(oneAddress.getSeq()).append("\n");
		}
		byte[] sendMsg = buffer.toString().getBytes();
		
		packet = new DatagramPacket(sendMsg, sendMsg.length, address, port);
		socket.send(packet);
	}
	
	private AddressVo parseData(String param) throws Exception {
		AddressVo vo = new AddressVo();
		
		String[] paramList = param.split(",");
		for (String pair : paramList) {
			String[] pairList = pair.split("=");
			
			String key = pairList[0];
			String val = pairList[1];
			
			switch (key) {
			case "NAME" :
				vo.setName(val);
				break;
			case "GENDER" :
				vo.setGender(Gender.toGender(val));
				break;
			case "AGE" :
				vo.setAge(Integer.parseInt(val));
				break;
			case "PHONE" :
				vo.setPhone(val);
				break;
			case "ADDRESS" :
				vo.setAddress(val);
				break;
			case "SEQ" :
				vo.setSeq(Integer.parseInt(val));
				break;
			default:
				LOGGER.debug("알 수 없는 Key 입니다 : " + key);
			}
		}
		
		return vo;
	}
}
