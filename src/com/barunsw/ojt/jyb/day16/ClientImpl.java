package com.barunsw.ojt.jyb.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.barunsw.ojt.vo.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private ShelfPanel shelfPanel;
	private EventQueueWorker<BoardVo> eventQueueWorker;

	protected ClientImpl(ShelfPanel shelfPanel) throws RemoteException {
		super();

		this.shelfPanel = shelfPanel;
		this.eventQueueWorker = shelfPanel.getEventQueueWorker();
	}

	@Override
	public void pushAlarm(BoardVo boardVo) throws RemoteException {
		eventQueueWorker.processObject(boardVo); //큐에 알림 추가
	}
}
