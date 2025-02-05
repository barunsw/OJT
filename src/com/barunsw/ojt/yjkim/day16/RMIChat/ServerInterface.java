package com.barunsw.ojt.yjkim.day16.RMIChat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(String name, ClientInterface clientInterface) throws RemoteException;
	public void send(String name, String msg) throws RemoteException;
}
