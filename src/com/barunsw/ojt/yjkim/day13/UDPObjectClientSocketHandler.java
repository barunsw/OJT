package com.barunsw.ojt.yjkim.day13;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UDPObjectClientSocketHandler extends Thread{
	private static final Logger LOGGER = LogManager.getLogger(UDPObjectClientSocketHandler.class);
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private String namespace = "com.barunsw.ojt.yjkim.day13.SocketAddressDao";
	private DatagramSocket clientSocket;
	private int port;
	byte buffer[] = new byte[512];
	DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
	InetAddress ia;
	
	public UDPObjectClientSocketHandler(DatagramSocket clientSocket) {
		LOGGER.debug("+++ UDPObjectClientSocketHandler");
		this.clientSocket = clientSocket;
		
		LOGGER.debug("--- UDPObjectClientSocketHandler");
	}
	
	public AddressVo parseCmd(String paramStr) {
		AddressVo addressVo = new AddressVo();
		
		String[] paramList = paramStr.split(",");
		for (String oneParam : paramList) {
			String[] oneParamData = oneParam.split("=");
			String key = oneParamData[0].trim();
			String val = oneParamData[1].trim();
			
			switch (key) {
			case "SEQ":
				addressVo.setSeq(Integer.parseInt(val));
			case "NAME":
				addressVo.setName(val);
				break;
			case "GENDER":
				try {
					addressVo.setGender(Gender.toGender(val));
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
				break;
			case "AGE":
				addressVo.setAge(Integer.parseInt(val));
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
		try {
			while (true) {
				LOGGER.debug("+++ UDPClientSocketHandler run ");
				byte[] bf = new byte[50000];
				dp = new DatagramPacket(bf,bf.length);
				clientSocket.receive(dp);
				String str = new String(dp.getData());
				ia = dp.getAddress();
				LOGGER.debug("수신 데이터 : " + str);
				String[] cmdSplit = str.split(":");
				AddressVo addressVo;
				LOGGER.debug(cmdSplit[0]);
				String cmd = cmdSplit[0];
				switch (cmd) {
					case "SELECT":
					try {
						UDPObjecthandleAllSelect();
					} catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
						break;
					case "INSERT":
						addressVo = parseCmd(cmdSplit[1]);
						UDPObjecthandleInsert(addressVo);
						break;
					case "UPDATE":
						addressVo = parseCmd(cmdSplit[1]);
						UDPObjecthandleUpdate(addressVo);
						break;
					case "DELETE":
						addressVo = parseCmd(cmdSplit[1]);
						UDPObjecthandleDelete(addressVo);
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
		
			port = dp.getPort();
			LOGGER.debug("IA : " +ia +"port "+port);
			StringBuffer buf = new StringBuffer();
			/*for (AddressVo addressvo : list) {
				buf.append(addressvo.toString()+"\n");
			}*/
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size()-1) {
					buf.append(list.get(i).toString());
				}else {
					buf.append(list.get(i).toString()+"\n");
				}
			}
			
			byte[] bf = buf.toString().getBytes();
			byte[] b = "+".getBytes();
			LOGGER.debug("LIST"+ buf.toString().getBytes(), buf.toString().length());
			if (list.size() > 0) {
				dp = new DatagramPacket(bf,bf.length,ia,port);
				clientSocket.send(dp);
			} else {
				dp = new DatagramPacket(b,b.length,ia,port);
				clientSocket.send(dp);
			}
		LOGGER.debug(String.format("--- UDPhandleAllSelect "));
	 }
  }
	
	
}
