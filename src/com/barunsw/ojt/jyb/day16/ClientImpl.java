package com.barunsw.ojt.jyb.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.barunsw.ojt.vo.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private ShelfPanel shelfPanel;

	protected ClientImpl(ShelfPanel shelfPanel) throws RemoteException {
		super();

		this.shelfPanel = shelfPanel;
	}

	@Override
	public void pushAlarm(BoardVo boardVo) throws RemoteException {
		// 패널에 알림 전달
		shelfPanel.pushAlarm(boardVo);
	}

}
