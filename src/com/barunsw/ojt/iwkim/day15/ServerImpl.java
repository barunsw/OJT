package com.barunsw.ojt.iwkim.day15;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final long serialVersionUID = -7642182568862518191L;
	
	private static Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	private Map<String, ClientInterface> clientRepo = new HashMap<>();

	public ServerImpl() throws RemoteException {
		
	}

	@Override
	public ResultVo register(String name, ClientInterface clientIf) throws RemoteException {
		LOGGER.debug("register name:" + name);

		ResultVo resultVo = new ResultVo();
		resultVo.setResult(Result.OK);
		
		// TODO Auto-generated method stub
		if (clientRepo.containsKey(name)) {
			resultVo.setResult(Result.FAIL);
			resultVo.setReason("이미 등록되어 잇습니다.(" + name + ")");
		}
		else {
			clientRepo.put(name, clientIf);
		}
		
		return resultVo;
	}

	@Override
	public void sendAll(String message) throws RemoteException {
		LOGGER.debug("sendAll message:" + message);
		
		List<String> weirdClients = new ArrayList<>();
		
		Iterator<String> iter = clientRepo.keySet().iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			
			try {
				clientRepo.get(name).push(message);
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				
				weirdClients.add(name);
			}
		}
		
		// 상태가 이상한 클라이언트는 삭제한다.
		for (String weirdName : weirdClients) {
			clientRepo.remove(weirdName);
		}
	}
}
