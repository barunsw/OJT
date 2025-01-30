package com.barunsw.ojt.day13_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public int register(String userId, ClientInterface clientIf) throws RemoteException;
	public int deregister(String userId) throws RemoteException;
	public int send(String userId, String message) throws RemoteException;
}
