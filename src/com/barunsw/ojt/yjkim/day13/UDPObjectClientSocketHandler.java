package com.barunsw.ojt.yjkim.day13;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mariadb.jdbc.internal.failover.HandleErrorResult;

public class UDPObjectClientSocketHandler extends Thread{
	private static final Logger LOGGER = LogManager.getLogger(UDPObjectClientSocketHandler.class);
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private String namespace = "com.barunsw.ojt.yjkim.day13.SocketAddressDao";
	private DatagramSocket clientSocket;
	private int port;
	byte buffer[] = new byte[512];
	private DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
	private InetAddress inetAddress;
	private ByteArrayOutputStream byteOutputStream;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private ByteArrayInputStream byteArrayInpuStream;
	
	private byte[] bf = new byte[50000];

	public UDPObjectClientSocketHandler(DatagramSocket clientSocket) {
		LOGGER.debug("+++ UDPObjectClientSocketHandler");
		this.clientSocket = clientSocket;
		this.byteOutputStream = new ByteArrayOutputStream();
		try {
			this.objectOutputStream =
					new ObjectOutputStream(byteOutputStream);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				LOGGER.debug("+++ UDPClientSocketHandler run ");
				datagramPacket = new DatagramPacket(bf,bf.length);
				clientSocket.receive(datagramPacket);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bf);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				Object o = null;
				SocketCommandVo socketCommandvo = new SocketCommandVo();
				try {
					 o = objectInputStream.readObject();
					 LOGGER.debug(o);
					 socketCommandvo = (SocketCommandVo)o;
				} catch (ClassNotFoundException cnfe) {
					LOGGER.error(cnfe.getMessage(), cnfe);
				}finally {
					if (objectInputStream != null) {
						objectInputStream.close();
					}
				}
				
				inetAddress = datagramPacket.getAddress();
				LOGGER.debug("수신 데이터 : " + socketCommandvo.toString());
				switch (socketCommandvo.getCmdType()) {
					case SELECT:
					try {
						UDPObjecthandleAllSelect();
					} catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
						break;
					case INSERT:
						UDPObjecthandleInsert(socketCommandvo.getAddressVo());
						break;
					case UPDATE:
						UDPObjecthandleUpdate(socketCommandvo.getAddressVo());
						break;
					case DELETE:
						UDPObjecthandleDelete(socketCommandvo.getAddressVo());
						break;
				}
			LOGGER.debug("UDPClientSocketHandler run ");

			}
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}

	private void UDPObjecthandleInsert(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ StreamhandleInsert"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.insert(namespace + ".insertSocketAddress", addressVo);
			session.commit();
		}
		LOGGER.debug(String.format("--- StreamhandleInsert"));

	}
	
	private void UDPObjecthandleUpdate(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleUpdate"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.update(namespace + ".updateSocketAddress",addressVo);
			session.commit();
		}
		LOGGER.debug(String.format("--- handleUpdate"));
	}
	
	private void UDPObjecthandleDelete(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ StreamhandleDelete"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.delete(namespace + ".deleteSocketAddress",addressVo);
			session.commit();
		}
		LOGGER.debug(String.format("--- StreamhandleDelete"));
	}
	
	private void UDPObjecthandleAllSelect() throws Exception {
		LOGGER.debug(String.format("+++ UDPhandleAllSelect "));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			List<AddressVo> list = session.selectList(namespace + ".selectAllSocketAddress");
			LOGGER.debug(list.toString() + "list입니다");
		
			port = datagramPacket.getPort();
			objectOutputStream.writeObject(list);
			objectOutputStream.flush();
			
			byte[] hanldersend = byteOutputStream.toByteArray();
			datagramPacket = new DatagramPacket(hanldersend, hanldersend.length, inetAddress, port);
			clientSocket.send(datagramPacket);
			
			
		LOGGER.debug(String.format("--- UDPhandleAllSelect "));
	 }
  }
	
	
}
