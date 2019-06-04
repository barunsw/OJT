package com.barunsw.ojt.gtkim.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class ClientSocketHandlerByObject extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandlerByObject.class);

	private Socket clientSocket;
	private DBAddressBookImpl dbConn = new DBAddressBookImpl();
	private ObjectInputStream objectInputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	
	public ClientSocketHandlerByObject(Socket clientSocket) throws Exception{
		this.clientSocket = clientSocket;
//		LOGGER.debug("소켓 초기화");
//		this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//		this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//		LOGGER.debug("io스트림 초기화");
	}
	
	@Override 
	public void run() {
		try {
			Object readObject = null;
			objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
			
//			while (!clientSocket.isClosed()) {		
			readObject = objectInputStream.readObject();
			LOGGER.debug("[Server] readLine : " + readObject);

			SocketCommandVo oneVo = null;
			if (readObject instanceof SocketCommandVo) {
				oneVo = (SocketCommandVo) readObject;
				handleObject(oneVo);
			} 
			else {
				LOGGER.debug("SocketCommandVo가 아닙니다");
			}
//			}
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		finally {
			try {
				if (objectOutputStream != null) {
					objectOutputStream.close();
				}
				if (objectInputStream != null) {
					objectInputStream.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}
	
	private void handleObject(SocketCommandVo oneVo) {
		int returnValue = 0;
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			List<AddressVo> addressList = dbConn.selectAddressList();
			
			CmdType cmd = oneVo.getCmdType();		
			AddressVo oneAddress = oneVo.getAddressVo();
			
			if (cmd.equals(CmdType.SELECT)) {
				addressList = dbConn.selectAddressList();
				sendData(addressList, oos);
			}
			else if (cmd.equals(CmdType.INSERT)) {
				returnValue = dbConn.insertAddress(oneAddress);
				oos.write(returnValue);
			}
			else if (cmd.equals(CmdType.DELETE)) {
				returnValue = dbConn.deleteAddress(oneAddress);
				oos.write(returnValue);
			}
			else if (cmd.equals(CmdType.UPDATE)) {
				returnValue = dbConn.updateAddress(oneAddress);
				oos.write(returnValue);
			}
			else {
				returnValue = -1;
				oos.write(returnValue);
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void sendData(List<AddressVo> addressList, ObjectOutputStream oos) {
		try {
			for (AddressVo oneAddress : addressList) {
				oos.writeObject(oneAddress);
				LOGGER.debug("Sever -> Client " + oneAddress);
			}
			oos.close();
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
}
