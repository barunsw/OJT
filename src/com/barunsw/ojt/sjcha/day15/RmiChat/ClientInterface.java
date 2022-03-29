package com.barunsw.ojt.sjcha.day15.RmiChat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public int push(String message) throws RemoteException;
}