package com.barunsw.ojt.phs.day12;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S_ClientSocketHandler extends Thread{
	
	private static final Logger LOGGER = LogManager.getLogger(S_ClientSocketHandler.class);
	
	//Broadcast를 위한 ObjectOutputStream 모음
	private static List<ObjectOutputStream> streamList;
	
	private Socket socket;
	
	private ObjectInputStream ois = null;
	
	public S_ClientSocketHandler(Socket socket, List streamList) {
		this.socket = socket;
		this.streamList = streamList;
		
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void run() {
		LOGGER.debug("serverThread start");
		MessageVO message = null;
		
		while (true) {
			try {
				//Client가 데이터를 보내기를 기다렸다가 
				//데이터가 들어오면 접속한 client전부에게 broadcast
				if ((message = (MessageVO)ois.readObject()) != null) {
					broadcast(message);
				}
			}
			catch (Exception e) {
				LOGGER.debug(e.getMessage());
			}	
		}
		
	}

	private void broadcast(MessageVO message) {
		LOGGER.debug("broadcast");
		for (ObjectOutputStream oos : streamList) {
			try {
				oos.writeObject(message);
				oos.flush();
			}
			catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
}
