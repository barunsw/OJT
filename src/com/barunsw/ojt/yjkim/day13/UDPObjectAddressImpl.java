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
	private ObjectOutputStream objectOutputstream;
	private ObjectInputStream objectInputstream;
	private byte[] receiveMsg = new byte[50000];
	
	public UDPObjectAddressImpl(String host, int port) throws SocketException {
		LOGGER.debug("+++ UPDObjectAddressImpl");
		this.host = host;
		this.port = port;
		clientSocket = new DatagramSocket(port);
		LOGGER.debug("--- UPDObjectAddressImpl");
		this.byteOutputStream = new ByteArrayOutputStream();
		try {
			this.objectOutputstream =
					new ObjectOutputStream(byteOutputStream);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					objectInputstream = 
							new ObjectInputStream(new ByteArrayInputStream(receiveMsg));
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		});
		inputThread.start();
		try {
			inputThread.join();
		} catch (InterruptedException ie) {
			LOGGER.error(ie.getMessage(), ie);
		}
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> list = new ArrayList<AddressVo>();
        InetAddress ia = InetAddress.getByName(host);

		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.SELECT);
		objectOutputstream.writeObject(vo);
		objectOutputstream.flush();
		
		byte[] SendMsg = byteOutputStream.toByteArray();
		DatagramPacket dp = new DatagramPacket(SendMsg, SendMsg.length, ia, 50000);
		clientSocket.send(dp);
		
		return null;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		return 0;
	}

}
