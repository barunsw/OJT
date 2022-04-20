package com.barunsw.ojt.cjs.day17;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.barunsw.ojt.cjs.common.BoardVo;

public interface ClientInterface extends Remote {
	public void pushAlarm(BoardVo boardVo) throws RemoteException;
}
