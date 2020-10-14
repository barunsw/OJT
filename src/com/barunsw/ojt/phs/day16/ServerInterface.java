package com.barunsw.ojt.phs.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(String name, ClientInterface clientInterface) throws RemoteException;
	public void send(MessageVO messageVo) throws RemoteException;	
	public void logOut(String name, ClientInterface clientInterface) throws RemoteException;
}
