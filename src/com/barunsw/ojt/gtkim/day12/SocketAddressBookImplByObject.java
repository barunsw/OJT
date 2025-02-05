package com.barunsw.ojt.gtkim.day12;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketAddressBookImplByObject implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(SocketAddressBookImplByObject.class);

	private Socket clientSocket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	private String host;
	private int port;

	public SocketAddressBookImplByObject(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private void connect(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
			LOGGER.debug("새로운 연결을 생성합니다");

//			LOGGER.debug("새로운 IO 생성합니다");
//			objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//			objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//			LOGGER.debug("새로운 IO 스트림을 생성합니다");
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	@Override
	public List<AddressVo> selectAddressList() throws Exception {	
		connect(host, port);
		List<AddressVo> addressList = new ArrayList<AddressVo>();
		try {
			objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

			SocketCommandVo selectVo = new SocketCommandVo();
			selectVo.setCmdType(CmdType.SELECT);

			objectOutputStream.writeObject(selectVo);
			objectOutputStream.flush();

			objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
			
			Object readObject = null;
			while ((readObject = objectInputStream.readObject()) != null) {
				if (readObject instanceof AddressVo) {
					addressList.add((AddressVo) readObject);
				}
			}
	
			close(clientSocket, objectOutputStream, objectInputStream);
			return addressList;
		}
		catch (EOFException eofe) {
			LOGGER.debug("Select 결과 수신");
			close(clientSocket, objectOutputStream, objectInputStream);
			return addressList;
		}
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		connect(host, port);
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		
		SocketCommandVo insertVo = new SocketCommandVo();
		
		insertVo.setCmdType(CmdType.INSERT);
		insertVo.setAddressVo(addressVo);
		
		LOGGER.debug("Insert()를 호출합니다");
		objectOutputStream.writeObject(insertVo);
		objectOutputStream.flush();
		
		objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
		
		int result = objectInputStream.read();
	
		close(clientSocket, objectOutputStream, objectInputStream);
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		connect(host, port);
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		
		SocketCommandVo updateVo = new SocketCommandVo();
		
		updateVo.setCmdType(CmdType.UPDATE);
		updateVo.setAddressVo(addressVo);
		
		LOGGER.debug("update()를 호출합니다");
		objectOutputStream.writeObject(updateVo);
		objectOutputStream.flush();
		
		objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
		
		int result = objectInputStream.read();
	
		close(clientSocket, objectOutputStream, objectInputStream);	
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		connect(host, port);
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		
		SocketCommandVo deleteVo = new SocketCommandVo();
		
		deleteVo.setCmdType(CmdType.DELETE);
		deleteVo.setAddressVo(addressVo);
		
		LOGGER.debug("delete()를 호출합니다");
		objectOutputStream.writeObject(deleteVo);
		objectOutputStream.flush();

		objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
		
		int result = objectInputStream.read();
		
		close(clientSocket, objectOutputStream, objectInputStream);
		return result;
	}

	public void close(Socket clientSocket, ObjectOutputStream writer, ObjectInputStream reader) {
		try {
			if (reader != null) {
				reader.close();
			}

			if (writer != null) {
				writer.close();
			}

			if (clientSocket != null) {
				clientSocket.close();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
