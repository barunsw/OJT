package com.barunsw.ojt.gtkim.day13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.gtkim.day12.AddressBookInterface;
import com.barunsw.ojt.gtkim.day12.AddressVo;

public class DatagramSocketAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(DatagramSocketAddressBookImpl.class);
	
	public final int CLIENT_PORT = 65432;
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	private InetAddress hostaddress;
	private int hostport;
	
	public DatagramSocketAddressBookImpl(String host, int port) throws Exception {
		this.hostaddress = InetAddress.getByName(host);
		this.hostport 	 = port;
		
		this.socket = new DatagramSocket(CLIENT_PORT);
	}
	
	private List<AddressVo> parseData(String data) throws Exception { 
		List<AddressVo> addressList = new ArrayList<>();
		
		String[] dataList = data.split("\n");
		for (String oneLine : dataList) {
			AddressVo oneAddress = new AddressVo();
			String[] paramList = oneLine.split(",");
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
			addressList.add(oneAddress);
		}
		
		return addressList;
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {		
		String command ="SELECT:NAME=ALL\n";
		byte[] sendMsg = command.getBytes();
		
		packet = new DatagramPacket(sendMsg, sendMsg.length, hostaddress, hostport);
		socket.send(packet);
		
		LOGGER.debug("Select 데이터를 보냈습니다");
		
		byte[] recvMsg = new byte[1024];
		
		packet = new DatagramPacket(recvMsg, recvMsg.length);
		socket.receive(packet);
		
		String getData = new String(packet.getData()).trim();
		LOGGER.debug("Packet을 수신하였습니다." + getData);
		
		return parseData(getData);
	}
	
	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String command = String.format("INSERT:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s\n",
				addressVo.getName(), addressVo.getGender().toString(), Integer.toString(addressVo.getAge()),
				addressVo.getPhone(), addressVo.getAddress());
		
		byte[] buf = command.getBytes();
		
		packet = new DatagramPacket(buf, buf.length, hostaddress, hostport);
		socket.send(packet);
		
		LOGGER.debug("Insert Data send");
		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String command = String.format("UPDATE:NAME=%s,GENDER=%s,AGE=%s,PHONE=%s,ADDRESS=%s,SEQ=%s\n",
				addressVo.getName(), addressVo.getGender().toString(), Integer.toString(addressVo.getAge()),
				addressVo.getPhone(), addressVo.getAddress(), Integer.toString(addressVo.getSeq()));
		
		byte[] buf = command.getBytes();
		
		packet = new DatagramPacket(buf, buf.length, hostaddress, hostport);
		socket.send(packet);
		
		LOGGER.debug("Update Data send");
		
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String command = String.format("DELETE:SEQ=%s\n", Integer.toString(addressVo.getSeq()));
		byte[] buf = command.getBytes();
		
		packet = new DatagramPacket(buf, buf.length, hostaddress, hostport);
		socket.send(packet);
		
		LOGGER.debug("Delete Data send");
		
		return 0;
	}
	
	private void close() {
		if (socket != null) {
			socket.close();
		}
	}

}
