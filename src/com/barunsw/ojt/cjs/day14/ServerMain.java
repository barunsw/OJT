package com.barunsw.ojt.cjs.day14;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);
	public static int PORT = 0;
	public static Properties serverProperties = new Properties();

	private boolean runFlag;
	
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
	
	public void start() throws Exception  {
		PORT = Integer.parseInt(serverProperties.getProperty("port"));

		runFlag = true;
		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			while (runFlag) {
				Socket socket = serverSocket.accept();
				LOGGER.debug(String.format("client connected(%s)", socket.getRemoteSocketAddress()));
				ClientSocketHandler handler = new ClientSocketHandler(socket);
				handler.start();
			}
		} 
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		loadProperties(args[0]);
		new ServerMain().start();
	}
}
