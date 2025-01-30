package com.barunsw.ojt.iwkim.day15;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	public ResultVo register(String name, ClientInterface clientIf) throws RemoteException;
	public void sendAll(String name, String msg) throws RemoteException;
}
