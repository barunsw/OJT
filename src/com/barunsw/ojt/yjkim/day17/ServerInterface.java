package com.barunsw.ojt.yjkim.day17;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public void register(String name, ClientInterface clientInterface) throws RemoteException;

}
