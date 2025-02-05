package com.barunsw.ojt.gtkim.day12;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSocketHandlerByObject extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandlerByObject.class);

	private Socket clientSocket;
	private DBAddressBookImpl dbConn = new DBAddressBookImpl();
	private ObjectInputStream objectInputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	
	public ClientSocketHandlerByObject(Socket clientSocket) throws Exception{
		this.clientSocket = clientSocket;
		this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
	}
	
	@Override 
	public void run() {
		try {
			Object readObject = null;
		
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
			//ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			List<AddressVo> addressList = dbConn.selectAddressList();
			
			CmdType cmd = oneVo.getCmdType();		
			AddressVo oneAddress = oneVo.getAddressVo();
			
			if (cmd.equals(CmdType.SELECT)) {
				addressList = dbConn.selectAddressList();
				sendData(addressList, objectOutputStream);
			}
			else if (cmd.equals(CmdType.INSERT)) {
				returnValue = dbConn.insertAddress(oneAddress);
				objectOutputStream.write(returnValue);
			}
			else if (cmd.equals(CmdType.DELETE)) {
				returnValue = dbConn.deleteAddress(oneAddress);
				objectOutputStream.write(returnValue);
			}
			else if (cmd.equals(CmdType.UPDATE)) {
				returnValue = dbConn.updateAddress(oneAddress);
				objectOutputStream.write(returnValue);
			}
			else {
				returnValue = -1;
				objectOutputStream.write(returnValue);
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
