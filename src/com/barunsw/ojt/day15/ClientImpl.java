package com.barunsw.ojt.day15;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.barunsw.ojt.vo.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private TestPanel panel;
	
	protected ClientImpl(TestPanel panel) throws RemoteException {
		super();

		this.panel = panel;
	}

	@Override
	public void pushAlarm(BoardVo boardVo) {
		// TODO Auto-generated method stub
		// panel쪽에 알람을 전달한다. pushAlarm을 호출
	}
}
