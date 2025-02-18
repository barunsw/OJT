package com.barunsw.ojt.mjg.day18;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);

	protected ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void pushAlarm(BoardVo boardVo) throws RemoteException {
		LOGGER.debug("+++(ClientImpl.pushAlarm 실행됨) boardVo: " + boardVo);

		ClientMain.eventQueueWorker.push(boardVo);
	}
}
