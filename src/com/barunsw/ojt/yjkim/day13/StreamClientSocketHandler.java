package com.barunsw.ojt.yjkim.day13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StreamClientSocketHandler extends Thread{
	private static final Logger LOGGER = LogManager.getLogger(StreamClientSocketHandler.class);
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private String namespace = "com.barunsw.ojt.yjkim.day13.SocketAddressDao";

	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public StreamClientSocketHandler(Socket clientSocket) throws IOException {
		LOGGER.debug("+++ StreamClientSocketHandler 생성자");
		this.clientSocket = clientSocket;
		writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
		LOGGER.debug("--- StreamClientSocketHandler 생성자");
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
				break;
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
		while (true) {
			LOGGER.debug("+++ StreamClientSocketHandler run");
				try {					
						String readLine = null;
						readLine = reader.readLine();
						LOGGER.debug(String.format("+++ readLine:%s", readLine));
						
						String[] cmdSplit = readLine.split(":");
						AddressVo addressVo;
						LOGGER.debug(cmdSplit[0]);
						String cmd = cmdSplit[0];
						switch (cmd) {
						case "SELECT":
							streamhandleAllSelect();
							break;
						case "INSERT":
							addressVo = parseCmd(cmdSplit[1]);
							streamhandleInsert(addressVo);
							break;
						case "UPDATE":
							addressVo = parseCmd(cmdSplit[1]);
							streamhandleUpdate(addressVo);
							break;
						case "DELETE":
							addressVo = parseCmd(cmdSplit[1]);
							streamhandleDelete(addressVo);
							break;
							
						}
					}
					catch (Exception ex) {
						try {
							reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						} catch (IOException ioe) {
							LOGGER.error(ioe.getMessage(), ioe);
						}
						LOGGER.error(ex.getMessage(), ex);
					}
			
		LOGGER.debug("--- StreamClientSocketHandler run");
	}
}
	
	private void streamhandleInsert(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ StreamhandleInsert"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.insert(namespace + ".insertSocketAddress", addressVo);
			session.commit();
		}
		LOGGER.debug(String.format("--- StreamhandleInsert"));

	}
	
	private void streamhandleUpdate(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleUpdate"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.update(namespace + ".updateSocketAddress",addressVo);
			session.commit();
		}
		LOGGER.debug(String.format("--- handleUpdate"));
	}
	
	private void streamhandleDelete(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ StreamhandleDelete"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.delete(namespace + ".deleteSocketAddress",addressVo);
			session.commit();
		}
		LOGGER.debug(String.format("--- StreamhandleDelete"));
	}
	
	private void streamhandleAllSelect() throws Exception {
		LOGGER.debug(String.format("+++ StreamhandleAllSelect "));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			List<AddressVo> list = session.selectList(namespace + ".selectAllSocketAddress");
			LOGGER.debug(list.toString() + "list입니다");
			if(list.size() == 0) {
				list = null;
			}
			if(list != null) {
				LOGGER.debug("list is not null");
				for(AddressVo addressvo : list) {
					LOGGER.debug(addressvo+"addressvo");
					writer.write(addressvo.toString()+"\n");
					writer.flush();
				}
				writer.write("+\n");
				writer.flush();
				LOGGER.debug("writer.write '+' ");
			}
		LOGGER.debug(String.format("--- StreamhandleAllSelect "));
	}
 }
	
}
