package com.barunsw.ojt.gtkim.day17;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(ClientInterface clientInterface) throws RemoteException;
	public void deregister(ClientInterface clientInterface) throws RemoteException;
}
