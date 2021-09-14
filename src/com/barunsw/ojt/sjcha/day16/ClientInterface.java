package com.barunsw.ojt.sjcha.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.barunsw.ojt.sjcha.day16.BoardVo;

public interface ClientInterface extends Remote {
	public void pushAlarm(BoardVo boardVo) throws RemoteException;
}
