package com.barunsw.ojt.iwkim.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final long serialVersionUID = -7642182568862518191L;

	private static Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public List<BoardVo> getBoardData() throws RemoteException {
//		List<BoardVo> boardList = new ArrayList<>();
//
//		 0, 1번에는 MPU가 들어가야되고 18번과 36번은 2칸을차지하는 SRGU가 들어가야한다.
//		 나머지 칸은 모두 SALC로 채워야한다.
//		for (int i = 0; i < RackViewConstants.BOARD_SIZE; i++) {
//			BoardVo boardVo = null;
//
//			if (i < 2) {
//				boardVo = new BoardVo(BoardType.MPU, "MPU"+i, (int) (Math.random() * 4), i);
//				boardVo.setBoardType(BoardType.MPU);
//				boardVo.setBoardName("MPU" + i);
//				boardVo.setSeverity((int) (Math.random() * 4));
//				boardVo.setBoardId(i);
//		}
//			else if (i % (RackViewConstants.SLOT_NUM - 2) == 0) {
//				boardVo.setBoardType(BoardType.SRGU);
//				boardVo.setBoardName("SRGU" + (i / (RackViewConstants.SLOT_NUM - 2) -1));
//				boardVo.setSeverity((int) (Math.random() * 4));
//				boardVo.setBoardId(i);
//			}
//			else {
//				boardVo.setBoardType(BoardType.SALC);
//				boardVo.setBoardName("SALC" + ((i < (RackViewConstants.SLOT_NUM - 2)) ? i - 2 : i - 4));
//				boardVo.setSeverity((int) (Math.random() * 4));
//				boardVo.setBoardId(i);
//			}
//
//			boardList.add(boardVo);
//		}
//		
//		return boardList;		

		return new RackViewConstants().getBoardList();
	}

	@Override
	public AlarmVo getAlarmInfo() {
		return new AlarmVo(BoardType.SALC
				, (int) (Math.random() * RackViewConstants.SALC_BOARD_SIZE)
				, (int) (Math.random() * RackViewConstants.SEVERITY_SIZE));
	}
}
