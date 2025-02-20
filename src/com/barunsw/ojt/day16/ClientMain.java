package com.barunsw.ojt.day16;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain {
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);
	
	public static EventQueueWorker<EventVo> eventQueueWorker = new EventQueueWorker();
	
	public static void main(String[] args) {
		try {
			eventQueueWorker.start();
			
			String name = args[0];
			
			ClientTestPanel testPanel = new ClientTestPanel();
			ClientInterface clientIf = new ClientImpl();
			
			Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);
			
			ServerInterface serverIf = null;
			
			Remote remote = registry.lookup(ServerMain.BIND_NAME);
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface)remote;
			}

			if (serverIf != null) {
				serverIf.register(name, clientIf);

				for (int i = 0; i < 10; i++) {
					Thread.sleep(1000);
					serverIf.send(name, "Hello(" + i + ")");
				}
			}
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
