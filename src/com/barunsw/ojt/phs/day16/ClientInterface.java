package com.barunsw.ojt.phs.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(MessageVO messageVo) throws RemoteException;
}