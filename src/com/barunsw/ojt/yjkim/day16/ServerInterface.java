package com.barunsw.ojt.yjkim.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(ClientInterface clientInterface) throws RemoteException;
	public void send() throws RemoteException;
}
