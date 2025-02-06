package com.barunsw.ojt.yjkim.day13;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.day13.ClientSocketHandler;

public class ObjectClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private String namespace = "com.barunsw.ojt.yjkim.day13.SocketAddressDao";

	private Socket clientSocket;
	
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	private static Map<Integer, AddressVo> addressMap = new HashMap<>();
	private static int addressSeq;
	
	public ObjectClientSocketHandler(Socket clientSocket) throws Exception {
		LOGGER.debug("+++ ObjectClientSocketHandler");
		this.clientSocket = clientSocket;
		
		objectOutputStream = 
				new ObjectOutputStream(clientSocket.getOutputStream());
		
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					objectInputStream =
							new ObjectInputStream(clientSocket.getInputStream());
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		});
		inputThread.start();
		inputThread.join();
		LOGGER.debug("--- ObjectClientSocketHandler");
		
	}
	
	@Override
	public void run() {
		LOGGER.debug("+++ ObjectClientHandler run");
		
		Object readObject = null;
		while (true) {
			try {
				LOGGER.debug(String.format("+++ readObject : [%s]", readObject));
				
				readObject = objectInputStream.readObject();
				if (readObject == null) {
					break;
				}
				
				LOGGER.debug(String.format("--- readObject : [%s]", readObject));
				
				if(readObject instanceof SocketCommandVo) {
					SocketCommandVo commandVo = (SocketCommandVo)readObject;
					switch (commandVo.getCmdType()) {
						case SELECT:
							objectHandleSelect();
							break;
						case INSERT:
							objectHandleInsert(commandVo.getAddressVo());
							break;
						case UPDATE:
							objectHandleUpdate(commandVo.getAddressVo());
							break;
						case DELETE:
							objectHandleDelete(commandVo.getAddressVo());
							break;
					}
				}
			
			} catch (EOFException eofe) {
				try {
					objectInputStream = 
							new ObjectInputStream(clientSocket.getInputStream());
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				break;
			}
		}
		LOGGER.debug("--- ObjectClientHandler run");
	}
	
	private void objectHandleSelect() throws Exception{
		LOGGER.debug("+++ objectHandleSelect");
		try (SqlSession session = sqlSessionFactory.openSession()){
			List<AddressVo> list = session.selectList(namespace +".selectAllSocketAddress");
			if(list.size() == 0) {
				list = null;
			}
			objectOutputStream.writeObject(list);
			LOGGER.debug("objectOutputStream.objectHandleSelect.writeObject(list)");
			objectOutputStream.flush();
			LOGGER.debug("objectOutputStream.objectHandleSelect.flush()");
			objectOutputStream.writeObject(null);

		}
		LOGGER.debug("--- objectHandleSelect");
	}
	
	private void objectHandleInsert(AddressVo addressVo) {
		LOGGER.debug("+++ objectHandleInsert");
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.insert(namespace +".insertSocketAddress", addressVo);
			session.commit();
		}
		LOGGER.debug("--- objectHandleInsert");

	}
	
	private void objectHandleUpdate(AddressVo addressVo) {
		LOGGER.debug("+++ objectHandleUpdate");
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.insert(namespace +".updateSocketAddress", addressVo);
			session.commit();
		}
		LOGGER.debug("--- objectHandleUpdate");
	}
	
	private void objectHandleDelete(AddressVo addressVo) {
		LOGGER.debug("+++ objectHandleDelete");
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.insert(namespace +".deleteSocketAddress", addressVo);
			session.commit();
		}
		LOGGER.debug("--- objectHandleDelete");
	}
}
