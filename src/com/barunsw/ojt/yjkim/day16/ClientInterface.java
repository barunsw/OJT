package com.barunsw.ojt.yjkim.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(String msg) throws RemoteException;
}
