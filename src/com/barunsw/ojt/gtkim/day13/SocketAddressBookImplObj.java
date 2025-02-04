package com.barunsw.ojt.gtkim.day13;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.gtkim.day12.AddressBookInterface;
import com.barunsw.ojt.gtkim.day12.AddressVo;
import com.barunsw.ojt.gtkim.day12.CmdType;
import com.barunsw.ojt.gtkim.day12.SocketCommandVo;

public class SocketAddressBookImplObj implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(SocketAddressBookImplObj.class);
	
	private Socket clientSocket;

	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	
	public SocketAddressBookImplObj(String host, int port) throws Exception {
		this.clientSocket = new Socket(host, port);
		this.objectOutputStream = 
				new ObjectOutputStream(clientSocket.getOutputStream());
		
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
		inputThread.join();
		
		LOGGER.debug("SocketImpl 생성완료!");
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> addressList = new ArrayList<AddressVo>();
		
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.SELECT);
		
		objectOutputStream.writeObject(vo);
		objectOutputStream.flush();
		
		LOGGER.debug("Object Select 전송");
		
		try {
			Object readObject = null;
			while ((readObject = objectInputStream.readObject()) != null) {
				if (readObject instanceof List) {
					List readList = (List) readObject;
					for (Object oneObject : readList) {
						if (oneObject instanceof AddressVo) {
							addressList.add((AddressVo) oneObject);
							LOGGER.debug("수신된 데이터 : " + oneObject);
						}
					}
				}
				else {
					// 스트림을 항상 열어두어서 계속 수신을 대기하는 것으로 추측됨
					break;
				}
			}
		}
		catch (EOFException eofe) {
			LOGGER.debug("EOFE 발생");
		}
		LOGGER.debug(addressList);
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.INSERT);
		vo.setAddressVo(addressVo);
		
		objectOutputStream.writeObject(vo);
		objectOutputStream.flush();
			
		LOGGER.debug("Object Insert 전송");
		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.UPDATE);
		vo.setAddressVo(addressVo);

		objectOutputStream.writeObject(vo);
		objectOutputStream.flush();
		
		LOGGER.debug("Object Update 전송");
		
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.DELETE);
		vo.setAddressVo(addressVo);
		
		objectOutputStream.writeObject(vo);
		objectOutputStream.flush();

		LOGGER.debug("Object Delete 전송");
		
		return 0;
	}

	private void close() {
		if (objectOutputStream != null) {
			try {
				objectOutputStream.close();
			}
			catch (Exception ex) {
				LOGGER.debug(ex.getMessage(), ex);
			}
		}
		
		if (objectInputStream != null) {
			try {
				objectInputStream.close();
			}
			catch (Exception ex) {
				LOGGER.debug(ex.getMessage(), ex);
			}
		}
		
		if (clientSocket != null) {
			try {
				clientSocket.close();
			}
			catch (Exception ex) {
				LOGGER.debug(ex.getMessage(), ex);
			}
		}
	}
}
