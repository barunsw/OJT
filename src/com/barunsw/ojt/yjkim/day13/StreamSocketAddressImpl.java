package com.barunsw.ojt.yjkim.day13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StreamSocketAddressImpl implements AddressBookInterface{
	private static final Logger LOGGER = LogManager.getLogger(StreamSocketAddressImpl.class);

	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public StreamSocketAddressImpl(String host, int port) throws Exception {
		LOGGER.debug("+++ StreamSocketAddressImpl 생성자 ");
		clientSocket = new Socket(host, port);
		writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));		
		//reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		});
		inputThread.start();
		inputThread.join();
		LOGGER.debug("--- StreamSocketAddressImpl 생성자 ");
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		LOGGER.debug("+++ StreamSocketAddressImpl.selectAddressList");
		List<AddressVo> selectList = new ArrayList<AddressVo>();
		String command = String.format("SELECT:\n");
		LOGGER.debug(command);
		
		writer.write(command);
		writer.flush();
		
		StringBuffer buf = new StringBuffer();
		
		try {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
			
				LOGGER.debug("received:" + readLine);
				if(readLine.equals("+")) {
					break;
				}
					AddressVo addressvo = new AddressVo();
					String[] paramList = readLine.split(",");
					for(String oneParam : paramList) {
						LOGGER.debug("oneParam" + oneParam);
						String[] oneParamData = oneParam.split("=");
						for(String one : oneParamData) {
							LOGGER.debug("one" + one);
						}
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
					LOGGER.debug(String.format("selectList size () [%d]", selectList.size()));
				}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		LOGGER.debug("--- StreamSocketAddressImpl.selectAddressList");

		return selectList;

	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String command = String.format("INSERT:SEQ=%d,NAME=%s,GENDER=%s,AGE=%d,ADDRESS=%S\n", 
				addressVo.getSeq(), addressVo.getName(),
				addressVo.getGender(), addressVo.getAge(),
				addressVo.getAddress());		
				LOGGER.debug("command:" + command);
		
		writer.write(command);		
		writer.flush();
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String command = String.format("UPDATE:SEQ=%d,NAME=%s,GENDER=%s,AGE=%d,ADDRESS=%s\n", 
				addressVo.getSeq(), addressVo.getName(),
				addressVo.getGender(), addressVo.getAge(),
				addressVo.getAddress());	
		writer.write(command);
		writer.flush();
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String command = String.format("DELETE:SEQ=%d\n", 
			addressVo.getSeq());
			writer.write(command);
			writer.flush();
		return 0;
	}

}
