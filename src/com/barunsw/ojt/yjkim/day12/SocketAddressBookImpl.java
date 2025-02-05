package com.barunsw.ojt.yjkim.day12;

import java.io.EOFException;
import java.io.IOException;
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
	//private BufferedReader reader;
	//private BufferedWriter writer;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	private SocketCommandVo socketcommandVo = new SocketCommandVo();
	public SocketAddressBookImpl(String host, int port) throws Exception {
		clientSocket = new Socket(host, port);
		clientSocket.setSoTimeout(30000);
		//reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));		
		//ois = new ObjectInputStream(clientSocket.getInputStream());
		//oos = new ObjectOutputStream(clientSocket.getOutputStream());
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		LOGGER.debug("SocketAddressBookImpl SelectAddressList");
		
		List<AddressVo> selectList = new ArrayList<AddressVo>();
		socketcommandVo.setCmdType(CmdType.SELECT);
		
		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(socketcommandVo);
			oos.flush();
			
			ois = new ObjectInputStream(clientSocket.getInputStream());
			Object o = null;
			while ((o = ois.readObject()) != null) {
				if (o instanceof AddressVo) {
					LOGGER.debug((AddressVo)o + "나도그만하고싶어");
					selectList.add((AddressVo)o);
				}
			}
		} catch (EOFException eofe) {

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}  finally {
		
			try {
				if (oos != null) {
					oos.close();
				}
				if (ois != null) {
					ois.close();
				}
			} catch (IOException ioe) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}
		
		
		
		//String command = String.format("SELECT:\n");
		//LOGGER.debug(command);
		
		//writer.write(command);
		//	writer.flush();
		
	/*
		StringBuffer buf = new StringBuffer();
		String readLine = null;
		while ((readLine = reader.readLine()) != null) {
			buf.append(readLine + "\n");
		}
		
		LOGGER.debug("received:" + readLine);
	

		String[] str = buf.toString().split("\n");
		
		for (String string : str) {
			LOGGER.debug(string + "parsing ");
			AddressVo addressvo = new AddressVo();
			
			String[] paramList = string.split(",");
			for(String oneParam : paramList) {
				String[] oneParamData = oneParam.split("=");
				String key = oneParamData[0].trim();
				String val = oneParamData[1].trim();
				switch (key) {
				case "seq":
					addressvo.setSeq(Integer.parseInt(val));
					break;
				case "name":
					addressvo.setName(val);
					break;
				case "gender":
					addressvo.setGender(Gender.toGender(val));
					break;
				case "age":
					addressvo.setAge(Integer.parseInt(val));
					break;
				case "address":
					addressvo.setAddress(val);
					break;
				}
			}
			selectList.add(addressvo);

		}*/
		return selectList;
		
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		socketcommandVo.setCmdType(CmdType.INSERT);
		socketcommandVo.setAddressVo(addressVo);
		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(socketcommandVo);
			oos.flush();
			
		} catch (EOFException eofe) {

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
				try {
				if (oos != null) {
					oos.close();
				}
				
			} catch (IOException ioe) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}
//		String command = String.format("INSERT:SEQ=%d,NAME=%s,GENDER=%s,AGE=%d,ADDRESS=%S\n", 
//				addressVo.getSeq(), addressVo.getName(),
//				addressVo.getGender(), addressVo.getAge(),
//				addressVo.getAddress());		
//		LOGGER.debug("command:" + command);
		
	//	writer.write(command);		
	//	writer.flush();
		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		socketcommandVo.setCmdType(CmdType.UPDATE);
		socketcommandVo.setAddressVo(addressVo);
		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(socketcommandVo);
			oos.flush();
			
		} catch (EOFException eofe) {

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
				try {
				if (oos != null) {
					oos.close();
				}
				
			} catch (IOException ioe) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}
	//String command = String.format("UPDATE:SEQ=%d,NAME=%s,GENDER=%s,AGE=%d,ADDRESS=%s\n", 
	//		addressVo.getSeq(), addressVo.getName(),
	//		addressVo.getGender(), addressVo.getAge(),
	//		addressVo.getAddress());	
	//	writer.write(command);
	//	writer.flush();
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
	
		socketcommandVo.setCmdType(CmdType.DELETE);
		socketcommandVo.setAddressVo(addressVo);
		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(socketcommandVo);
			oos.flush();
			
		} catch (EOFException eofe) {

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
				try {
				if (oos != null) {
					oos.close();
				}
				
			} catch (IOException ioe) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}
	//String command = String.format("DELETE:SEQ=%d\n", 
	//		addressVo.getSeq());
	//	writer.write(command);
	//	writer.flush();
		return 0;
	}

	public void close() {
		
	}
}
