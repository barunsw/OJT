package com.barunsw.ojt.phs.day15;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RackViewInterface extends Remote {
	public List<BoardVo> selectBoardList() throws RemoteException;
	public void addBoardVo(BoardVo boardVo) throws RemoteException;
}
