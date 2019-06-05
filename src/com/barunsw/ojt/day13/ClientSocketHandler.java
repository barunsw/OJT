package com.barunsw.ojt.day13;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	
	private Socket clientSocket;
	
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	private static Map<Integer, AddressVo> addressMap = new HashMap<>();
	private static int addressSeq;
	
	public ClientSocketHandler(Socket clientSocket) throws Exception {
		LOGGER.debug("+++ ClientSocketHandler");
		
		this.clientSocket = clientSocket;
		
		this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					objectInputStream = 
							new ObjectInputStream(clientSocket.getInputStream());
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		});
		inputThread.start();
		// thread 소멸을 기다린다.
		inputThread.join();
		
		LOGGER.debug("--- ClientSocketHandler");		
	}
	
	private AddressVo parseCmd(String paramStr) {
		AddressVo addressVo = new AddressVo();
		
		String[] paramList = paramStr.split(",");
		for (String oneParam : paramList) {
			String[] oneParamData = oneParam.split("=");
			String key = oneParamData[0].trim();
			String val = oneParamData[1].trim();
			
			switch (key) {
			case "NAME":
				addressVo.setName(val);
				break;
			case "AGE":
				addressVo.setAge(Integer.parseInt(val));
				break;
			}
		}
		
		return addressVo;
	}
	
	@Override
	public void run() {
		LOGGER.debug("+++ ClientSocketHandler run");
			
		Object readObject = null;
		while (true) {
			try {
				LOGGER.debug(String.format("+++ readObject:%s", readObject));

				readObject = objectInputStream.readObject();
				if (readObject == null) {
					break;
				}
				
				LOGGER.debug(String.format("--- readObject:%s", readObject));

				if (readObject instanceof SocketCommandVo) {
					SocketCommandVo commandVo = (SocketCommandVo)readObject;
					switch (commandVo.getCmdType()) {
					case SELECT :
						handleSelect(commandVo.getAddressVo());
						break;
					case INSERT :
						handleInsert(commandVo.getAddressVo());
						break;
					case UPDATE :
						handleUpdate(commandVo.getAddressVo());
						break;
					case DELETE :
						handleDelete(commandVo.getAddressVo());
						break;
					}
				}
			}
			catch (EOFException eofe) {
				LOGGER.debug("socket.isConnected:" + clientSocket.isConnected());
				LOGGER.debug("socket.isClosed:" + clientSocket.isClosed());
				
				try {
					objectInputStream = 
							new ObjectInputStream(clientSocket.getInputStream());
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				break;
			}
		}
		
		LOGGER.debug("--- ClientSocketHandler run");
	}
	
	private void handleSelect(AddressVo addressVo) throws Exception {
		LOGGER.debug(String.format("+++ handleSelect [%s]", addressVo));

		List<AddressVo> addressList = new ArrayList<>();
		
		Collection<AddressVo> addressColl = addressMap.values();
		for (AddressVo oneAddressVo : addressColl) {
			addressList.add(oneAddressVo);
		}
		
		objectOutputStream.writeObject(addressList);
		objectOutputStream.flush();
	}
	
	private void handleInsert(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleInsert [%s]", addressVo));
		addressVo.setSeq(++addressSeq);
		
		addressMap.put(addressSeq, addressVo);
	}
	
	private void handleUpdate(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleUpdate [%s]", addressVo));

		addressMap.put(addressVo.getSeq(), addressVo);
	}	
	
	private void handleDelete(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleDelete [%s]", addressVo));

		int seq = addressVo.getSeq();
		if (addressMap.containsKey(seq)) {
			addressMap.remove(seq);
		}
	}
}
