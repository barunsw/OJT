package com.barunsw.ojt.gtkim.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.barunsw.ojt.vo.AlarmVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	public ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void push(Object o) throws RemoteException {
		ClientMain.eventQueueWorker.push(o);
	}
}
