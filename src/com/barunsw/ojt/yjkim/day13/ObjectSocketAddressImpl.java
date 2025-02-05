package com.barunsw.ojt.yjkim.day13;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ObjectSocketAddressImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(ObjectSocketAddressImpl.class);
	
	private Socket ClientSocket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	
	public ObjectSocketAddressImpl(String host, int port) throws Exception {
		LOGGER.debug("+++ ObjectSocketAddressImpl 생성자");
		this.ClientSocket = new Socket(host, port);
		this.objectOutputStream = 
				new ObjectOutputStream(ClientSocket.getOutputStream());
		
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					objectInputStream = 
							new ObjectInputStream(ClientSocket.getInputStream());
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		});
		inputThread.start();
		inputThread.join();
		LOGGER.debug("--- ObjectSocketAddressImpl 생성자");
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> list = new ArrayList<AddressVo>();
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.SELECT);
		//명령어를 vo 객체에 담아서 write한다.
		objectOutputStream.writeObject(vo);
		LOGGER.debug("objectOutputStream.selectAddressList.writeObject()");
		objectOutputStream.flush();
		LOGGER.debug("objectOutputStream.selectAddressList.flush()");
		
		//ClientHandler에서 처리한 값을 받아온다.
		try {
			Object readObject = null;
			while ((readObject = objectInputStream.readObject()) != null) {
				LOGGER.debug("+++ objectInputStream.selectAddressList.readObject()");
				LOGGER.debug(readObject);
				if (readObject instanceof List) {
					List selectList = (List)readObject;
					for (Object oneObj : selectList) {
						LOGGER.debug(String.format("OneAddress [%s]", oneObj));
						if (oneObj instanceof AddressVo) {
							list.add((AddressVo)oneObj);
						}
					}
				}
			}
			LOGGER.debug("*** objectInputStream.selectAddressList.readObject()");

		} catch (EOFException eofe) {
			LOGGER.error(eofe.getMessage(), eofe);
		}
		LOGGER.debug("--- objectInputStream.selectAddressList.readObject()");
		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.INSERT);
		vo.setAddressVo(addressVo);
		
		objectOutputStream.writeObject(vo);
		LOGGER.debug("objectOutputStream.insertAddress.WriteObject()");
		objectOutputStream.flush();
		LOGGER.debug("objectOutputStream.insertAddress.flush()");
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.UPDATE);
		vo.setAddressVo(addressVo);
		
		objectOutputStream.writeObject(vo);
		LOGGER.debug("objectOutputStream.updateAddress.WriteObject()");
		objectOutputStream.flush();
		LOGGER.debug("objectOutputStream.updateAddress.flush()");
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		SocketCommandVo vo = new SocketCommandVo();
		vo.setCmdType(CmdType.DELETE);
		vo.setAddressVo(addressVo);
		
		objectOutputStream.writeObject(vo);
		LOGGER.debug("objectOutputStream.deleteAddress.WriteObject()");
		objectOutputStream.flush();
		LOGGER.debug("objectOutputStream.deleteAddress.flush()");
		return 0;
	}

}
