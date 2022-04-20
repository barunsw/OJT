package com.barunsw.ojt.cjs.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.barunsw.ojt.cjs.common.BoardVo;


public class ClientImpl extends UnicastRemoteObject implements ClientInterface{

	protected ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void pushAlarm(BoardVo boardVo) {
		ClientMain.eventQueueWorker.push(boardVo);
	}

}
