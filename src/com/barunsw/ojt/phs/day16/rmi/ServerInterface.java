package com.barunsw.ojt.phs.day16.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(ClientInterface clientInterface) throws RemoteException;
	public void alaram() throws RemoteException;
}
