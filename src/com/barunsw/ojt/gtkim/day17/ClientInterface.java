package com.barunsw.ojt.gtkim.day17;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(Object o) throws RemoteException;
}
