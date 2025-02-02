package com.barunsw.ojt.day15;

import java.rmi.Remote;
import java.util.List;

import com.barunsw.ojt.vo.BoardVo;

public interface ServerInterface extends Remote {
	public int register(ClientInterface clientInterface);
	public List<BoardVo> selectBoardList();
}
