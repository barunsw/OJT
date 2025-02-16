package com.barunsw.ojt.jyb.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.vo.BoardVo;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	public List<ClientInterface> cliRepo = new ArrayList<>();

	private List<BoardVo> boardList = new ArrayList<>();

	protected ServerImpl() throws RemoteException {
		super();

		initData();
		initAlarmGenerator();
	}

	private void initData() {
		// 보드 정보를 생성한다. Full 실장된 보드 정보를 생성한다.
		// boardList 전역에 둔다.

		// MPU 보드 (ID: 0, 1)
		boardList.add(createBoard(BoardType.MPU, "MPU01", 0, Severity.NORMAL));
		boardList.add(createBoard(BoardType.MPU, "MPU02", 1, Severity.NORMAL));

		// SRGU 보드 (ID: 18, 36)
		boardList.add(createBoard(BoardType.SRGU, "SRGU01", 18, Severity.NORMAL));
		boardList.add(createBoard(BoardType.SRGU, "SRGU02", 36, Severity.NORMAL));

		// SALC 보드 (나머지 ID: 2~17, 20~35)
		for (int i = 2; i < 18; i++) {
			boardList.add(createBoard(BoardType.SALC, "SALC" + String.format("%02d", i), i, Severity.NORMAL));
		}
		for (int i = 20; i < 36; i++) {
			boardList.add(createBoard(BoardType.SALC, "SALC" + String.format("%02d", i), i, Severity.NORMAL));
		}
	}

	private BoardVo createBoard(BoardType type, String name, int id, int severity) {
		BoardVo board = new BoardVo();
		board.setBoardType(type);
		board.setBoardName(name);
		board.setBoardId(id);
		board.setSeverity(severity);
		return board;
	}

	private void initAlarmGenerator() {
		// 1. RandomTest를 참고해서 쓰레드를 만든다.
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					// 2. 그 쓰레드 안에서 랜덤하게 ID, Severity를 생성하고 BoardVo에 넣는다.
					// int boardId = (int) (Math.random() * 32);
					int boardId = (int) (Math.random() * boardList.size()); // 수정된 부분

					int severity = (int) (Math.random() * 4); // severity값

					Severity sev = Severity.items[severity]; // 열거형 타입 항목

					LOGGER.debug("boardId:" + boardId + ", severity:" + sev);

					BoardVo boardVo = boardList.get(boardId);
					boardVo.setSeverity(severity);

					// 3. cliRepo 루프를 돌면서 clientInterface에 pushAlarm한다.
					for (ClientInterface clientInterface : cliRepo) {
						try {
							clientInterface.pushAlarm(boardVo);
						}
						catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException ex) {

					}
				}
			}
		});
		thread.start();
	}

	@Override
	public int register(ClientInterface clientInterface) throws RemoteException {
		LOGGER.debug("register 완료");

		cliRepo.add(clientInterface);

		return 0;
	}

	@Override
	public List<BoardVo> selectBoardList() throws RemoteException {
		LOGGER.debug("select Board");

		return boardList;
	}

}
