package com.barunsw.ojt.jyb.day17;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(String msg) throws RemoteException;
//	public void push(EventVo eventVo) throws RemoteException;
}
