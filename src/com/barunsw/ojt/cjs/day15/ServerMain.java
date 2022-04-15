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

		LOGGER.debug(String.format("--- ServerMain started."));
	}

	private void initAddressBookIf() throws Exception {

		String className = ServerMain.serverProperties.getProperty("address_if_class");
		LOGGER.debug(className);
		Object o = null;
		String serverHost = ServerMain.serverProperties.getProperty("host");
		LOGGER.debug(serverHost);

		int serverPort = Integer.parseInt(ServerMain.serverProperties.getProperty("port"));
		LOGGER.debug(serverPort + "");

		String regist = ServerMain.serverProperties.getProperty("regist");
		LOGGER.debug(regist);

		if (className.contains("SocketAddressBookImpl")) {
			Constructor c = Class.forName(className).getConstructor(String.class, Integer.class);
			o = c.newInstance(serverHost, serverPort);
			LOGGER.debug(o + "");
		} else {
			o = Class.forName(className).newInstance();
			LOGGER.debug(className + "");
		}
		if (o != null && o instanceof AddressBookInterface) {
			addressBookIf = (AddressBookInterface) o;
		}
		Registry registry = LocateRegistry.createRegistry(serverPort);
		registry.rebind(regist, addressBookIf);
	}

	public static void main(String[] args) throws Exception {
		loadProperties(args[0]);
		new ServerMain().start();
	}
}
