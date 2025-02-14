package com.barunsw.ojt.day15;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.barunsw.ojt.vo.BoardVo;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private List<ClientInterface> cliRepo = new ArrayList<>();
	
	public ServerImpl() throws RemoteException {
		super();
		
		initData();
		initAlarmGenerator();
	}
	
	private void initData() {
		// 보드 정보를 생성한다. Full 실장된 보드 정보를 생성한다.
		// boardList 전역에 둔다.
	}
	
	private void initAlarmGenerator() {
		// 1. RandomTest를 참고해서 쓰레드를 만든다.
		// 2. 그 쓰레드 안에서 랜덤하게 ID, Severity를 생성하고 BoardVo에 넣는다.
		// 3. cliRepo 루프를 돌면서 clientInterface에 pushAlarm한다.
	}

	@Override
	public int register(ClientInterface clientInterface) {
		// cliRepo에 담는다.
		
		return 0;
	}

	@Override
	public List<BoardVo> selectBoardList() {
		// 보드정보(boardList)를 보내준다.
		
		return null;
	}
}
