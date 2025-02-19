package com.barunsw.ojt.jyb.day16;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RmiControl {
	private static final Logger LOGGER = LogManager.getLogger(RmiControl.class);

	private ClientInterface clientIf;
	private ServerInterface serverIf;

	public RmiControl(ClientImpl clientImpl) throws NotBoundException {
		try {
			initRmi(clientImpl);
		}
		catch (RemoteException e) {
			LOGGER.error("RMI 초기화 실패");
		}
	}

	private void initRmi(ClientImpl clientImpl) throws RemoteException, NotBoundException {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", ServerMain.PORT);
			serverIf = (ServerInterface) registry.lookup("RMIRACKVIEW");
			clientIf = clientImpl;
			serverIf.register(clientIf);
			LOGGER.info("RMI 서버 연결 완료");
		}
		catch (RemoteException e) {
			LOGGER.error("RMI 서버 연결 실패", e);
			throw e;
		}
	}

	public ServerInterface getServerIf() {
		return serverIf;
	}

	public ClientInterface getClientIf() {
		return clientIf;
	}

}