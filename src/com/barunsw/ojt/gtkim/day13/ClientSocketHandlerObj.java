package com.barunsw.ojt.gtkim.day13;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.gtkim.day12.AddressVo;
import com.barunsw.ojt.gtkim.day12.DBAddressBookImpl;
import com.barunsw.ojt.gtkim.day12.SocketCommandVo;

public class ClientSocketHandlerObj extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandlerObj.class);
	
	private Socket clientSocket;
	
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	
	private DBAddressBookImpl dbHandler = new DBAddressBookImpl();
	
	public ClientSocketHandlerObj(Socket clientSocket) throws Exception {
		this.clientSocket = clientSocket;
		
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		
		Thread inputThread = new Thread(new Runnable() {
			@Override 
			public void run() {
				try {
					objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
				}
				catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		});
		inputThread.start();
		inputThread.join();
		
		LOGGER.debug("ClientSocketHandleObj 생성 완료!");
	}
	
	@Override 
	public void run() {
		Object readObject = null;
		
		while (true) {
			try {
				readObject = objectInputStream.readObject();
				if (readObject == null) {
					break;
				}
				
				LOGGER.debug("읽은 데이터 : " + readObject);
				
				if (readObject instanceof SocketCommandVo) {
					SocketCommandVo vo = (SocketCommandVo) readObject;
					
					switch (vo.getCmdType()) {
					case SELECT :
						List <AddressVo> addressList = dbHandler.selectAddressList();
						objectOutputStream.writeObject(addressList);
						// 끝을 알리는 신호
						objectOutputStream.writeObject(new AddressVo());;
						objectOutputStream.flush();
						
						break;
					case INSERT :
						dbHandler.insertAddress(vo.getAddressVo());
						break;
					case UPDATE : 
						dbHandler.updateAddress(vo.getAddressVo());
						break;
					case DELETE : 
						dbHandler.deleteAddress(vo.getAddressVo());
						break;
					default : 
						LOGGER.debug("알수 없는 CmdType입니다 : " + vo.getCmdType());
					}			
				}
			}
			catch (EOFException eofe) {
				
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			finally {
				
			}
		}
	}
}
