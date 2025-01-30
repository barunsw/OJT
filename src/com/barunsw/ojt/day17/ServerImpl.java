package com.barunsw.ojt.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.AlarmVo;

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

	public void sendAlarm(AlarmVo alarmVo) {
		synchronized (clientRepo) {
			List<String> weirdClientKeyList = new ArrayList<>();
			
			Iterator<String> keyIter = clientRepo.keySet().iterator();
			while (keyIter.hasNext()) {
				String key = keyIter.next();
				
				try {
					ClientInterface oneClient = clientRepo.get(key);
					oneClient.push(alarmVo);
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
					weirdClientKeyList.add(key);
				}
			}
			
			for (String oneKey : weirdClientKeyList) {
				clientRepo.remove(oneKey);
			}
		}
	}
}
