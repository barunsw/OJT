package com.barunsw.ojt.gtkim.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	private List<ClientInterface> clientRepo = new ArrayList<>();
	
	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public void register(ClientInterface clinetIf) throws RemoteException {
		synchronized(clientRepo) {
			clientRepo.add(clinetIf);
		}
		
		LOGGER.debug("새로운 클라이언트가 등록되었습니다");
	}

	@Override
	public void change() throws RemoteException {
		synchronized (clientRepo) {
			AlarmVo oneAlarmVo = new AlarmVo();
			
			LOGGER.debug(String.format("AlarmVo를 생성합니다 id: %d, severity : %d",
					oneAlarmVo.getBoardId(), oneAlarmVo.getSeverity()));
			
			for (ClientInterface oneIf : clientRepo) {
				oneIf.push(oneAlarmVo);
			}
		}
	}

	@Override
	public void deRegister(ClientInterface clientIf) throws RemoteException {
		synchronized (clientRepo) {
			clientRepo.remove(clientIf);
		}		
	}
}
