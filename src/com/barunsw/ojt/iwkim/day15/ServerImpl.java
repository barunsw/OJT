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
	
	// 클라이언트에서 넘겨받은 이름과 인터페이스를 자료구조를 활용하여 담아놔야함!
	private Map<String, ClientInterface> clientRepo = new HashMap<>();

	public ServerImpl() throws RemoteException {
		
	}

	@Override
	public ResultVo register(String name, ClientInterface clientIf) throws RemoteException {
		LOGGER.debug("register name:" + name);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setResult(Result.OK);
		
		// 기본적인 result값은  OK로 하고
		// 자료구조를 찾아보고 같은 이름이 있으면 result에는 FAIL 
		// reason에는 이미 이름이 등록되었음을 설정해준다. 
		// 같은 이름이 없다면 자료구조에 추가해준다.
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
	public void sendAll(String name, String msg) throws RemoteException {
		LOGGER.debug("sendAll message:" + msg);
		
		// 상태가 이상한 클라이언트 담는 용도!
		List<String> weirdClients = new ArrayList<>();
		
		// 클라이언트의 이름으로 반복자를 통해 메세지를 push한다! 
		Iterator<String> iter = clientRepo.keySet().iterator();
		while (iter.hasNext()) {
			String clientName = iter.next();
			
			try {
				clientRepo.get(clientName).push(String.format(" %s : %s", name, msg));
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
