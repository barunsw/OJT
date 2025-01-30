package com.barunsw.ojt.iwkim.day15;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
	public static final int RMI_PORT = 1099;
	
	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.createRegistry(RMI_PORT);
		
		ServerInterface serverIf = new ServerImpl();
		
		registry.bind("CHAT", serverIf);
	}
}
