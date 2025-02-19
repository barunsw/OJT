package com.barunsw.ojt.jyb.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.barunsw.ojt.vo.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	protected ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void pushAlarm(BoardVo boardVo) throws RemoteException {
		ClientMain.eventQueueWorker.push(boardVo); // 큐에 알림 추가
	}
}