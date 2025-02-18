package com.barunsw.ojt.jyb.day17;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiControl {
	private ServerInterface serverIf;
	private ClientInterface clientIf;

	public RmiControl() {
		try {
			initRmi();
		}
		catch (Exception ex) {
			
		}
	}
	
	private void initRmi() throws Exception {
		Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);

		Remote remote = registry.lookup(ServerMain.BIND_NAME);
		if (remote instanceof ServerInterface) {
			serverIf = (ServerInterface) remote;
		}

		clientIf = new ClientImpl();
	}
	
	public void register(String name) throws Exception {
		serverIf.register(name, clientIf);
	}
		
	public void deregister(String name) throws Exception {
		serverIf.deregister(name);
	}
	
	public void send(String name, String message) throws Exception {
		serverIf.send(name, message);
	}
}
