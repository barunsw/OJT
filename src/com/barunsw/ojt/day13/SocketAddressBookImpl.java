package com.barunsw.ojt.day13;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(SocketAddressBookImpl.class);
	
	private Socket clientSocket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	
	public SocketAddressBookImpl(String host, int port) throws Exception {
		LOGGER.debug("+++ SocketAddressBookImpl 생성자");
		clientSocket = new Socket(host, port);
		objectOutputStream = 
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
		// thread 소멸을 기다린다.
		inputThread.join();
		LOGGER.debug("--- SocketAddressBookImpl 생성자");
	}
	
	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception {
		List<AddressVo> list = new ArrayList<AddressVo>();

		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.SELECT);
		vo.setAddressVo(addressVo);

		LOGGER.debug("vo:" + vo);

		objectOutputStream.writeObject(vo);

		LOGGER.debug("objectOutputStream.writeObject()");

		objectOutputStream.flush();

		LOGGER.debug("writer.flush()");

		try {
			Object readObject = null;
			while ((readObject = objectInputStream.readObject()) != null) {
				if (readObject instanceof List) {
					List selectList = (List)readObject;
					for (Object oneObj : selectList) {
						LOGGER.debug("oneAddress:" + oneObj);
						if (oneObj instanceof AddressVo) {
							list.add((AddressVo)oneObj);
						}
					}
				}
			}
		}
		catch (EOFException eofe) {
		}

		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.INSERT);
		vo.setAddressVo(addressVo);

		LOGGER.debug("vo:" + vo);

		objectOutputStream.writeObject(vo);

		LOGGER.debug("objectOutputStream.writeObject()");

		objectOutputStream.flush();

		LOGGER.debug("writer.flush()");
		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.UPDATE);
		vo.setAddressVo(addressVo);

		LOGGER.debug("vo:" + vo);

		objectOutputStream.writeObject(vo);

		LOGGER.debug("objectOutputStream.writeObject()");

		objectOutputStream.flush();

		LOGGER.debug("writer.flush()");
		
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.DELETE);
		vo.setAddressVo(addressVo);

		LOGGER.debug("vo:" + vo);

		objectOutputStream.writeObject(vo);

		LOGGER.debug("objectOutputStream.writeObject()");

		objectOutputStream.flush();

		LOGGER.debug("writer.flush()");
		
		return 0;
	}
}
