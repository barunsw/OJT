package com.barunsw.ojt.iwkim.day15;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(String message) throws RemoteException;
}
