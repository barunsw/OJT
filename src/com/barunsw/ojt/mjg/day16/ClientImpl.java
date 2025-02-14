package com.barunsw.ojt.mjg.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);
	
	private ShelfPanel panel;
	
	protected ClientImpl(ShelfPanel panel) throws RemoteException {
		super();

		this.panel = panel;
	}

	@Override
	public void pushAlarm(BoardVo boardVo) throws RemoteException{
		// panel쪽에 알람을 전달한다. pushAlarm을 호출

        panel.pushAlarm(boardVo);
	}
}
