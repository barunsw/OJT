package com.barunsw.ojt.jyb.day14;

import java.io.Reader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.RmiAddressBookInterface;

public class ServerMain {
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);

	public static final int PORT = 50004;

	private boolean runFlag;
	//extends UnicastRemoteObject 

	public void start() throws RemoteException { // 그냥 서버가 시작된다는 의미

		try {
			// config.properties 파일을 읽고, 구현체 클래스 이름을 동적으로 로드
			Properties properties = new Properties();
			try (Reader reader = Resources.getResourceAsReader("config.properties")) {
				properties.load(reader);
				String addressIfClass = properties.getProperty("address_if_class");

				// 해당 클래스를 동적으로 로드하고 객체 생성
				Object o = Class.forName(addressIfClass).newInstance();
				System.out.println(o instanceof RmiAddressBookInterface);
				if (o instanceof RmiAddressBookInterface) {
					RmiAddressBookInterface addressBookIf = (RmiAddressBookInterface) o;

					// RMI 레지스트리 생성 및 바인딩
					Registry registry = LocateRegistry.createRegistry(PORT);
					registry.rebind("ADDRESSBOOK", addressBookIf);
					LOGGER.debug("RMI 서버가 ADDRESSBOOK을 바인딩했습니다.");
				} else {
					throw new RuntimeException(addressIfClass + "는 RmiAddressBookInterface를 구현하지 않습니다.");
				}
			}
		}
		catch (Exception e) {
			LOGGER.error("RMI 서버 실행 오류:", e);
		}

		LOGGER.debug(String.format("--- ServerMain started."));
	}

	public static void main(String[] args) throws RemoteException {
		new ServerMain().start();
	}
}
