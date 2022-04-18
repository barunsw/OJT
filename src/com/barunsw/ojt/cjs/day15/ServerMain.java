package com.barunsw.ojt.cjs.day15;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.AddressBookInterface;

public class ServerMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

	public static Properties serverProperties = new Properties();
	public static AddressBookInterface addressBookIf;

	public ServerMain() throws Exception {
		initAddressBookIf();
	}

	private static void loadProperties(String configPath) throws IOException {
		Reader reader = new FileReader(configPath);
		serverProperties.load(reader);

		Iterator<Object> keySet = serverProperties.keySet().iterator();
		while (keySet.hasNext()) {
			Object key = keySet.next();
			Object value = serverProperties.get(key);
			LOGGER.debug(String.format("%s = %s", key, value));
		}
	}

	public void start() throws Exception {
		LOGGER.debug(String.format("+++ ServerMain started."));
		int serverPort = Integer.parseInt(ServerMain.serverProperties.getProperty("port"));
		LOGGER.debug(serverPort + "");
		
		String regist = (String)ServerMain.serverProperties.getProperty("regist");
		
		Registry registry = LocateRegistry.createRegistry(serverPort);
		registry.rebind(regist, addressBookIf);
		LOGGER.debug(String.format("--- ServerMain started."));
	}

	public static void main(String[] args) throws Exception {
		loadProperties(args[0]);
		new ServerMain().start();
	}
}
