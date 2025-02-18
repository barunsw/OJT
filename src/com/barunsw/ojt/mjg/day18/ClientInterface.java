package com.barunsw.ojt.mjg.day18;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.barunsw.ojt.vo.BoardVo;

public interface ClientInterface extends Remote {
	public void pushAlarm(BoardVo boardVo) throws RemoteException;
}
