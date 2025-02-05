package com.barunsw.ojt.phs.day13;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S_ServerMain {
	
	private static final Logger LOGGER = LogManager.getLogger(S_ServerMain.class);
	
	private static final S_ServerMain serverMain = new S_ServerMain();
	
	protected static final S_AddressBookInterface addressBook = new S_DBAddressBookImpl();
	
	//접속한 Client들의 OutputStream 
	private static List<ObjectOutputStream> streamList;
	
	synchronized public void addStream(ObjectOutputStream oos) {
		getStreamList().add(oos);
	}
	
	synchronized public static List<ObjectOutputStream> getStreamList() {
		return streamList;
	}
	
	public static void main(String[] args) {
		
		try {
			addressBook.createStorage();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		try {
			
			streamList = new ArrayList<ObjectOutputStream>();
			ServerSocket serverSocket = new ServerSocket(6500);
			LOGGER.debug("server소켓 생성완료");
			
			while (true) {
				LOGGER.debug("accept waitng...");
				Socket socket = serverSocket.accept();
				LOGGER.debug("accept approve!!");
				
				S_ClientSocketHandler socketHandler = new S_ClientSocketHandler(socket, serverMain);
				socketHandler.start();
			}
			
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

}
