package com.barunsw.ojt.phs.day12;

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
	
	//접속한 Client들의 OutputStream 
	private static List<ObjectOutputStream> streamList;
	
	public static void main(String[] args) {

		try {
			streamList = new ArrayList<ObjectOutputStream>();
			ServerSocket serverSocket = new ServerSocket(55555);
			
			while (true) {
				LOGGER.debug("accept waitng...");
				Socket socket = serverSocket.accept();
				LOGGER.debug("accept approve!!");
				streamList.add(new ObjectOutputStream(socket.getOutputStream()));
				
				S_ClientSocketHandler socketHandler = new S_ClientSocketHandler(socket, streamList);
				socketHandler.start();
			}
			
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
	}

}
