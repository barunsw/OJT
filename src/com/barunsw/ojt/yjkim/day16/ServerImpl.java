package com.barunsw.ojt.yjkim.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Severity;

public class ServerImpl extends UnicastRemoteObject 
	implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	private Map<String, ClientInterface> clientRepo = 
			new ConcurrentHashMap<>();
	
	public ServerImpl() throws RemoteException {
		super();
	}
	
	@Override
	public void register(String name, ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("register");
		
		synchronized (clientRepo) {
			clientRepo.put(name, clientInterface);
		}
	}

	@Override
	public void send(String name, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("send msg:" + msg);
		
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				if (msg.equals("start")) {
					while(true) {
						synchronized (clientRepo) {
							for (ClientInterface oneClient : clientRepo.values()) {
								try {
										AlaramVo alaramVo = new AlaramVo();
										alaramVo.setSeverity(getSeverity());
										oneClient.push(alaramVo.toString());
									
									Thread.sleep(10000);
								} catch (RemoteException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			
		});
		thread.start();
	
		
	}
	
	public int getSeverity() {
		double randomServerity = Math.random();
		int randomValue = (int) (randomServerity * 4);
		if (randomValue == 0) {
			return Severity.CRITICAL;
		} else if (randomValue == 1) {
			return Severity.MAJOR;
		} else if (randomValue == 2) {
			return Severity.MINOR;
		} else {
			return Severity.NORMAL;
		}
	}
}
