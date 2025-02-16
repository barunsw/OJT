package com.barunsw.ojt.jyb.day16;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import com.barunsw.ojt.vo.BoardVo;

public interface ServerInterface extends Remote {
	public int register(ClientInterface clientInterface) throws RemoteException;

	public List<BoardVo> selectBoardList() throws RemoteException;
}
