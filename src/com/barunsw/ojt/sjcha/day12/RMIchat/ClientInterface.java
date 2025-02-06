package com.barunsw.ojt.sjcha.day12.RMIchat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public int push(String message) throws RemoteException;
}