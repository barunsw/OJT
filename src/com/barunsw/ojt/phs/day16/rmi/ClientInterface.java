package com.barunsw.ojt.phs.day16.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(Object o) throws RemoteException;
}
