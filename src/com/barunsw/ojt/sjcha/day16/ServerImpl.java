package com.barunsw.ojt.sjcha.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.phs.day16.rmi.AlaramVo;
import com.barunsw.ojt.sjcha.day16.BoardType;
import com.barunsw.ojt.sjcha.day16.BoardVo;
import com.barunsw.ojt.sjcha.day16.ServerImpl;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	private List<ClientInterface> clientData = new ArrayList<>();

	private List<BoardVo> boardList = new ArrayList<>();

	protected ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public int register(ClientInterface clientInterface) throws RemoteException {
		LOGGER.debug("register(ClientInterface) : Register user from Server to Client");

		clientData.add(clientInterface);

		return 0;
	}

	@Override
	public List<BoardVo> selectBoardList() throws RemoteException {
		LOGGER.debug("selectBoard");

		for (ClientInterface oneClient : clientData) {
			for(int i = 0; i < 38; i++) {
				int index = (int) (Math.random() * 37);

				BoardVo boardVo = new BoardVo();
				boardVo.setBoardId(index);
				boardVo.setSeverity((int) (Math.random() * 4));

				if (index < 2) {
					boardVo.setBoardType(BoardType.MPU);
					boardVo.setBoardName("MPU");
				}

				else if (index % 18 == 0 || index % 36 == 0) {
					boardVo.setBoardType(BoardType.SRGU);
					boardVo.setBoardName("SRGU");
				}

				else if (index % 19 == 0 || index % 37 == 0) {
					continue;
				}

				else {
					boardVo.setBoardType(BoardType.SALC);
					boardVo.setBoardName("SALC");
				}

				boardList.add(boardVo);
				oneClient.pushAlarm(boardVo);
			}
		}
		return boardList;
	}
}