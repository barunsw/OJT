package com.barunsw.ojt.mjg.day16;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.mjg.day15.ServerMain;
import com.barunsw.ojt.vo.BoardVo;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
	
	public static final int PORT = 50000;
	
	private List<ClientInterface> cliRepo = new ArrayList<>();
	private List<BoardVo> boardList = new ArrayList<>();

	public ServerImpl() throws RemoteException {
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

	    // SRGU 보드 (ID: 18, 38)
	    boardList.add(createBoard(BoardType.SRGU, "SRGU01", 18, Severity.NORMAL));
	    boardList.add(createBoard(BoardType.SRGU, "SRGU02", 38, Severity.NORMAL));

	    // SALC 보드 (나머지 ID: 2~17, 20~35)
	    for (int i = 2; i < 18; i++) {
	        boardList.add(createBoard(BoardType.SALC, "SALC" + String.format("%02d", i), i, Severity.NORMAL));
	    }
	    for (int i = 20; i < 38; i++) {
	        boardList.add(createBoard(BoardType.SALC, "SALC" + String.format("%02d", i), i, Severity.NORMAL));
	    }
	}
	
	// 보드 정보 생성 
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
		Thread alarmThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						// 2. 랜덤하게 ID와 Severity를 생성하고 BoardVo에 반영
						int boardId = (int) (Math.random() * boardList.size());
						int severityIndex = (int) (Math.random() * 4);

						Severity severity = Severity.items[severityIndex];

						BoardVo boardVo = boardList.get(boardId);
						boardVo.setSeverity(severityIndex);
						
						LOGGER.debug("\nUpdated Board: " + boardVo.getBoardName() + ", Severity: " + severity);

						// 3. cliRepo 루프를 돌면서 clientInterface에 pushAlarm 실행
						for (ClientInterface client : cliRepo) {
							try {
								client.pushAlarm(boardVo);
							}
							catch (RemoteException re) {
								re.printStackTrace();
							}
						}

						// 10초 간격으로 실행
						Thread.sleep(10000L);
					}
					catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
		});

		alarmThread.start();
	}

	@Override
	public int register(ClientInterface clientInterface) {
		// cliRepo에 담는다.
        cliRepo.add(clientInterface);
        return 0;
	}

	@Override
	public List<BoardVo> selectBoardList() {
		// 보드정보(boardList)를 보내준다.

		return boardList;
	}
}
