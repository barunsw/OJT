package com.barunsw.ojt.yjkim.day13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UDPAddressImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(UDPAddressImpl.class);

	private DatagramSocket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private int port;
	private String host;
	public UDPAddressImpl(String host, int port) throws SocketException {
		LOGGER.debug("+++ UPDAddressImpl");
		this.host = host;
		this.port = port;
		clientSocket = new DatagramSocket(port);
		LOGGER.debug("--- UPDAddressImpl");
	}
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		LOGGER.debug("+++ selectAddressList");
        InetAddress ia = InetAddress.getByName(host);
		String command = String.format("SELECT:\n");
		DatagramPacket dp = new DatagramPacket(command.getBytes(),command.getBytes().length,ia,50000);
		clientSocket.send(dp);
		
		LOGGER.debug("--- selectAddressList");
		List<AddressVo> list = new ArrayList<>();
		byte[] bf = new byte[50000];
		StringBuffer buffer = new StringBuffer();
		dp = new DatagramPacket(bf,bf.length);
		clientSocket.receive(dp);
		LOGGER.debug("수신된 데이터 : " + new String(dp.getData()).trim());
		String data = new String(dp.getData()).trim();
		if (data.equals("+")) {
			LOGGER.debug(data+"음");
			return list;
		}
		else {
		String[] parsing = data.split("\n");
		for (String oneData : parsing) {
			AddressVo addressVo = new AddressVo();
			String[] paramList = oneData.split(",");
			for (String oneParam : paramList) {
				String[] oneParamData = oneParam.split("=");
				String key = oneParamData[0].trim();
				String val = oneParamData[1].trim();
				LOGGER.debug("key : " + key);
				LOGGER.debug("value : " + val);
				switch (key) {
				case "seq":
					addressVo.setSeq(Integer.parseInt(val));
					break;
				case "name":
					addressVo.setName(val);
					break;
				case "gender":
					try {
						addressVo.setGender(Gender.toGender(val));
					} catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
					break;
				case "age":
					addressVo.setAge(Integer.parseInt(val));
					break;
				case "address":
					addressVo.setAddress(val);
					break;
				}
			}
			LOGGER.debug(addressVo.toString());
			list.add(addressVo);

		}
	}
		
		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("+++ insertAddress");
        InetAddress ia = InetAddress.getByName(host);
		String command = String.format("INSERT:SEQ=%d,NAME=%s,GENDER=%s,AGE=%d,ADDRESS=%S\n", 
				addressVo.getSeq(), addressVo.getName(),
				addressVo.getGender(), addressVo.getAge(),
				addressVo.getAddress());		
		LOGGER.debug(command);
		DatagramPacket dp = new DatagramPacket(command.getBytes(),command.getBytes().length,ia,50000);
		clientSocket.send(dp);		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("+++ updateAddress");
        InetAddress ia = InetAddress.getByName(host);
        String command = String.format("UPDATE:SEQ=%d,NAME=%s,GENDER=%s,AGE=%d,ADDRESS=%s\n", 
				addressVo.getSeq(), addressVo.getName(),
				addressVo.getGender(), addressVo.getAge(),
				addressVo.getAddress());	
		DatagramPacket dp = new DatagramPacket(command.getBytes(),command.getBytes().length,ia,50000);
		clientSocket.send(dp);
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("+++ deleteAddress");
        InetAddress ia = InetAddress.getByName(host);
    	String command = String.format("DELETE:SEQ=%d\n", 
    			addressVo.getSeq());
		DatagramPacket dp = new DatagramPacket(command.getBytes(),command.getBytes().length,ia,50000);
		clientSocket.send(dp);
		return 0;
	}

}
