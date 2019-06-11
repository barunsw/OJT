package com.barunsw.ojt.gtkim.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(ClientInterface clientIf) throws RemoteException;
	
	public void change() throws RemoteException;
}
