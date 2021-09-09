package com.barunsw.ojt.sjcha.day12.RMIchat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day12.RMIchat.ServerInterface;
import com.barunsw.ojt.sjcha.day12.RMIchat.ChatPanel;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);
	
	private ServerInterface serverIf;
	private ChatPanel chatPanel;
	
	public ClientImpl(ChatPanel chatPanel) throws RemoteException {
		super();
		this.chatPanel = chatPanel;
		/*
		 * try { initRmi(); } catch (Exception ex) { LOGGER.error(ex.getMessage(), ex);
		 * }
		 */
	}
	
	/*
	 * private void initRmi() throws Exception { Registry registry =
	 * LocateRegistry.getRegistry("localhost", 5000);
	 * 
	 * serverIf = (ServerInterface)registry.lookup("RMICHAT"); }
	 */
	
	@Override
	public int push(String message) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("Send Message : " + message);
		
		//ChatPanel chatPanel = new ChatPanel();
		// message를 panel에 보내서 textarea에 쓰기
		chatPanel.push(message);
		return 0;
	}
}
