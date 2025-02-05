package com.barunsw.ojt.yjkim.day13;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UDPObjectAddressImpl implements AddressBookInterface{
	private static final Logger LOGGER = LogManager.getLogger(UDPObjectAddressImpl.class);
	private DatagramSocket clientSocket;
	private int port;
	private String host;
	private ByteArrayOutputStream byteOutputStream;
	//private ObjectOutputStream objectOutputstream;
	private ObjectInputStream objectInputstream;
	private byte[] receiveMsg = new byte[50000];
	private DatagramPacket datagramPacket;
	private InetAddress inetAddress;
	
	public UDPObjectAddressImpl(String host, int port) throws SocketException {
		LOGGER.debug("+++ UPDObjectAddressImpl");
		this.host = host;
		this.port = port;
		clientSocket = new DatagramSocket(port);
		this.byteOutputStream = new ByteArrayOutputStream();
		
		LOGGER.debug("--- UPDObjectAddressImpl");
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		LOGGER.debug("+++ UDPObjectselectAddressList");
		List<AddressVo> list = new ArrayList<AddressVo>();
        inetAddress = InetAddress.getByName(host);

		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.SELECT);
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
				ObjectOutputStream objectOutputstream = new ObjectOutputStream(byteOutputStream)){
			objectOutputstream.writeObject(vo);
			objectOutputstream.flush();
			byte[] impleSend = byteOutputStream.toByteArray();
			DatagramPacket dp = new DatagramPacket(impleSend, impleSend.length, inetAddress, 50000);
			clientSocket.send(dp);
		} 
		byte[] impleSend = byteOutputStream.toByteArray();
		DatagramPacket dp = new DatagramPacket(impleSend, impleSend.length, inetAddress, 50000);
		clientSocket.send(dp);
		
		
		
		byte[] bf = new byte[50000];
		datagramPacket = new DatagramPacket(bf,bf.length);
		clientSocket.receive(datagramPacket);
		ByteArrayInputStream byteArrayInputStrearm = new ByteArrayInputStream(bf);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStrearm);
		Object o = null;
		SocketCommandVo socketCommandvo = new SocketCommandVo();
		
		try {
			o = objectInputStream.readObject();
			if(o instanceof List) {
				list = (List)o;
			}
			LOGGER.debug(list);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
			if(objectInputStream != null) {
				objectInputStream.close();
			}
		}
		LOGGER.debug("--- UDPObjectselectAddressList");

		return list;
		
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("+++ UDPObjectinsertAddress");

		inetAddress = InetAddress.getByName(host);

		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.INSERT);
		vo.setAddressVo(addressVo);
		LOGGER.debug(vo.getAddressVo());
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
				ObjectOutputStream objectOutputstream = new ObjectOutputStream(byteOutputStream)){
			objectOutputstream.writeObject(vo);
			objectOutputstream.flush();
			byte[] impleSend = byteOutputStream.toByteArray();
			DatagramPacket dp = new DatagramPacket(impleSend, impleSend.length, inetAddress, 50000);
			clientSocket.send(dp);
		} 
		
	
		LOGGER.debug("--- UDPObjectinsertAddress");

		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("+++ UDPObjectupdateAddress");

		inetAddress = InetAddress.getByName(host);

		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.UPDATE);
		vo.setAddressVo(addressVo);
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
				ObjectOutputStream objectOutputstream = new ObjectOutputStream(byteOutputStream)){
			objectOutputstream.writeObject(vo);
			objectOutputstream.flush();
			byte[] impleSend = byteOutputStream.toByteArray();
			DatagramPacket dp = new DatagramPacket(impleSend, impleSend.length, inetAddress, 50000);
			clientSocket.send(dp);
		} 
		
		byte[] impleSend = byteOutputStream.toByteArray();
		DatagramPacket dp = new DatagramPacket(impleSend, impleSend.length, inetAddress, 50000);
		clientSocket.send(dp);
		LOGGER.debug("--- UDPObjectinsertAddress");

		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("+++ UDPObjectdeleteAddress");

		inetAddress = InetAddress.getByName(host);

		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.DELETE);
		vo.setAddressVo(addressVo);
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
				ObjectOutputStream objectOutputstream = new ObjectOutputStream(byteOutputStream)){
			objectOutputstream.writeObject(vo);
			objectOutputstream.flush();
			byte[] impleSend = byteOutputStream.toByteArray();
			DatagramPacket dp = new DatagramPacket(impleSend, impleSend.length, inetAddress, 50000);
			clientSocket.send(dp);
		} 
		
		byte[] impleSend = byteOutputStream.toByteArray();
		DatagramPacket dp = new DatagramPacket(impleSend, impleSend.length, inetAddress, 50000);
		clientSocket.send(dp);
		LOGGER.debug("--- UDPObjectdeleteAddress");

		return 0;
	}

}
