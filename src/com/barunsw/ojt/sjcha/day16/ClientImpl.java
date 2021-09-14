package com.barunsw.ojt.sjcha.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.barunsw.ojt.sjcha.day16.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	protected ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void pushAlarm(BoardVo boardVo) throws RemoteException {
		ClientMain.eventQueueWorker.push(boardVo);
	}
}