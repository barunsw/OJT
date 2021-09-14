package com.barunsw.ojt.day15;

import java.rmi.Remote;

import com.barunsw.ojt.vo.BoardVo;

public interface ClientInterface extends Remote {
	public void pushAlarm(BoardVo boardVo);
}
