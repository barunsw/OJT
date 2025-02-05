package com.barunsw.ojt.cjs.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.BoardVo;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientImpl.class);

	protected ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void pushAlarm(BoardVo boardVo) {
		ClientMain.eventQueueWorker.push(boardVo);
		LOGGER.debug("========");
	}

}
