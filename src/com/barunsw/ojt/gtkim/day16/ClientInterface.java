package com.barunsw.ojt.gtkim.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void push(AlarmVo alramVo) throws RemoteException;
}
