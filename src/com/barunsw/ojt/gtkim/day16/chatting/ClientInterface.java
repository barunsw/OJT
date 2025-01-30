package com.barunsw.ojt.gtkim.day16.chatting;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(String msg) throws RemoteException;
}
