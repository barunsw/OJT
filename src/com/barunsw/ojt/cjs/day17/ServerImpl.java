package com.barunsw.ojt.cjs.day17;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.cjs.common.BoardVo;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);
	private List<BoardVo> rackViewList = new ArrayList<>();
	private List<ClientInterface> clientInfoList = new ArrayList<>();
	private BoardVo board = new BoardVo();

	public ServerImpl() throws RemoteException, InterruptedException {
		super();
		initData();
		initAlarmGenerator();
	}

	private void initData() {
		for (int i = 0; i < 38; i++) {
			BoardVo boardVo = new BoardVo();
			boardVo.setBoardId(i);
			boardVo.setSeverity(3);
			if (i < 2) {
				boardVo.setBoardType(BoardType.MPU);
				boardVo.setBoardName("MPU");
			}
			else if (i % 18 == 0 || i == 36) {
				boardVo.setBoardType(BoardType.SRGU);
				boardVo.setBoardName("SRGU");
			}
			else if (i % 19 == 0 || i == 37) {
				continue;
			}
			else {
				boardVo.setBoardName("SALC");
				boardVo.setBoardType(BoardType.SALC);
			}
			rackViewList.add(boardVo);
		}
	}

	private void initAlarmGenerator() throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					int boardId = (int) (Math.random() * 38);
					int severity = (int) (Math.random() * 4);
					Severity sev = Severity.items[severity];
					board.setBoardId(boardId);
					board.setSeverity(severity);
					try {
						for (ClientInterface client : clientInfoList) {
							client.pushAlarm(board);
						}
						Thread.sleep(1000);
					}
					catch (Exception e) {
						LOGGER.debug(e.getMessage(), e);
					}
				}
			}
		});
		t.start();
	}

	@Override
	public int register(ClientInterface clientInterface) {
		clientInfoList.add(clientInterface);
		return 0;
	}

	@Override
	public List<BoardVo> selectBoardList() {
		return rackViewList;
	}
}
