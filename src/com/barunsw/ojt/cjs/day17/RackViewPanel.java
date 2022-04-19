package com.barunsw.ojt.cjs.day17;

import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.BoardVo;

public class RackViewPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(RackViewPanel.class);
	ServerInterface serverIf ;
	ClientInterface clientIf;

	private List<BoardVo> boardList;
	private int boardId;
	BoardVo boardVo ;
	public static final int WIDTH = 854;
	public static final int HEIGHT = 604;

	public final int SLOT_NUM = 38;

	public final int BOARD_START_X = 27;
	public final int TOP_BOARD_START_Y = 26;
	public final int BOTTOM_BOARD_START_Y = 307;
	
	public RackViewPanel() {
		try {
			initRmi();
			initComponent();
			initData();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initRmi() throws RemoteException {
		ClientMain.eventQueueWorker.addEventListener(this);

		Registry registry = LocateRegistry.getRegistry(50001);
		Remote remote;
		try {
			remote = registry.lookup("Rack");
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface) remote;
			}
			serverIf.register(clientIf);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		this.setLayout(null);
	}

	private List<BoardVo> getBoardData() throws RemoteException {
		return serverIf.selectBoardList();
	}

	private void initData() throws RemoteException {
		// 연동에 의해 board 정보 조회
		boardList = getBoardData();

		for (BoardVo oneBoardVo : boardList) {
			boardId = oneBoardVo.getBoardId();

			BoardPanel boardPanel = new BoardPanel(oneBoardVo);

			this.add(boardPanel, null);
			boardPanel.repaint();

			int boardWidth = boardPanel.getBoardWidth();
			int boardHeight = boardPanel.getBoardHeight();

			LOGGER.debug(String.format("boardId:%s, boardWidth:%s, boardHeight:%s", boardId, boardWidth, boardHeight));

			if (boardId < 2) {
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			} 
			else if (boardId < 19) {
				LOGGER.debug("startX:" + (BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM))));

				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
						BOTTOM_BOARD_START_Y, boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			} 
			else {
				LOGGER.debug("{}", boardId % SLOT_NUM);
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId - 18)), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
			LOGGER.debug("--- TestPanel에 boardPanel 추가");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
	}

	@Override
	public void push(Object o) {

		repaint();
	}
}
