package com.barunsw.ojt.day13_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public int push(String message) throws RemoteException;
}
