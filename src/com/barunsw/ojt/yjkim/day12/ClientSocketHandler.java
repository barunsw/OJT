package com.barunsw.ojt.yjkim.day12;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientSocketHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientSocketHandler.class);
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private String namespace = "com.barunsw.ojt.yjkim.day12.SocketAddressDao";

	private Socket clientSocket;
	
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	public ClientSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public AddressVo parseCmd(String paramStr) {
		AddressVo addressVo = new AddressVo();
		
		String[] paramList = paramStr.split(",");
		for (String oneParam : paramList) {
			String[] oneParamData = oneParam.split("=");
			String key = oneParamData[0].trim();
			String val = oneParamData[1].trim();
			
			switch (key) {
			case "SEQ":
				addressVo.setSeq(Integer.parseInt(val));
			case "NAME":
				addressVo.setName(val);
				break;
			case "GENDER":
				try {
					addressVo.setGender(Gender.toGender(val));
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
				break;
			case "AGE":
				addressVo.setAge(Integer.parseInt(val));
				break;
			case "ADDRESS":
				addressVo.setAddress(val);
				break;
			}
		}
		
		return addressVo;
	}
	
	@Override
	public void run() {
		LOGGER.debug("+++ ClientSocketHandler run");
		//Object 방식
		try {
			ois =  new ObjectInputStream(clientSocket.getInputStream());
			Object o = null;
			while ((o = ois.readObject()) != null) {
				if (o instanceof SocketCommandVo) {
					SocketCommandVo socketCommandVo = (SocketCommandVo) o;
					LOGGER.debug(socketCommandVo);
					switch(socketCommandVo.getCmdType().toString()) {
						case "SELECT" : 
							handleAllSelect();
							break;
						case "INSERT":
							handleInsert(socketCommandVo.getAddressVo());
							break;
						case "UPDATE":
							handleUpdate(socketCommandVo.getAddressVo());
							break;
						case "DELETE":
							handleDelete(socketCommandVo.getAddressVo());
							break;
					}
				}
			}
		} catch (EOFException eofe) {
			return;
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		} finally {
			try {
				if (ois != null) {
					ois.close();
				} 
			}catch(IOException ioe) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}
		
		
		//write 방식
		/*
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
				String readLine = null;
				while ((readLine = reader.readLine()) != null) {
					LOGGER.debug(String.format("+++ readLine:%s", readLine));
					
					String[] cmdSplit = readLine.split(":");
					AddressVo addressVo;
					LOGGER.debug(cmdSplit[0]);
					String cmd = cmdSplit[0];
					switch (cmd) {
					case "INSERT":
						addressVo = parseCmd(cmdSplit[1]);
						handleInsert(addressVo);
						break;
					case "SELECT":
						handleAllSelect();
						break;
					case "UPDATE":
						addressVo = parseCmd(cmdSplit[1]);
						handleUpdate(addressVo);
						break;
					case "DELETE":
						addressVo = parseCmd(cmdSplit[1]);
						handleDelete(addressVo);
						break;
						
					}
				}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		*/
		LOGGER.debug("--- ClientSocketHandler run");
	}
	
	private void handleInsert(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleInsert"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.insert(namespace + ".insertSocketAddress", addressVo);
			session.commit();
		}

	}
	private void handleUpdate(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleUpdate"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.update(namespace + ".updateSocketAddress",addressVo);
			session.commit();
		}
		
	}
	private void handleDelete(AddressVo addressVo) {
		LOGGER.debug(String.format("+++ handleDelete"));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.delete(namespace + ".deleteSocketAddress",addressVo);
			session.commit();
		}
	}
	private void handleAllSelect() throws ClassNotFoundException {
		LOGGER.debug(String.format("+++ handleAllSelect "));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			List<AddressVo> list = session.selectList(namespace + ".selectAllSocketAddress");
			//writer 방식
				/*try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
					for(AddressVo addressvo : list) {
						LOGGER.debug(addressvo.toString());
						writer.write(addressvo.toString()+"\n");
						writer.flush();
					}
				} catch (IOException ioe) {
					LOGGER.error(ioe.getMessage(), ioe);
				}*/
			
				//ObjectOutput방식
				try {
					oos = new ObjectOutputStream(clientSocket.getOutputStream());
					for (AddressVo addressVo : list) {
						oos.writeObject(addressVo);
						oos.flush();
					}
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
			}
		}
	}

