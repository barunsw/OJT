package com.barunsw.ojt.phs.day15;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RackViewImpl extends UnicastRemoteObject implements RackViewInterface{
	
	private static final Logger LOGGER = LogManager.getLogger(ClientPanel.class);
	
	private List<BoardVo> boardlist;
	
	public RackViewImpl() throws RemoteException {
		LOGGER.debug("Server - RackViewImpl 생성완료");
		boardlist = new ArrayList<BoardVo>();
	}
	
	@Override
	public List<BoardVo> selectBoardList() throws RemoteException {
		LOGGER.debug("Server - boardList 조회");
		return boardlist;
	}

	@Override
	public void addBoardVo(BoardVo boardVo) throws RemoteException {
		LOGGER.debug("Server - boardVo 추가");
		boardlist.add(boardVo);
	}


}
