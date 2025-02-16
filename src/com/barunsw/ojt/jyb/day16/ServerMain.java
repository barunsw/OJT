package com.barunsw.ojt.jyb.day16;

import java.io.Reader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);

	public static final int PORT = 50005;

	private boolean runFlag;

	public void startServer() { // 그냥 서버가 시작된다는 의미
		LOGGER.debug("서버 시작");

		try {
			ServerInterface server = new ServerImpl();

			Registry registry = LocateRegistry.createRegistry(PORT);
			
			registry.rebind("RMIRACKVIEW", server);

			LOGGER.debug("RMI 서버가 RMIRACVIEW를 바인딩했습니다.");
			//바인딩 한 순간 board를 랜덤하게 생성해야 함

		}
		catch (Exception e) {
			LOGGER.error("RMI 서버 실행 오류:", e);
		}

		LOGGER.debug(String.format("--- ServerMain started."));
	}

	public static void main(String[] args) throws RemoteException {
		new ServerMain().startServer();
	}
}
