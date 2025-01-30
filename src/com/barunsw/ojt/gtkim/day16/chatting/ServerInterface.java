package com.barunsw.ojt.gtkim.day16.chatting;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(String name, ClientInterface clientInterface) throws RemoteException;
	public void send(String name, String msg) throws RemoteException;	
	public void logOut(String name, ClientInterface clientInterface) throws RemoteException;
}
