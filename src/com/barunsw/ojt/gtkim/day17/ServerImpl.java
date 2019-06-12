package com.barunsw.ojt.gtkim.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.AlarmVo;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	private List<ClientInterface> clientRepo = new ArrayList<>();

	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public void register(ClientInterface clientInterface) throws RemoteException {
		LOGGER.debug("register");

		synchronized (clientRepo) {
			clientRepo.add(clientInterface);
		}
	}

	@Override
	public void deregister(ClientInterface clientInterface) throws RemoteException {
		LOGGER.debug("deregister");

		synchronized (clientRepo) {
			clientRepo.remove(clientInterface);
		}
	}

	public void sendAlarm(AlarmVo alarmVo) {
		synchronized (clientRepo) {
			List<ClientInterface> weirdClientList = new ArrayList<>();

			for (ClientInterface oneClient : clientRepo) {
				try {
					oneClient.push(alarmVo);
				} 
				catch (RemoteException e) {
					LOGGER.error(e.getMessage(), e);
					weirdClientList.add(oneClient);
				}
			}

			for (ClientInterface weirdClient : weirdClientList) {
				clientRepo.remove(weirdClient);
				LOGGER.debug("보내는 과정에서 발생한 오류");
			}
		}
	}
}
