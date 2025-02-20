package com.barunsw.ojt.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(String name, ClientInterface clientInterface) throws RemoteException;
	public void deregister(String name) throws RemoteException;
	public void send(String name, String message) throws RemoteException;
	//public void send(EventVo eventVo) throws RemoteException;
}
