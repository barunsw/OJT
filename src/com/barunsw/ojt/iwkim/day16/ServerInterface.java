package com.barunsw.ojt.iwkim.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.barunsw.ojt.vo.BoardVo;

public interface ServerInterface extends Remote {
	public List<BoardVo> getBoardData() throws RemoteException;
}
